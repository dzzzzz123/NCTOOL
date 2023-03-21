package com.ruoyi.common.utils.transform;

import java.util.Arrays;

import static com.ruoyi.common.constant.TransformConstants.MAZAK655_M_TO_DELETE;
import static com.ruoyi.common.constant.TransformConstants.MAZAK655_T_TO_CHANGE;

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
            if (content[i].contains("T96")) {
                newStr.append(content[i].replace("T96", MAZAK655_T_TO_CHANGE.get("T96")));
            } else if (content[i].contains("T67")) {
                newStr.append(content[i].replace("T67", MAZAK655_T_TO_CHANGE.get("T67")));
            } else if (content[i].contains("40")) {
                if (content[i].contains(".H")) {
                    newStr.append(content[i].replaceFirst("40", "84"));
                } else if (content[i].contains("T40")) {
                    newStr.append(content[i].replace("T40", "T84"));
                } else {
                    newStr.append(content[i]);
                }
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}