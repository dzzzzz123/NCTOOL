package com.ruoyi.system.service;

import java.util.HashMap;
import java.util.List;
import com.ruoyi.system.domain.SysToolPocket;
import com.ruoyi.system.domain.SysToolPocketToSql;

/**
 * 刀具加工参数Service接口
 * 
 * @author dz
 * @date 2023-02-21
 */
public interface ISysToolPocketService 
{
    /**
     * 查询刀具加工参数
     * 
     * @param toolId 刀具加工参数主键
     * @return 刀具加工参数
     */
    public SysToolPocket selectSysToolPocketByToolPocketId(String toolId);

    /**
     * 查询刀具加工参数列表
     * 
     * @param sysToolPocket 刀具加工参数
     * @return 刀具加工参数集合
     */
    public List<SysToolPocket> selectSysToolPocketList(SysToolPocket sysToolPocket);

    /**
     * 新增刀具加工参数
     * 
     * @param sysToolPocket 刀具加工参数
     * @return 结果
     */
    public int insertSysToolPocket(SysToolPocketToSql sysToolPocket);

    /**
     * 修改刀具加工参数
     * 
     * @param sysToolPocket 刀具加工参数
     * @return 结果
     */
    public int updateSysToolPocket(SysToolPocketToSql sysToolPocket);

    /**
     * 批量删除刀具加工参数
     * 
     * @param toolIds 需要删除的刀具加工参数主键集合
     * @return 结果
     */
    public int deleteSysToolPocketByToolPocketIds(String[] toolIds);

    /**
     * 删除刀具加工参数信息
     * 
     * @param toolId 刀具加工参数主键
     * @return 结果
     */
    public int deleteSysToolPocketByToolPocketId(String toolId);

    /**
     * Creo软件根据刀具id获取参数
     * @param toolId 刀具id
     * @return 结果
     */
    public SysToolPocket getParameter(String toolId);

}
