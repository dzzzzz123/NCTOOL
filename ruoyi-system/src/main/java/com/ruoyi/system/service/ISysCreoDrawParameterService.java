package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysProgramNumber;

import java.util.ArrayList;

/**
 * @author dz
 * @date 2023-02-27
 */
public interface ISysCreoDrawParameterService {
    /**
     * 选出需要个数的工序号
     * @param bookNumber 选号的数量
     * @return SysProgramNumber
     */
    ArrayList<SysProgramNumber> getProgramNumbers(Integer bookNumber);
}
