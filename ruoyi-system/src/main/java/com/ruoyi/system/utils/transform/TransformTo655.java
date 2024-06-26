package com.ruoyi.system.utils.transform;

import static com.ruoyi.system.constant.TransformConstants.CIRCULATING_DRILLING_MAZAK655_T_VALUE;

/**
 * 将NC代码转换为655机床使用的G代码
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformTo655 extends TransformBaseUtil {

    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            // 更新内容：G83->G81出现重复，flag重置出现问题，添加break;
            if (content[i].startsWith("G81")) {
                int j = 1;
                boolean flag3 = false;
                while (i - j >= 0 && j <= 30) {
                    if (CIRCULATING_DRILLING_MAZAK655_T_VALUE.containsKey(content[i - j])) {
                        flag3 = true;
                        newStr.append(content[i].replace("G81", "G83")).append(CIRCULATING_DRILLING_MAZAK655_T_VALUE.get(content[i - j]));
                        break;
                    }
                    j++;
                }
                if (!flag3) {
                    newStr.append(content[i]);
                }
            } else if (content[i].contains("D#51999")) {
                process51999(content, newStr, i);
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}