package com.ruoyi.system.utils.transform;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ruoyi.system.constant.TransformConstants.*;

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
                newStr.append("G91G30X0.Y0.Z0.").append("\n").append("G54G90G0B0.").append("\n").append(content[i]).append("\n");
                i++;
                newStr.append(content[i]).append("\n");
                i++;
                newStr.append(content[i]).append("\n");
                if (flag2 > 1) {
                    newStr.append(TOOL_SET_DETECTION);
                    continue;
                }
                flag2++;
            } else if (content[i].startsWith("G84")) {
                int j = 1;
                String mPattern = ".*S\\d{3}M\\d{2}$";
                Pattern pattern = Pattern.compile(mPattern);
                while (i - j >= 0 && j <= 50) {
                    Matcher matcher = pattern.matcher(content[i - j]);
                    if (matcher.matches()) {
                        String msCode = matcher.group().substring(matcher.group().indexOf("S"), matcher.group().indexOf("S") + 4);
                        newStr.append(TAPTEETH).append(msCode).append("\n").append(content[i]).append("\n");
                        break;
                    }
                    if (j == 49) {
                        String msCode = "S243";
                        newStr.append(TAPTEETH).append(msCode).append("\n").append(content[i]).append("\n");
                    }
                    j++;
                }
                i++;
                while (i < content.length && !content[i].startsWith("G80")) {
                    newStr.append(content[i]).append("\n");
                    i++;
                }
                newStr.append("G80").append("\n").append("G94");
            } else if (Objects.equals(content[i], "M01") && !Objects.equals(flag, "")) {
                newStr.append(WEAR_DETECTION.get(flag)).append("M01");
                flag = "";
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\n");
        }
        return newStr;
    }
}