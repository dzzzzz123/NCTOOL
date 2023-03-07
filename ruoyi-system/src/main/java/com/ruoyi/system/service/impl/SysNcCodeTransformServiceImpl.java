package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysDiffFiles;
import com.ruoyi.system.domain.SysTapList;
import com.ruoyi.system.service.ISysNcCodeTransformService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author dz
 * @date 2023-02-28
 */
@Service
public class SysNcCodeTransformServiceImpl implements ISysNcCodeTransformService {
    private static ArrayList<File> SCAN_FILES_NAMES;

    private static ArrayList<String> SCAN_FILES_FILTER_NAMES;

    private static ArrayList<SysTapList> TAP_LIST;

    @Override
    public ArrayList<SysTapList> selectTapList(String[] tapNames) {
        SCAN_FILES_NAMES = new ArrayList<>();
        SCAN_FILES_FILTER_NAMES = new ArrayList<>();
        TAP_LIST = new ArrayList<>();
        scanFilesWithRecursion("D:\\upload");
        for (File scanFiles : SCAN_FILES_NAMES) {
            if (scanFiles.getName().endsWith(".tap_MMC_NH6300") ||scanFiles.getName().endsWith(".tap_MMC_NV7000") || scanFiles.getName().endsWith(".tap_V655")) {
                SCAN_FILES_FILTER_NAMES.add(scanFiles.getName());
            }
        }
        for (String scanFilesName : SCAN_FILES_FILTER_NAMES) {
            for (String tapName : tapNames) {
                if (Objects.equals(scanFilesName.split("\\.")[0], tapName.split("\\.")[0])) {
                    SysTapList tap = new SysTapList(scanFilesName);
                    TAP_LIST.add(tap);
                }
            }
        }
        System.out.println("tapNames = " + Arrays.toString(tapNames));
        System.out.println("TAP_LIST = " + TAP_LIST);
        return TAP_LIST;
    }

    /**
     * 选中前端返回的NC代码名，后端传输文件给前端
     *
     * @param filesNames 需要比较的NC代码文件名
     * @return NC代码
     */
    @Override
    public SysDiffFiles compareNcCode(SysDiffFiles filesNames) {
        SCAN_FILES_NAMES = new ArrayList<>();
        scanFilesWithRecursion("D:\\upload");
        for (File scanFile : SCAN_FILES_NAMES) {
            if (scanFile.getName().equals(filesNames.getOldFileName())){
                filesNames.setOldFile(scanFile);
            }else if(scanFile.getName().equals(filesNames.getNewFileName())){
                filesNames.setNewFile(scanFile);
            }
        }
        return filesNames;
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
}
