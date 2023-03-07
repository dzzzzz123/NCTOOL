package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDiffFiles;
import com.ruoyi.system.domain.SysTapList;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author dz
 * @date 2023-02-28
 */
public interface ISysNcCodeTransformService {

    /**
     * 查询出tap文件列表
     * @param tapNames 需要转换的NC代码文件名
     * @return 在本地文件下查询出已转换的tap文件
     * @throws FileNotFoundException 文件未找到异常
     */
    ArrayList<SysTapList> selectTapList(String[] tapNames) throws FileNotFoundException;

    /**
     * 选中前端返回的NC代码名，后端传输文件给前端
     * @param filesNames 需要比较的NC代码文件名
     * @return NC代码
     */
    SysDiffFiles compareNcCode(SysDiffFiles filesNames);
}
