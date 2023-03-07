package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * vue-diff帮助类
 * @author dz
 * @date 2023-03-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDiffFiles {
    private String oldFileName;
    private String newFileName;
    private File oldFile;
    private File newFile;
}
