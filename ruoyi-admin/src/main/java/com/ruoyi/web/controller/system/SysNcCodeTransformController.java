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
import java.io.File;
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


    /**
     * @param files 前端传递过来的tap文件
     * @return 返回结果集传递给前端
     * @throws IOException               异常
     * @throws InvalidExtensionException 异常
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:upload')")
    @Log(title = "上传NC代码到后端", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile[] files) throws IOException, InvalidExtensionException {
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileName = FilenameUtils.getName(originalFileName);

            // File newFile = new File(fileName);
            // String absolutePath = newFile.getAbsolutePath();
            // System.out.println("absolutePath = " + absolutePath);

            if (Objects.requireNonNull(fileName).length() > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

            Path fileDir = Paths.get(path);
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
                TransformTo6300.transform(file, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FileUtil.copyFile(temp, file2, REPLACE_EXISTING);
                TransformTo7000.transform(file2, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FileUtil.copyFile(temp, file3, REPLACE_EXISTING);
                TransformTo655.transform(file3, 2);
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
            File ORIGIN = new File(path + tapName);
            File NH6300 = new File(path + tapName.split("\\.")[0] + ".tap_MMC_NH6300");
            File NV7000 = new File(path + tapName.split("\\.")[0] + ".tap_MMC_NV7000");
            File V655 = new File(path + tapName.split("\\.")[0] + ".tap_V655");
            File NH6300_DNC = new File(toDncPath + File.separator + "Final_NH6300" + File.separator + tapName.split("\\.")[0]);
            File NV7000_DNC = new File(toDncPath + File.separator + "Final_NV7000" + File.separator + tapName.split("\\.")[0]);
            File V655_DNC = new File(toDncPath + File.separator + "Final_V655" + File.separator + tapName.split("\\.")[0]);
            try {
                FileUtil.del(ORIGIN);
                FileUtil.move(NH6300, NH6300_DNC, true);
                FileUtil.move(NV7000, NV7000_DNC, true);
                FileUtil.move(V655, V655_DNC, true);
            } catch (IORuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return new AjaxResult(200, "Success");
    }
}