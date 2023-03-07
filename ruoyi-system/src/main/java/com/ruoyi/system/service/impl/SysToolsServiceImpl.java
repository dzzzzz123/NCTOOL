package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.constant.ToolsConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysToolsMapper;
import com.ruoyi.system.domain.SysTools;
import com.ruoyi.system.service.ISysToolsService;

/**
 * 刀具管理Service业务层处理
 * 
 * @author dz
 * @date 2023-02-14
 */
@Service
public class SysToolsServiceImpl implements ISysToolsService 
{
    @Autowired
    private SysToolsMapper sysToolsMapper;

    /**
     * 查询刀具管理
     * 
     * @param toolId 刀具管理主键
     * @return 刀具管理
     */
    @Override
    public SysTools selectSysToolsByToolId(Long toolId)
    {
        return sysToolsMapper.selectSysToolsByToolId(toolId);
    }

    /**
     * 查询刀具管理列表
     * 
     * @param sysTools 刀具管理
     * @return 刀具管理
     */
    @Override
    public List<SysTools> selectSysToolsList(SysTools sysTools)
    {
        return sysToolsMapper.selectSysToolsList(sysTools);
    }

    /**
     * 新增刀具管理
     * 
     * @param sysTools 刀具管理
     * @return 结果
     */
    @Override
    public int insertSysTools(SysTools sysTools)
    {
        return sysToolsMapper.insertSysTools(sysTools);
    }

    /**
     * 修改刀具管理
     * 
     * @param sysTools 刀具管理
     * @return 结果
     */
    @Override
    public int updateSysTools(SysTools sysTools)
    {
        return sysToolsMapper.updateSysTools(sysTools);
    }

    /**
     * 批量删除刀具管理
     * 
     * @param toolIds 需要删除的刀具管理主键
     * @return 结果
     */
    @Override
    public int deleteSysToolsByToolIds(Long[] toolIds)
    {
        return sysToolsMapper.deleteSysToolsByToolIds(toolIds);
    }

    /**
     * 删除刀具管理信息
     * 
     * @param toolId 刀具管理主键
     * @return 结果
     */
    @Override
    public int deleteSysToolsByToolId(Long toolId)
    {
        return sysToolsMapper.deleteSysToolsByToolId(toolId);
    }

    /**
     * 校验刀具描述是否唯一
     *
     * @param sysTools 刀具
     * @return {@link String}
     */
    @Override
    public String checkToolsDescriptionUnique(SysTools sysTools) {
        Long toolsId = StringUtils.isNull(sysTools.getToolId()) ? -1L : sysTools.getToolId();
        SysTools description = sysToolsMapper.checkToolsDescriptionUnique(sysTools.getToolDescription());
        if (StringUtils.isNotNull(description) && description.getToolId().longValue() != toolsId.longValue())
        {
            return ToolsConstants.NOT_UNIQUE;
        }
        return ToolsConstants.UNIQUE;
    }

    /**
     * 校验刀具参数是否唯一
     *
     * @param sysTools 刀具
     * @return {@link String}
     */
    @Override
    public String checkToolsParameterUnique(SysTools sysTools) {
        Long toolsId = StringUtils.isNull(sysTools.getToolId()) ? -1L : sysTools.getToolId();
        SysTools parameter = sysToolsMapper.checkToolsParameterUnique(sysTools.getToolSap());
        if (StringUtils.isNotNull(parameter) && parameter.getToolId().longValue() != toolsId.longValue())
        {
            return ToolsConstants.NOT_UNIQUE;
        }
        return ToolsConstants.UNIQUE;
    }
}
