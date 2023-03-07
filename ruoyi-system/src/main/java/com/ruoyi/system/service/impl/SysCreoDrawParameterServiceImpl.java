package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.system.domain.SysProgramNumber;
import com.ruoyi.system.mapper.SysCreoDrawParameterMapper;
import com.ruoyi.system.service.ISysCreoDrawParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author dz
 * @date 2023-02-27
 */
@Service
public class SysCreoDrawParameterServiceImpl implements ISysCreoDrawParameterService {

    @Autowired
    private SysCreoDrawParameterMapper creoMapper;

    /**
     * 选出需要个数的工序号
     *
     * @param bookNumber 选号的数量
     * @return SysProgramNumber
     */
    @Override
    public ArrayList<SysProgramNumber> getProgramNumbers(Integer bookNumber) {
        ArrayList<SysProgramNumber> list = new ArrayList<>();
        while (list.size() < bookNumber) {
            creoMapper.addOne();
            SysProgramNumber programNumber = creoMapper.getProgramNumber();
            if (validateProgramNumber(programNumber)){
                list.add(programNumber);
            }else{
                // 当工序图id无法满足客户需求时，进位
                creoMapper.carryProgramNumber();
            }
        }
        return list;
    }

    /**
     * 验证从数据库中获取的id是否符合需求
     * @param sysProgramNumber 用来验证的对象
     * @return 返回值
     */
    private static boolean validateProgramNumber(SysProgramNumber sysProgramNumber){
        return sysProgramNumber.getNumberTwo() <= Constants.MAX_NUMBER_TWO;
    }
}
