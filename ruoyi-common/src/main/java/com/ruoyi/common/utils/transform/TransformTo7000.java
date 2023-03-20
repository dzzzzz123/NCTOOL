package com.ruoyi.common.utils.transform;


import java.util.Arrays;
import java.util.Objects;

import static com.ruoyi.common.constant.TransformConstants.*;
import static com.ruoyi.common.constant.TransformConstants.NV7000_ALL_TO_CHANGE;

/**
 * 将NC代码转换为7000机床使用的G代码
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformTo7000 extends TransformBaseUtil {

    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            // 在每一把刀之前都加上G91G30X0.Y0.Z0.
            if (content[i].startsWith("T")) {
                newStr.append("G91G30X0.Y0.Z0.\n");
            }

            if (Arrays.asList(NV7000_M_TO_DELETE).contains(content[i])) {
            } else if (content[i].contains("T1") && !content[i].contains("(") && !content[i].contains("T10")) {
                newStr.append(content[i].replace("T1", "T9901"));
            } else if (content[i].contains("96")) {
                if (content[i].contains(".H")) {
                    newStr.append(content[i].replaceFirst("96", "44"));
                } else if (content[i].contains("T96")) {
                    newStr.append(content[i].replace("T96", "T9944"));
                } else {
                    newStr.append(content[i]);
                }
            } else if (content[i].contains("67")) {
                if (content[i].contains(".H")) {
                    newStr.append(content[i].replaceFirst("67", "56"));
                } else if (content[i].contains("T67")) {
                    newStr.append(content[i].replace("T67", "T9956"));
                } else {
                    newStr.append(content[i]);
                }
            } else if (content[i].startsWith("G84")) {
                newStr.append(TAPPING_TEETH).append(content[i]);
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}