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
        processAllReplace(content);
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            // 给刀具最开始的(TOOL TABLE SUMMARY)加,
            if (Objects.equals(content[i], "(TOOL NUMBER   TOOL ID OFFSET NO  TOOL COMMENT)")) {
                newStr.append(content[i]).append("\r\n");
                i++;
                while (!content[i].startsWith("(TOOL NAME")) {
                    if (content[i].startsWith("(67")) {
                        content[i] = content[i].replace("(67", "(56");
                    } else if (content[i].startsWith("(96")) {
                        content[i] = content[i].replace("(96", "(44");
                    } else if (content[i].startsWith(" ")) {
                        i++;
                        continue;
                    }
                    String[] str = content[i].split("\\s+", 2);
                    newStr.append(str[0]).append(", ").append(str[1]).append("\r\n");
                    i++;
                }
                newStr.append(content[i]);
            }
            // 在没一把刀之前都加上G91G30X0.Y0.Z0.
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
            } else if (Objects.equals(content[i], ORIGIN_M_PROGCAT)) {
                newStr.append(NV7000_M_PROGCAT);
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }

    /**
     * 为所有需要修改的字符串修改
     *
     * @param content 用来处理的字符串
     */
    static void processAllReplace(String[] content) {
        for (int i = 0; i < content.length; i++) {
            for (String s : NV7000_ALL_TO_CHANGE.keySet()) {
                if (content[i].contains(s) && !content[i].startsWith("(")) {
                    content[i] = content[i].replace(s, NV7000_ALL_TO_CHANGE.get(s));
                }
            }
        }
    }
}