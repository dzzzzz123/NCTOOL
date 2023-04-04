package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysTapName;

import java.util.ArrayList;

/**
 * @author dz
 * @date 2023-04-04
 */
public interface SysTapNameMapper {

    /**
     * 校验文件夹
     * @param tapName 需要校验的tap文件名
     * @return 校验tap文件名是否存在
     */
    int getTapName(String tapName);

    /**
     * 将tap文件名写入到数据库中
     * @param tapNames 需要插入的tap文件名
     * @return 插入成功的条数
     */
    int insertTapNames(ArrayList<String> tapNames);
}
