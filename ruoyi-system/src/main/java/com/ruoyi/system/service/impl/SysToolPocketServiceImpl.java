package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.domain.SysToolPocketToSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysToolPocketMapper;
import com.ruoyi.system.domain.SysToolPocket;
import com.ruoyi.system.service.ISysToolPocketService;

/**
 * 刀具加工参数Service业务层处理
 * 
 * @author dz
 * @date 2023-02-21
 */
@Service
public class SysToolPocketServiceImpl implements ISysToolPocketService 
{
    @Autowired
    private SysToolPocketMapper sysToolPocketMapper;

    /**
     * 查询刀具加工参数
     * 
     * @param toolId 刀具加工参数主键
     * @return 刀具加工参数
     */
    @Override
    public SysToolPocket selectSysToolPocketByToolPocketId(String  toolId)
    {
        return sysToolPocketMapper.selectSysToolPocketByToolPocketId(toolId);
    }

    /**
     * 查询刀具加工参数列表
     * 
     * @param sysToolPocket 刀具加工参数
     * @return 刀具加工参数
     */
    @Override
    public List<SysToolPocket> selectSysToolPocketList(SysToolPocket sysToolPocket)
    {
        return sysToolPocketMapper.selectSysToolPocketList(sysToolPocket);
    }

    /**
     * 新增刀具加工参数
     * 
     * @param sysToolPocket 刀具加工参数
     * @return 结果
     */
    @Override
    public int insertSysToolPocket(SysToolPocketToSql sysToolPocket)
    {
        return sysToolPocketMapper.insertSysToolPocket(sysToolPocket);
    }

    /**
     * 修改刀具加工参数
     * 
     * @param sysToolPocket 刀具加工参数
     * @return 结果
     */
    @Override
    public int updateSysToolPocket(SysToolPocketToSql sysToolPocket)
    {
        return sysToolPocketMapper.updateSysToolPocket(sysToolPocket);
    }

    /**
     * 批量删除刀具加工参数
     * 
     * @param toolIds 需要删除的刀具加工参数主键
     * @return 结果
     */
    @Override
    public int deleteSysToolPocketByToolPocketIds(String[] toolIds)
    {
        return sysToolPocketMapper.deleteSysToolPocketByToolPocketIds(toolIds);
    }

    /**
     * 删除刀具加工参数信息
     * 
     * @param toolId 刀具加工参数主键
     * @return 结果
     */
    @Override
    public int deleteSysToolPocketByToolPocketId(String toolId)
    {
        return sysToolPocketMapper.deleteSysToolPocketByToolPocketId(toolId);
    }

    /**
     * Creo软件根据刀具id获取参数
     *
     * @param toolId 刀具id
     * @return 结果
     */
    @Override
    public SysToolPocket getParameter(String toolId) {
        return sysToolPocketMapper.getParameter(toolId);
    }
}
