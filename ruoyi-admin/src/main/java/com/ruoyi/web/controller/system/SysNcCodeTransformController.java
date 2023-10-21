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
import com.ruoyi.system.domain.SysTapList;
import com.ruoyi.system.service.ISysNcCodeTransformService;
import com.ruoyi.system.utils.transform.TransformTo6300;
import com.ruoyi.system.utils.transform.TransformTo655;
import com.ruoyi.system.utils.transform.TransformTo7000;
import com.ruoyi.system.utils.transform.TransformTo7000Finishing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static com.ruoyi.framework.datasource.DynamicDataSourceContextHolder.log;
import static com.ruoyi.system.constant.TransformConstants.*;
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
     */
    @PreAuthorize("@ss.hasAnyPermi('system:NcCode:upload')")
    @Log(title = "上传NC代码到后端", businessType = BusinessType.UPLOAD)
    @PostMapping("/upload")
    public AjaxResult uploadTapFile(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        List<CompletableFuture<Void>> uploadFutures = new ArrayList<>();

        for (MultipartFile file : files) {
            CompletableFuture<Void> uploadFuture = CompletableFuture.runAsync(() -> {
                try {
                    processUploadedFile(file, request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            uploadFutures.add(uploadFuture);
        }

        CompletableFuture<Void>[] futuresArray = uploadFutures.toArray(new CompletableFuture[0]);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futuresArray);
        try {
            allOf.join(); // 使用join而不是get，以处理内部的异常
        } catch (CompletionException e) {
            Throwable cause = e.getCause(); // 获取实际异常原因
            if (cause instanceof InvalidExtensionException) {
                return AjaxResult.error("请检查您的账户和windows账户是否一致！");
            }
            return AjaxResult.error("上传文件出现问题，请检查是否成功！");
        }

        return AjaxResult.success("Success");
    }

    /**
     * 处理文件上传
     * @param file
     * @param request
     * @throws IOException
     * @throws InvalidExtensionException
     */
    @Async
    protected void processUploadedFile(MultipartFile file, HttpServletRequest request) throws IOException, InvalidExtensionException {
        // 上传文件的处理逻辑，与你之前的代码相同
        String originalFileName = file.getOriginalFilename();
        String fileName = FilenameUtils.getName(originalFileName);
        String extension = FilenameUtils.getExtension(originalFileName);

        if (Objects.requireNonNull(fileName).length() > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

        // 判断上传文件类型，是tap文件还是pdf文件
        Path fileDir = extension.equals("tap") ? Paths.get(path) : Paths.get(toPdfPath);

        if (!Files.exists(fileDir)) {
            Files.createDirectories(fileDir);
        }

        Path targetPath = fileDir.resolve(fileName);

        Files.write(targetPath, file.getBytes());
        // 如果是pdf文件，上传后再修改文件的作者属性
        if (extension.equals("pdf")) {
            Boolean flag = setAuthor(targetPath.toFile(), getCurrentWindowsUser(request));
            if (!flag) {
                FileUtil.del(targetPath.toFile());
            }
        }
    }

    /**
     * 获取当前系统的Windows用户名
     *
     * @param request 请求
     * @return 用户名
     */
    private String getCurrentWindowsUser(HttpServletRequest request) {
        String currentWindowsUser = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    currentWindowsUser = cookie.getValue();
                }
            }
        }
        return currentWindowsUser;
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
            File origin = new File(path + tapName);
            try {
                String postRes = checkIsFinishing(origin);
                switch (postRes) {
                    case POST_PROCESSOR_IS_30: {
                        File file = new File(path + tapName.split("\\.")[0] + ".final_NV7000");
                        FileUtil.copyFile(origin, file, REPLACE_EXISTING);
                        TransformTo7000Finishing.transform(file, 3);
                        break;
                    }
                    case POST_PROCESSOR_IS_98: {
                        File file = new File(path + tapName.split("\\.")[0] + ".final_NV7000");
                        FileUtil.copyFile(origin, file, REPLACE_EXISTING);
                        break;
                    }
                    case POST_PROCESSOR_IS_DEFAULT: {
                        File file = new File(path + tapName.split("\\.")[0] + ".tap_MMC_NH6300");
                        File file2 = new File(path + tapName.split("\\.")[0] + ".tap_MMC_NV7000");
                        File file3 = new File(path + tapName.split("\\.")[0] + ".tap_V655");
                        FileUtil.copyFile(origin, file, REPLACE_EXISTING);
                        FileUtil.copyFile(origin, file2, REPLACE_EXISTING);
                        FileUtil.copyFile(origin, file3, REPLACE_EXISTING);
                        TransformTo6300.transform(file, 0);
                        TransformTo7000.transform(file2, 1);
                        TransformTo655.transform(file3, 2);
                        break;
                    }
                    default:
                        break;
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
            File origin = new File(path + tapName);
            String tapNamePrefix = tapName.split("\\.")[0];
            String postRes = checkIsFinishing(origin);
            switch (postRes) {
                case POST_PROCESSOR_IS_30: {
                    File nv7000 = new File(path + tapNamePrefix + ".final_NV7000");
                    try {
                        File nv7000DncFinishing = new File(toDncPath + File.separator + "Final_NV7000" + File.separator + tapNamePrefix);
                        FileUtil.move(nv7000, nv7000DncFinishing, true);
                    } catch (IORuntimeException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case POST_PROCESSOR_IS_98:
                    File nv7000DncFinishing = new File(toDncPath + File.separator + "Final_NV7000" + File.separator + tapNamePrefix);
                    FileUtil.move(origin, nv7000DncFinishing, true);
                    break;
                case POST_PROCESSOR_IS_DEFAULT: {
                    File nh6300 = new File(path + tapNamePrefix + ".tap_MMC_NH6300");
                    File nv7000 = new File(path + tapNamePrefix + ".tap_MMC_NV7000");
                    File v655 = new File(path + tapNamePrefix + ".tap_V655");
                    File nh6300Dnc = new File(toDncPath + File.separator + "Mori_Seiki_NH6300" + File.separator + tapNamePrefix);
                    File nv7000Dnc = new File(toDncPath + File.separator + "Mori_Seiki_NV7000" + File.separator + tapNamePrefix);
                    File v655Dnc = new File(toDncPath + File.separator + "mzk655" + File.separator + tapNamePrefix);
                    try {
                        FileUtil.copyFile(nh6300, nh6300Dnc, REPLACE_EXISTING);
                        FileUtil.copyFile(nv7000, nv7000Dnc, REPLACE_EXISTING);
                        FileUtil.copyFile(v655, v655Dnc, REPLACE_EXISTING);
                    } catch (IORuntimeException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                default:
                    break;
            }

        }
        return new AjaxResult(200, "Success");
    }

    public String checkIsFinishing(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lineString;
            int line = 1;
            while ((lineString = reader.readLine()) != null && line < 15) {
                line++;
                if (lineString.startsWith("(POST PROCESSOR")) {
                    if (lineString.contains(POST_PROCESSOR_30)) {
                        return POST_PROCESSOR_IS_30;
                    } else if (lineString.contains(POST_PROCESSOR_98)) {
                        return POST_PROCESSOR_IS_98;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return POST_PROCESSOR_IS_DEFAULT;
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


    /**
     * 修改pdf文件的所有者信息
     *
     * @param file   需要修改所有者信息的pdf文件
     * @param author 所有者信息
     * @return 结果集
     */
    private Boolean setAuthor(File file, String author) {
        try {
            Path path = Paths.get(file.getAbsolutePath());
            FileOwnerAttributeView foav = Files.getFileAttributeView(path, FileOwnerAttributeView.class);

            FileSystem fs = FileSystems.getDefault();
            UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();

            UserPrincipal newOwner = upls.lookupPrincipalByName(author);
            foav.setOwner(newOwner);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 每天午夜执行任务
     * 任务：删除upload文件夹中的所有文件
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void dailyTask() {
        // 在这里编写你的定时任务逻辑
        // 在上传到本地文件夹之前，先清空存储tap文件的临时文件夹
        FileUtil.del(path);
    }
}