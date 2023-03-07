package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysToolPocket;
import com.ruoyi.system.domain.SysToolPocketToSql;

/**
 * 刀具加工参数Mapper接口
 * 
 * @author dz
 * @date 2023-02-21
 */
public interface SysToolPocketMapper 
{
    /**
     * 查询刀具加工参数
     * 
     * @param toolPocketId 刀具加工参数主键
     * @return 刀具加工参数
     */
    public SysToolPocket selectSysToolPocketByToolPocketId(Long toolPocketId);

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
     * 删除刀具加工参数
     * 
     * @param toolPocketId 刀具加工参数主键
     * @return 结果
     */
    public int deleteSysToolPocketByToolPocketId(Long toolPocketId);

    /**
     * 批量删除刀具加工参数
     * 
     * @param toolPocketIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysToolPocketByToolPocketIds(Long[] toolPocketIds);

    /**
     * Creo软件根据刀具id获取参数
     * @param toolId 刀具id
     * @return 结果
     */
    public SysToolPocket getParameter(String toolId);
}
