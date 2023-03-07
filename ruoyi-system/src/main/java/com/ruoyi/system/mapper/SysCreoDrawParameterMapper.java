package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysProgramNumber;

/**
 * @author dz
 * @date 2023-02-27
 */
public interface SysCreoDrawParameterMapper {

    /**
     * 取号
     * @return SysProgramNumber
     */
    SysProgramNumber getProgramNumber();

    /**
     * 加一
     * @return SysProgramNumber
     */
    void addOne();

    /**
     * 根据需求进位
     */
    void carryProgramNumber();
}
