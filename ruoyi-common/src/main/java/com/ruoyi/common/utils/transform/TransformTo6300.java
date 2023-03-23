package com.ruoyi.common.utils.transform;

import java.util.Arrays;
import java.util.Objects;

import static com.ruoyi.common.constant.TransformConstants.*;

/**
 * 将NC代码转换为6300机床使用的G代码
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformTo6300 extends TransformBaseUtil {

    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        String flag = "";
        int flag2 = 0;
        for (int i = 0; i < content.length; i++) {
            if (WEAR_DETECTION.containsKey(content[i])) {
                flag = content[i];
            }
            if (content[i].startsWith("T") && Objects.equals(content[i + 1], "M06")) {
                newStr.append("G91G30X0.Y0.Z0.").append("\r\n").append("G54G90G0B0.").append("\r\n").append(content[i]).append("\r\n");
                i++;
                newStr.append(content[i]).append("\r\n");
                i++;
                newStr.append(content[i]).append("\r\n");
                // if (Arrays.asList(TOOLS_TO_SET_DETECTION).contains(content[i - 2]) && flag2 > 1) {
                if (flag2 > 1) {
                    newStr.append(TOOL_SET_DETECTION);
                    continue;
                }
                flag2++;
            } else if (content[i].startsWith("G43Z35.H")) {
                newStr.append("G43Z35.H1");
            } else if (content[i].startsWith("G84")) {
                newStr.append(TAPPING_TEETH).append(content[i]).append("\r\n");
                for (int j = 0; j < 4; j++) {
                    i++;
                    newStr.append(content[i]).append("\r\n");
                }
                newStr.append("G94");
            } else if (Objects.equals(content[i], "M01") && !Objects.equals(flag, "")) {
                newStr.append(WEAR_DETECTION.get(flag)).append("M01");
                flag = "";
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}