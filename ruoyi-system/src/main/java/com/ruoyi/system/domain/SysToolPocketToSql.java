package com.ruoyi.system.domain;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 刀具加工参数帮助对象
 *
 * @author dz
 * @date 2023-02-22
 */
@Data
public class SysToolPocketToSql extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 刀具ID
     */
    private String toolId;
    /**
     * 刀具描述
     */
    private String description;
    /**
     * 参数
     */
    private String parameter;

    public SysToolPocketToSql(SysToolPocket sysToolPocket) {
        this.toolId = sysToolPocket.getToolId();
        this.description = sysToolPocket.getDescription();
        if (sysToolPocket.getParameter() == null) {
            this.parameter = "1";
        } else {
            this.parameter = JSON.toJSONString(sysToolPocket.getParameter());
        }
    }
}
