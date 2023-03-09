package com.ruoyi.web.controller.system;

import cn.hutool.core.io.FileUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.transform.TransformTo6300;
import com.ruoyi.common.utils.transform.TransformTo655;
import com.ruoyi.common.utils.transform.TransformTo7000;
import com.ruoyi.system.domain.SysDiffFiles;
import com.ruoyi.system.domain.SysTapList;
import com.ruoyi.system.service.ISysNcCodeTransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
            String fileName = file.getOriginalFilename();
            File fileDir = new File("d:/upload");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            int fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
            if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
            File desc = createAbsoluteFile(fileName);
            file.transferTo(desc);
        }
        return new AjaxResult(200, "Success");
    }

    /**
     * @param fileName 文件名
     * @return 返回文件在本地电脑中的真实路径
     * @throws IOException 异常
     */
    private static File createAbsoluteFile(String fileName) throws IOException {
        File desc = new File("d:/upload" + File.separator + fileName);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
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
            File temp = new File("d:/upload/" + tapName);
            File file = new File("d:/upload/" + tapName.split("\\.")[0] + ".tap_MMC_NH6300");
            File file2 = new File("d:/upload/" + tapName.split("\\.")[0] + ".tap_MMC_NV7000");
            File file3 = new File("d:/upload/" + tapName.split("\\.")[0] + ".tap_V655");
            try {
                FileUtil.copyFile(temp, file, REPLACE_EXISTING);
                TransformTo6300.transform(file,0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                FileUtil.copyFile(temp, file2, REPLACE_EXISTING);
                TransformTo7000.transform(file2,1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                FileUtil.copyFile(temp, file3, REPLACE_EXISTING);
                TransformTo655.transform(file3,2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new AjaxResult(200, "Success");
    }

    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:tapList')")
    @Log(title = "获取已转换tap文件列表", businessType = BusinessType.SELECT)
    @GetMapping("/newTapList/{tapNames}")
    public ArrayList<SysTapList> newTapList(@PathVariable String[] tapNames) throws IOException {
        return transformService.selectTapList(tapNames);
    }

    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:compare')")
    @Log(title = "比较NC代码", businessType = BusinessType.COMPARE)
    @PutMapping("/compare")
    public void compareDownload(@RequestBody SysDiffFiles files, HttpServletRequest request, HttpServletResponse response) {
        SysDiffFiles sysDiffFiles = transformService.compareNcCode(files);
        try {
            String realFileName = sysDiffFiles.getNewFileName();
            String filePath = sysDiffFiles.getNewFile().getPath();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }
}
