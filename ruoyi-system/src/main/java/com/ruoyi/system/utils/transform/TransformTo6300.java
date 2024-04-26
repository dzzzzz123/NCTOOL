package com.ruoyi.system.utils.transform;

import java.util.Objects;

import static com.ruoyi.system.constant.TransformConstants.TOOL_SET_DETECTION;
import static com.ruoyi.system.constant.TransformConstants.WEAR_DETECTION;

/**
 * 将NC代码转换为6300机床使用的G代码
 * 内容已经很混乱，没办法修改了，将就用吧，需要什么就直接if
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformTo6300 extends TransformBaseUtil {

    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        String flag = "";
        // 判断是否是第一个刀具检测的标识
        int flag2 = 0;
        for (int i = 0; i < content.length; i++) {
            if (WEAR_DETECTION.containsKey(content[i])) {
                flag = content[i];
            }
            if (content[i].contains("D#51999")) {
                newStr.append(content[i].replace("#51999", "1"));
            } else if (content[i].startsWith("T") && Objects.equals(content[i + 1], "M06")) {
                newStr.append("G91G30X0.Y0.Z0.").append("\r\n").append("G54G90G0B0.").append("\r\n").append(content[i]).append("\r\n");
                i++;
                newStr.append(content[i]).append("\r\n");
                i++;
                newStr.append(content[i]).append("\r\n");
                if (flag2 > 1) {
                    newStr.append(TOOL_SET_DETECTION);
                    continue;
                }
                flag2++;
            } else if (content[i].startsWith("G84")) {
                i = processTAPTEETH(content, newStr, i);
            } else if (Objects.equals(content[i], "M01") && !Objects.equals(flag, "")) {
                newStr.append(WEAR_DETECTION.get(flag)).append("M01");
                flag = "";
            } else if (content[i].startsWith("#610=")) {
                String tempStr = "";
                if (content[i].startsWith("#610=2")) {
                    tempStr = "M98P8991(Z AXIS HEIGHT MEASUREMENT)";
                } else if (content[i].startsWith("#610=3")) {
                    tempStr = "M98P8992(Z AXIS HEIGHT MEASUREMENT)";
                } else {
                    newStr.append(content[i]).append("\r\n");
                    continue;
                }
                while (true) {
                    if (content[i].equals("G65P8881")) {
                        newStr.append(tempStr).append("\r\n");
                        break;
                    }
                    newStr.append(content[i]).append("\r\n");
                    i++;
                }
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}