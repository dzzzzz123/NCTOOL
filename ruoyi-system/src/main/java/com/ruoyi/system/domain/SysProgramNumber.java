package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;

/**
 * Creo加工图参数实体类
 *
 * @author dz
 * @date 2023-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysProgramNumber extends BaseEntity {
    private Integer numberOne;
    private Integer numberTwo;

    @Override
    public String toString() {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(4);
        formatter.setGroupingUsed(false);
        return numberOne + formatter.format(numberTwo);
    }
}
