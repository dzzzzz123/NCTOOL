package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 刀具管理对象 sys_tools
 * 
 * @author dz
 * @date 2023-02-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysTools extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 刀具id */
    private Long toolId;

    /** 刀具在机床6800的位置 */
    @Excel(name = "刀具在机床6300的位置")
    private Long nh6300;

    /** 刀具在机床650的位置 */
    @Excel(name = "刀具在机床7000的位置")
    private Long nv7000;

    /** 刀具在机床7000的位置 */
    @Excel(name = "刀具在机床655的位置")
    private Long mazak655;

    /** 刀具描述 */
    @Excel(name = "刀具描述")
    private String toolDescription;

    /** 删除标准，1删除，0正常 */
    private String delFlag;

}
