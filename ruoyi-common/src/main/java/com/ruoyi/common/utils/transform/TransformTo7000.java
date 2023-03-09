package com.ruoyi.common.utils.transform;

import com.ruoyi.common.constant.TransformConstants;


import java.util.Arrays;
import java.util.Objects;

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
            if (true) {

            } else if (Arrays.asList(TransformConstants.NV7000_M_TO_DELETE).contains(content[i])) {
            } else if (TransformConstants.NV7000_M_TO_CHANGE.containsKey(content[i])) {
                for (String s : TransformConstants.NV7000_M_TO_CHANGE.keySet()) {
                    if (Objects.equals(content[i], s)) {
                        newStr.append(TransformConstants.NV7000_M_TO_CHANGE.get(s)).append("\r\n");
                    }
                }
            } else if (Objects.equals(content[i], TransformConstants.ORIGIN_M_PROGCAT)) {
                newStr.append(TransformConstants.NV7000_M_PROGCAT).append("\r\n");
            } else if (i == 7) {
                newStr.append(content[i]).append("\r\n").append("(Processed by Platform: ").append(getTime()).append(")").append("\r\n");
            } else {
                newStr.append(content[i]).append("\r\n");
            }
        }
        return newStr;
    }
}
