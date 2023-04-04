package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysTapList;
import com.ruoyi.system.domain.SysTapName;
import com.ruoyi.system.domain.SysTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dz
 * @date 2023-02-28
 */
public interface ISysNcCodeTransformService {

    /**
     * 查询出tap文件列表
     *
     * @param tapNames 需要转换的NC代码文件名
     * @return 在本地文件下查询出已转换的tap文件
     * @throws FileNotFoundException 文件未找到异常
     */
    ArrayList<SysTapList> selectTapList(String[] tapNames) throws FileNotFoundException;

    /**
     * 选中前端返回的NC代码名，后端传输文件给前端
     *
     * @param fileName 需要比较的NC代码文件名
     * @return NC代码
     */
    File compareNcCode(String fileName);

    /**
     * 查询出刀具列表
     *
     * @return 在数据库中查询出的刀具列表
     */
    List<SysTools> selectToolList();

    /**
     * 校验tap文件是否转换上传成功
     *
     * @param tapName 需要校验的tap文件名
     * @return 校验tap文件名是否存在
     */
    boolean checkTapNames(String tapName);

    /**
     * 插入tap文件名
     *
     * @param tapNames 需要插入的tap文件名
     */
    void insertTapNames(ArrayList<String> tapNames);
}
