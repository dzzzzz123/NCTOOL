package com.ruoyi.web.controller.system;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.system.utils.transform.TransformTo6300;
import com.ruoyi.system.utils.transform.TransformTo655;
import com.ruoyi.system.utils.transform.TransformTo7000;
import com.ruoyi.system.domain.SysTapList;
import com.ruoyi.system.service.ISysNcCodeTransformService;
import com.ruoyi.system.utils.transform.TransformTo7000Finishing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

import static com.ruoyi.framework.datasource.DynamicDataSourceContextHolder.log;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * 转换NC代码控制层
 *
 * @author dz
 * @date 2023-02-23
 */
@RestController
@RequestMapping("/system/nccode")
public class SysNcCodeTransformController extends BaseController {

    @Autowired
    private ISysNcCodeTransformService transformService;

    @Value("${upload.path}")
    private String path;

    @Value("${upload.toDNCPath}")
    private String toDncPath;

    @Value("${upload.pdf}")
    private String toPdfPath;

    /**
     * 判断文件是pdf文件还是tap文件，然后进行上传
     *
     * @param files 前端传递过来的tap文件和pdf文档
     * @return 返回结果集传递给前端
     * @throws IOException               异常
     * @throws InvalidExtensionException 异常
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:upload')")
    @Log(title = "上传NC代码到后端", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult uploadTapFile(@RequestParam("file") MultipartFile[] files) throws IOException, InvalidExtensionException {
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileName = FilenameUtils.getName(originalFileName);
            String extension = FilenameUtils.getExtension(originalFileName);

            if (Objects.requireNonNull(fileName).length() > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
            Path fileDir;
            if (extension.equals("tap")) {
                fileDir = Paths.get(path);
            } else {
                fileDir = Paths.get(toPdfPath);
            }
            if (!Files.exists(fileDir)) {
                Files.createDirectories(fileDir);
            }

            Path targetPath = fileDir.resolve(fileName);
            Files.write(targetPath, file.getBytes());
        }
        return new AjaxResult(200, "Success");
    }

    /**
     * 转换NC代码
     *
     * @param tapNames 前端传输过来需要转换NC代码的tap文件名称
     * @return 返回集
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:transform')")
    @Log(title = "转换NC代码", businessType = BusinessType.TRANSFORM)
    @GetMapping("/transform/{tapNames}")
    public AjaxResult transformNcCode(@PathVariable String[] tapNames) {
        for (String tapName : tapNames) {
            File temp = new File(path + tapName);
            File file = new File(path + tapName.split("\\.")[0] + ".tap_MMC_NH6300");
            File file2 = new File(path + tapName.split("\\.")[0] + ".tap_MMC_NV7000");
            File file3 = new File(path + tapName.split("\\.")[0] + ".tap_V655");
            try {
                FileUtil.copyFile(temp, file, REPLACE_EXISTING);
                FileUtil.copyFile(temp, file2, REPLACE_EXISTING);
                FileUtil.copyFile(temp, file3, REPLACE_EXISTING);
                if (checkIsFinishing(file2)) {
                    TransformTo7000Finishing.transform(file2, 3);
                } else {
                    TransformTo6300.transform(file, 0);
                    TransformTo7000.transform(file2, 1);
                    TransformTo655.transform(file3, 2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new AjaxResult(200, "Success");
    }

    /**
     * 用上传文件的文件名，获取到文件列表
     *
     * @param tapNames 上传的文件名
     * @return tapList的列表
     * @throws IOException 读写异常
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:tapList')")
    @Log(title = "获取已转换tap文件列表", businessType = BusinessType.SELECT)
    @GetMapping("/newTapList/{tapNames}")
    public ArrayList<SysTapList> newTapList(@PathVariable String[] tapNames) throws IOException {
        return transformService.selectTapList(tapNames);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static
    class FileToCompare {
        private String oldFileName;
    }

    /**
     * 将需要比较的文件上传到前端
     *
     * @param fileName 前端传输来的需要比较的文件名
     * @param response response
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:compare')")
    @Log(title = "比较NC代码", businessType = BusinessType.COMPARE)
    @PutMapping("/compare")
    public void compareDownload(@RequestBody FileToCompare fileName, HttpServletResponse response) {
        File file = transformService.compareNcCode(fileName.getOldFileName());
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, file.getName());
            FileUtils.writeBytes(file.getPath(), response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 将本地文件上传到DNC
     *
     * @param tapNames 上传文件名
     * @return 返回集
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:ToDNC')")
    @Log(title = "上传NC代码到DNC", businessType = BusinessType.UPLOAD)
    @GetMapping("/ToDNC/{tapNames}")
    public AjaxResult uploadToDnc(@PathVariable String[] tapNames) {
        for (String tapName : tapNames) {
            String tapNamePrefix = tapName.split("\\.")[0];
            File nh6300 = new File(path + tapNamePrefix + ".tap_MMC_NH6300");
            File nv7000 = new File(path + tapNamePrefix + ".tap_MMC_NV7000");
            File v655 = new File(path + tapNamePrefix + ".tap_V655");
            File nh6300Dnc = new File(toDncPath + File.separator + "Mori_Seiki_NH6300" + File.separator + tapNamePrefix);
            File nv7000Dnc = new File(toDncPath + File.separator + "Mori_Seiki_NV7000" + File.separator + tapNamePrefix);
            File nv7000DncFinishing = new File(toDncPath + File.separator + "Final_NV7000" + File.separator + tapNamePrefix);
            File v655Dnc = new File(toDncPath + File.separator + "mzk655" + File.separator + tapNamePrefix);
            try {
                if (checkIsFinishing(nv7000)) {
                    FileUtil.move(nv7000, nv7000DncFinishing, true);
                } else {
                    FileUtil.move(nh6300, nh6300Dnc, true);
                    FileUtil.move(nv7000, nv7000Dnc, true);
                    FileUtil.move(v655, v655Dnc, true);
                }
            } catch (IORuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        FileUtil.del(path);
        return new AjaxResult(200, "Success");
    }

    public boolean checkIsFinishing(File file) {
        boolean flag = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lineString;
            int line = 1;
            while ((lineString = reader.readLine()) != null && line < 10) {
                line++;
                if (lineString.startsWith("(POST PROCESSOR") && lineString.contains("30,")) {
                    flag = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * 客户需要当NC代码转换上传到DNC后，所有操作完成之后，可以得到反馈
     * 而不是需要去DNC文件夹中去查看是否转换上传成功，需要对结果进行校验
     * 所有在前端获取tap文件的文件名，与数据库中的数据进行比较，完成校验
     *
     * @param tapName 前端传输过来的tap文件名
     * @return 返回结果集
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:checkTap')")
    @Log(title = "查询tap文件是否存在", businessType = BusinessType.SELECT)
    @GetMapping("/getTapName/{tapName}")
    public boolean checkTapName(@PathVariable String tapName) {
        return transformService.checkTapNames(tapName.split("\\.")[0]);
    }

    /**
     * 完成DNC上传之后，前端返回上传成功后执行此方法
     * 将前端传输的tap文件名数组写入到数据库方便后面查询
     *
     * @param tapNames 前端传输过来的tap文件名
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:insertTap')")
    @Log(title = "插入tap文件名到数据库", businessType = BusinessType.INSERT)
    @GetMapping("/insertTapNames/{tapNames}")
    public void insertTapNames(@PathVariable String[] tapNames) {
        ArrayList<String> tapNameToInsert = new ArrayList<>();
        for (String tapName : tapNames) {
            tapNameToInsert.add(tapName.split("\\.")[0]);
        }
        transformService.insertTapNames(tapNameToInsert);
    }
}