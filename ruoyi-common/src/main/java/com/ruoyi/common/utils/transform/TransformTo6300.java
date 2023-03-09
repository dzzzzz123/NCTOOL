package com.ruoyi.common.utils.transform;

import com.ruoyi.common.constant.TransformConstants;

import java.util.Arrays;
import java.util.Objects;

/**
 * 将NC代码转换为6300机床使用的G代码
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformTo6300 extends TransformBaseUtil {


    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            if (content[i].startsWith("T") && Objects.equals(content[i + 1], "M06")) {
                newStr.append("G91G30X0.Y0.Z0.").append("\r\n").append("G54G90G0B0").append("\r\n").append(content[i]).append("\r\n");
            } else if (Arrays.asList(TransformConstants.NH6300_M_TO_DELETE).contains(content[i])) {
            } else if (Objects.equals(content[i], "G65P8881")) {
                newStr.append("M98P8881(Z AXIS HEIGHT MEASUREMENT)").append("\r\n");
            } else if (content[i].startsWith("G43Z35.H")) {
                newStr.append("G43Z35.H1").append("\r\n");
            } else if (TransformConstants.NH6300_M_TO_CHANGE.containsKey(content[i])) {
                for (String s : TransformConstants.NH6300_M_TO_CHANGE.keySet()) {
                    if (Objects.equals(content[i], s)) {
                        newStr.append(TransformConstants.NH6300_M_TO_CHANGE.get(s)).append("\r\n");
                    }
                }
            } else if (Objects.equals(content[i], TransformConstants.ORIGIN_M_PROGCAT)) {
                newStr.append(TransformConstants.NH6300_M_PROGCAT).append("\r\n");
            } else if (i == 7) {
                newStr.append(content[i]).append("\r\n").append("(Processed by Platform: ").append(getTime()).append(")").append("\r\n");
            } else {
                newStr.append(content[i]).append("\r\n");
            }
        }
        return newStr;
    }
}
