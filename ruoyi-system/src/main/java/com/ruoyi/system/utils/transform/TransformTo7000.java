package com.ruoyi.system.utils.transform;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ruoyi.system.constant.TransformConstants.*;

/**
 * 将NC代码转换为7000机床使用的G代码,内容以及非常混乱了，但是没有很好的办法
 *
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
            if (content[i].startsWith("G84")) {
                int j = 1;
                Pattern pattern = Pattern.compile(M_PATTERN);
                while (i - j >= 0 && j <= 10) {
                    Matcher matcher = pattern.matcher(content[i - j]);
                    if (matcher.matches()) {
                        String msCode = matcher.group().substring(matcher.group().indexOf("S"), matcher.group().indexOf("S") + 4);
                        newStr.append(TAPTEETH).append(msCode).append("\n").append(content[i]).append("\n");
                        break;
                    }
                    j++;
                }
                i++;
                while (i < content.length && !content[i].startsWith("G80")) {
                    newStr.append(content[i]).append("\n");
                    i++;
                }
                newStr.append("G80").append("\n").append("G94");
            } else if (content[i].startsWith("G81")) {
                int j = 1;
                boolean flag = false;
                while (i - j >= 0 && j <= 30) {
                    if (CIRCULATING_DRILLING_T_VALUE.containsKey(content[i - j])) {
                        flag = true;
                        newStr.append(content[i].replace("G81", "G83")).append(CIRCULATING_DRILLING_T_VALUE.get(content[i - j]));
                    }
                    j++;
                }
                if (!flag) {
                    newStr.append(content[i]);
                }
            } else if (Objects.equals(content[i], "T1") || Objects.equals(content[i], "G90T1")) {
                newStr.append(content[i].replace("T1", "T9901"));
            } else if (Objects.equals(content[i], "T3") || Objects.equals(content[i], "G90T3")) {
                newStr.append(content[i].replace("T3", "T9903"));
            } else if (Objects.equals(content[i], "T5") || Objects.equals(content[i], "G90T5")) {
                newStr.append(content[i].replace("T5", "T9916"));
            } else if (Objects.equals(content[i], "T8") || Objects.equals(content[i], "G90T8")) {
                newStr.append(content[i].replace("T8", "T9908"));
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\n");
        }
        return newStr;
    }
}