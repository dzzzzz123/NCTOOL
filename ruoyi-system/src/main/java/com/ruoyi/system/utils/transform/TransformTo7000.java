package com.ruoyi.system.utils.transform;

import java.util.Objects;

import static com.ruoyi.system.constant.TransformConstants.CIRCULATING_DRILLING_NV7000_T_VALUE;
import static com.ruoyi.system.constant.TransformConstants.TOOL_BREAK_DETECTION;

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
                newStr.append("G91G30X0.Y0.Z0.\r\n");
            }
            if (content[i].startsWith("G84")) {
                i = processTAPTEETH(content, newStr, i);
            }
            // 更新内容：G83->G81出现重复，falg重置出现问题，添加break;
            else if (content[i].startsWith("G81")) {
                int j = 1;
                boolean flag = false;
                while (i - j >= 0 && j <= 30) {
                    if (CIRCULATING_DRILLING_NV7000_T_VALUE.containsKey(content[i - j])) {
                        flag = true;
                        newStr.append(content[i].replace("G81", "G83")).append(CIRCULATING_DRILLING_NV7000_T_VALUE.get(content[i - j]));
                        break;
                    }
                    j++;
                }
                if (!flag) {
                    newStr.append(content[i]);
                }
            } else if (content[i].contains("D#51999")) {
                process51999(content, newStr, i);
            } else if (content[i].startsWith("G30G91") && content[i + 1].startsWith("M01")) {
                int j = 1;
                boolean isMatch = false;
                boolean hasMatch = false;
                while (i - j >= 0 && j <= 50 && !hasMatch) {
                    if (content[i - j].startsWith("T")) {
                        for (String s : TOOL_BREAK_DETECTION.keySet()) {
                            if (Objects.equals(content[i - j], s)) {
                                isMatch = true;
                                newStr.append(content[i]).append("\r\n").append(TOOL_BREAK_DETECTION.get(s));
                            }
                        }
                        hasMatch = true;
                    }
                    j++;
                }
                if (!isMatch) {
                    newStr.append(content[i]);
                }
            } else {
                replace99(content, newStr, i);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }


    /**
     * 将7000机床的刀具前面加上99
     *
     * @param content 每行G代码
     * @param newStr  生成出来新的每行G代码
     * @param i       G代码指针
     */
    static void replace99(String[] content, StringBuilder newStr, int i) {
        if (Objects.equals(content[i], "T1") || Objects.equals(content[i], "G90T1")) {
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
    }
}