package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysTools;

/**
 * 刀具管理Service接口
 * 
 * @author dz
 * @date 2023-02-14
 */
public interface ISysToolsService 
{
    /**
     * 查询刀具管理
     * 
     * @param toolId 刀具管理主键
     * @return 刀具管理
     */
    public SysTools selectSysToolsByToolId(Long toolId);

    /**
     * 查询刀具管理列表
     * 
     * @param sysTools 刀具管理
     * @return 刀具管理集合
     */
    public List<SysTools> selectSysToolsList(SysTools sysTools);

    /**
     * 新增刀具管理
     * 
     * @param sysTools 刀具管理
     * @return 结果
     */
    public int insertSysTools(SysTools sysTools);

    /**
     * 修改刀具管理
     * 
     * @param sysTools 刀具管理
     * @return 结果
     */
    public int updateSysTools(SysTools sysTools);

    /**
     * 批量删除刀具管理
     * 
     * @param toolIds 需要删除的刀具管理主键集合
     * @return 结果
     */
    public int deleteSysToolsByToolIds(Long[] toolIds);

    /**
     * 删除刀具管理信息
     * 
     * @param toolId 刀具管理主键
     * @return 结果
     */
    public int deleteSysToolsByToolId(Long toolId);

    /**
     * 校验刀具描述是否唯一
     * @param sysTools 刀具
     * @return {@link String}
     */
    public String checkToolsDescriptionUnique(SysTools sysTools);

    /**
     * 校验刀具参数是否唯一
     *
     * @param sysTools 刀具
     * @return {@link String}
     */
    public String checkToolsParameterUnique(SysTools sysTools);
}
