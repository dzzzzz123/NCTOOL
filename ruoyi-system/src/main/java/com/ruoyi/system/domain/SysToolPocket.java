package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;


/**
 * 刀具加工参数对象 sys_tool_pocket
 *
 * @author dz
 * @date 2023-02-21
 */
@TableName(autoResultMap = true)
public class SysToolPocket extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 刀具加工参数id
     */
    private Long toolPocketId;

    /**
     * 刀具ID
     */
    @Excel(name = "刀具ID")
    private String toolId;

    /**
     * 特征名称
     */
    @Excel(name = "特征名称")
    private String featName;

    /**
     * 参数
     */
    @Excel(name = "参数")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private SysParameter parameter;

    public void setToolPocketId(Long toolPocketId) {
        this.toolPocketId = toolPocketId;
    }

    public Long getToolPocketId() {
        return toolPocketId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getToolId() {
        return toolId;
    }

    public void setFeatName(String featName) {
        this.featName = featName;
    }

    public String getFeatName() {
        return featName;
    }

    public SysParameter getParameter() {
        return parameter;
    }

    public void setParameter(SysParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("toolPocketId", getToolPocketId())
                .append("toolId", getToolId())
                .append("featName", getFeatName())
                .append("parameter", getParameter())
                .toString();
    }

    @Data
    public static class SysParameter {
        @Excel(name = "")
        @JsonProperty(value = "Coolant")
        private String coolant;
        @Excel(name = "")
        @JsonProperty(value = "FeedRate")
        private String feedRate;
        @Excel(name = "")
        @JsonProperty(value = "PeckDepth")
        private String peckDepth;
        @Excel(name = "")
        @JsonProperty(value = "SpindelSpeed")
        private String spindelSpeed;
    }
}

