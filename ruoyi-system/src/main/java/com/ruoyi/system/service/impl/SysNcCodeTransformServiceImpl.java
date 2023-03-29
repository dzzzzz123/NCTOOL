package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysTapList;
import com.ruoyi.system.domain.SysTools;
import com.ruoyi.system.mapper.SysToolsMapper;
import com.ruoyi.system.service.ISysNcCodeTransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dz
 * @date 2023-02-28
 */
@Service
public class SysNcCodeTransformServiceImpl implements ISysNcCodeTransformService {

    @Autowired
    private SysToolsMapper sysToolsMapper;
    private static ArrayList<File> SCAN_FILES_NAMES;

    @Value("${upload.path}")
    private String path;

    @Value("${upload.toDNCPath}")
    private String toDncPath;

    @Override
    public ArrayList<SysTapList> selectTapList(String[] tapNames) {
        SCAN_FILES_NAMES = new ArrayList<>();
        ArrayList<String> scanFilesFilterNames = new ArrayList<>();
        ArrayList<SysTapList> tapList = new ArrayList<>();
        scanFilesWithRecursion("D:\\upload");
        for (File scanFiles : SCAN_FILES_NAMES) {
            if (scanFiles.getName().endsWith(".tap_MMC_NH6300") || scanFiles.getName().endsWith(".tap_MMC_NV7000") || scanFiles.getName().endsWith(".tap_V655")) {
                scanFilesFilterNames.add(scanFiles.getName());
            }
        }
        for (String scanFilesName : scanFilesFilterNames) {
            for (String tapName : tapNames) {
                if (Objects.equals(scanFilesName.split("\\.")[0], tapName.split("\\.")[0])) {
                    SysTapList tap = new SysTapList(scanFilesName);
                    tapList.add(tap);
                }
            }
        }
        return tapList;
    }

    /**
     * 选中前端返回的NC代码名，后端传输文件给前端
     *
     * @param fileName 需要比较的NC代码文件名
     * @return NC代码
     */
    @Override
    public File compareNcCode(String fileName) {
        String[] split = fileName.split("\\.");
        String pathToScan = toDncPath;
        String fileNameWithoutSuffix = split[0];
        SCAN_FILES_NAMES = new ArrayList<>();
        File file = null;
        switch (split[1]) {
            case "tap_MMC_NH6300":
                pathToScan += File.separator + "tap_MMC_NH6300";
                break;
            case "tap_MMC_NV7000":
                pathToScan += File.separator + "Final_NV7000";
                break;
            case "tap_V655":
                pathToScan += File.separator + "tap_V655";
                break;
            default:
                break;
        }
        scanFilesWithRecursion(pathToScan);
        for (File scanFile : SCAN_FILES_NAMES) {
            if (scanFile.getName().equals(fileNameWithoutSuffix)) {
                file = scanFile;
            }
        }
        return file;
    }

    public static void scanFilesWithRecursion(String folderPath) {
        File directory = new File(folderPath);
        if (directory.isDirectory()) {
            File[] fileList = directory.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isDirectory()) {
                        scanFilesWithRecursion(file.getAbsolutePath());
                    } else {
                        SCAN_FILES_NAMES.add(file);
                    }
                }
            }
        }
    }

    /**
     * 查询出刀具列表
     *
     * @return 在数据库中查询出的刀具列表
     */
    @Override
    public List<SysTools> selectToolList() {
        return sysToolsMapper.selectSysToolsList2();
    }
}
