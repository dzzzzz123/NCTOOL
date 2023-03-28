package com.ruoyi.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.system.domain.SysToolPocketToSql;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysToolPocket;
import com.ruoyi.system.service.ISysToolPocketService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 刀具加工参数Controller
 *
 * @author dz
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/system/pocket")
public class SysToolPocketController extends BaseController {
    @Autowired
    private ISysToolPocketService sysToolPocketService;

    /**
     * 查询刀具加工参数列表
     */
    @PreAuthorize("@ss.hasPermi('system:pocket:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysToolPocket sysToolPocket) {
        startPage();
        List<SysToolPocket> list = sysToolPocketService.selectSysToolPocketList(sysToolPocket);
        return getDataTable(list);
    }

    /**
     * 导出刀具加工参数列表
     */
    @PreAuthorize("@ss.hasPermi('system:pocket:export')")
    @Log(title = "刀具加工参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysToolPocket sysToolPocket) {
        List<SysToolPocket> list = sysToolPocketService.selectSysToolPocketList(sysToolPocket);
        ExcelUtil<SysToolPocket> util = new ExcelUtil<SysToolPocket>(SysToolPocket.class);
        util.exportExcel(response, list, "刀具加工参数数据");
    }

    /**
     * 获取刀具加工参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pocket:query')")
    @GetMapping(value = "/{toolId}")
    public AjaxResult getInfo(@PathVariable("toolId") String toolId) {
        return success(sysToolPocketService.selectSysToolPocketByToolPocketId(toolId));
    }

    /**
     * 新增刀具加工参数
     */
    @PreAuthorize("@ss.hasPermi('system:pocket:add')")
    @Log(title = "刀具加工参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysToolPocket sysToolPocket) {
        return toAjax(sysToolPocketService.insertSysToolPocket(new SysToolPocketToSql(sysToolPocket)));
    }

    /**
     * 修改刀具加工参数
     */
    @PreAuthorize("@ss.hasPermi('system:pocket:edit')")
    @Log(title = "刀具加工参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysToolPocket sysToolPocket) {
        return toAjax(sysToolPocketService.updateSysToolPocket(new SysToolPocketToSql(sysToolPocket)));
    }

    /**
     * 删除刀具加工参数
     */
    @PreAuthorize("@ss.hasPermi('system:pocket:remove')")
    @Log(title = "刀具加工参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{toolIds}")
    public AjaxResult remove(@PathVariable String[] toolIds) {
        return toAjax(sysToolPocketService.deleteSysToolPocketByToolPocketIds(toolIds));
    }

    /**
     * Creo软件外部接口获取刀具加工参数
     * Coolant          COOLANT_OPTION
     * FeedRate         CUT_FEED
     * PeckDepth        PECK_DEPTH
     * SpindelSpeed     SPINDLE_SPEED
     * @param toolId 刀具id
     * @return 加工参数
     */
    @Anonymous
    @GetMapping("/Creo/{toolId}")
    public AjaxResult getParameter(@PathVariable String toolId) {
        AjaxResult ajax = new AjaxResult();
        SysToolPocket sysToolPocket = sysToolPocketService.getParameter(toolId);
        SysToolPocket.SysParameter parameter = sysToolPocket.getParameter();
        Map<String, String> modifiedParameter = new HashMap<>(4);
        modifiedParameter.put("COOLANT_OPTION", parameter.getCoolant());
        modifiedParameter.put("CUT_FEED", parameter.getFeedRate());
        modifiedParameter.put("PECK_DEPTH", parameter.getPeckDepth());
        modifiedParameter.put("SPINDLE_SPEED", parameter.getSpindelSpeed());
        ajax.put("Parameter", modifiedParameter);
        return ajax;
    }
}