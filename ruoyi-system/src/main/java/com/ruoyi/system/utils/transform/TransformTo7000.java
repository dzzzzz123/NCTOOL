package com.ruoyi.system.utils.transform;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ruoyi.system.constant.TransformConstants.*;

/**
 * 将NC代码转换为7000机床使用的G代码,内容以及非常混乱了，但是没有很好的办法
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
            } else if (content[i].startsWith("G81")) {
                int j = 1;
                boolean flag = false;
                while (i - j >= 0 && j <= 30) {
                    if (CIRCULATING_DRILLING_NV7000_T_VALUE.containsKey(content[i - j])) {
                        flag = true;
                        newStr.append(content[i].replace("G81", "G83")).append(CIRCULATING_DRILLING_NV7000_T_VALUE.get(content[i - j]));
                    }
                    j++;
                }
                if (!flag) {
                    newStr.append(content[i]);
                }
            } else if (content[i].contains("D#51999")) {
                int j = 1;
                boolean isMatch = false;
                while (i - j >= 0 && j <= 50) {
                    String mPattern = "(?<=\\.H)\\d+";
                    Pattern pattern = Pattern.compile(mPattern);
                    Matcher matcher = pattern.matcher(content[i - j]);
                    if (matcher.find()) {
                        isMatch = true;
                        String hCode = matcher.group();
                        newStr.append(content[i].replace("#51999", hCode));
                    }
                    j++;
                }
                if (!isMatch) {
                    newStr.append(content[i]);
                }
            } else if (content[i].startsWith("G30G91") && content[i + 1].startsWith("M01")) {
                int j = 1;
                boolean isMatch = false;
                while (i - j >= 0 && j <= 50) {
                    if (content[i - j].startsWith("T")) {
                        for (String s : TOOL_BREAK_DETECTION.keySet()) {
                            if (Objects.equals(content[i - j], s)) {
                                isMatch = true;
                                newStr.append(content[i]).append("\n").append(TOOL_BREAK_DETECTION.get(s));
                            }
                        }
                    }
                    j++;
                }
                if (!isMatch) {
                    newStr.append(content[i]);
                }
            } else if (Objects.equals(content[i], "T1") || Objects.equals(content[i], "G90T1")) {
                newStr.append(content[i].replace("T1", "T9901"));
            } else if (Objects.equals(content[i], "T3") || Objects.equals(content[i], "G90T3")) {
                newStr.append(content[i].replace("T3", "T9903"));
            } else if (Objects.equals(content[i], "T5") || Objects.equals(content[i], "G90T5")) {
                newStr.append(content[i].replace("T5", "T9914"));
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