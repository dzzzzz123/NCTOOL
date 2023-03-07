package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.ToolsConstants;
import com.ruoyi.common.constant.UserConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysTools;
import com.ruoyi.system.service.ISysToolsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 刀具管理Controller
 *
 * @author dz
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/system/tools")
public class SysToolsController extends BaseController {
    @Autowired
    private ISysToolsService sysToolsService;

    /**
     * 查询刀具管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tools:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTools sysTools) {
        startPage();
        List<SysTools> list = sysToolsService.selectSysToolsList(sysTools);
        return getDataTable(list);
    }

    /**
     * 导出刀具管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tools:export')")
    @Log(title = "刀具管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTools sysTools) {
        List<SysTools> list = sysToolsService.selectSysToolsList(sysTools);
        ExcelUtil<SysTools> util = new ExcelUtil<SysTools>(SysTools.class);
        util.exportExcel(response, list, "刀具管理数据");
    }

    /**
     * 获取刀具管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tools:query')")
    @GetMapping(value = "/{toolId}")
    public AjaxResult getInfo(@PathVariable("toolId") Long toolId) {
        return success(sysToolsService.selectSysToolsByToolId(toolId));
    }

    /**
     * 新增刀具管理
     */
    @PreAuthorize("@ss.hasPermi('system:tools:add')")
    @Log(title = "刀具管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysTools sysTools) {
        if (ToolsConstants.NOT_UNIQUE.equals(sysToolsService.checkToolsDescriptionUnique(sysTools)))
        {
            return error("新增刀具'" + sysTools.getToolDescription() + "'失败，刀具描述已存在");
        }else if(ToolsConstants.NOT_UNIQUE.equals(sysToolsService.checkToolsParameterUnique(sysTools))){
            return error("新增刀具'" + sysTools.getToolSap() + "'失败，刀具参数已存在");
        }
        return toAjax(sysToolsService.insertSysTools(sysTools));
    }

    /**
     * 修改刀具管理
     */
    @PreAuthorize("@ss.hasPermi('system:tools:edit')")
    @Log(title = "刀具管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysTools sysTools) {
        if (ToolsConstants.NOT_UNIQUE.equals(sysToolsService.checkToolsDescriptionUnique(sysTools)))
        {
            return error("新增刀具'" + sysTools.getToolDescription() + "'失败，刀具描述已存在");
        }else if(ToolsConstants.NOT_UNIQUE.equals(sysToolsService.checkToolsParameterUnique(sysTools))){
            return error("新增刀具'" + sysTools.getToolSap() + "'失败，刀具参数已存在");
        }
        return toAjax(sysToolsService.updateSysTools(sysTools));
    }

    /**
     * 删除刀具管理
     */
    @PreAuthorize("@ss.hasPermi('system:tools:remove')")
    @Log(title = "刀具管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{toolIds}")
    public AjaxResult remove(@PathVariable Long[] toolIds) {
        return toAjax(sysToolsService.deleteSysToolsByToolIds(toolIds));
    }
}
