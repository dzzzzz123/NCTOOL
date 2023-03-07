package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 刀具管理对象 sys_tools
 * 
 * @author dz
 * @date 2023-02-14
 */
public class SysTools extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 刀具id */
    private Long toolId;

    /** 刀具在机床6800的位置 */
    @Excel(name = "刀具在机床6800的位置")
    private Long pocket6800;

    /** 刀具在机床650的位置 */
    @Excel(name = "刀具在机床650的位置")
    private Long pocket650;

    /** 刀具在机床7000的位置 */
    @Excel(name = "刀具在机床7000的位置")
    private Long pocket7000;

    /** 刀具在机床haas的位置 */
    @Excel(name = "刀具在机床haas的位置")
    private Long pocketHaas;

    /** 刀具描述 */
    @Excel(name = "刀具描述")
    private String toolDescription;

    /** 刀具参数 */
    @Excel(name = "刀具参数")
    private String toolSap;

    /** 删除标准，1删除，0正常 */
    private String delFlag;

    public void setToolId(Long toolId) 
    {
        this.toolId = toolId;
    }

    public Long getToolId() 
    {
        return toolId;
    }
    public void setPocket6800(Long pocket6800) 
    {
        this.pocket6800 = pocket6800;
    }

    public Long getPocket6800() 
    {
        return pocket6800;
    }
    public void setPocket650(Long pocket650) 
    {
        this.pocket650 = pocket650;
    }

    public Long getPocket650() 
    {
        return pocket650;
    }
    public void setPocket7000(Long pocket7000) 
    {
        this.pocket7000 = pocket7000;
    }

    public Long getPocket7000() 
    {
        return pocket7000;
    }
    public void setPocketHaas(Long pocketHaas) 
    {
        this.pocketHaas = pocketHaas;
    }

    public Long getPocketHaas() 
    {
        return pocketHaas;
    }
    public void setToolDescription(String toolDescription) 
    {
        this.toolDescription = toolDescription;
    }

    public String getToolDescription() 
    {
        return toolDescription;
    }
    public void setToolSap(String toolSap) 
    {
        this.toolSap = toolSap;
    }

    public String getToolSap() 
    {
        return toolSap;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("toolId", getToolId())
            .append("pocket6800", getPocket6800())
            .append("pocket650", getPocket650())
            .append("pocket7000", getPocket7000())
            .append("pocketHaas", getPocketHaas())
            .append("toolDescription", getToolDescription())
            .append("toolSap", getToolSap())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
