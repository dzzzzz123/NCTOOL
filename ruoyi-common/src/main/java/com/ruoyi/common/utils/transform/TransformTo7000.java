package com.ruoyi.common.utils.transform;

import com.ruoyi.common.constant.TransformConstants;

import java.util.Arrays;
import java.util.Objects;

/**
 * 将NC代码转换为7000机床使用的G代码
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformTo7000 extends TransformBaseUtil {

    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            if (content[i].contains("G28G91")) {
                newStr.append(content[i].replace("G28G91", "G30G91"));
            } else if (Arrays.asList(TransformConstants.NV7000_M_TO_DELETE).contains(content[i])) {
            } else if (TransformConstants.NV7000_M_TO_CHANGE.containsKey(content[i])) {
                for (String s : TransformConstants.NV7000_M_TO_CHANGE.keySet()) {
                    if (Objects.equals(content[i], s)) {
                        newStr.append(TransformConstants.NV7000_M_TO_CHANGE.get(s));
                    }
                }
            } else if (Objects.equals(content[i], TransformConstants.ORIGIN_M_PROGCAT)) {
                newStr.append(TransformConstants.NV7000_M_PROGCAT);
            }else if (i == 2) {
                newStr.append("(FILENAME E:\\NV7000\\").append(content[i].split("\\\\")[2]);
            }
            else if (i == 7) {
                newStr.append(content[i]).append("\r\n").append("(Processed by Platform: ").append(getTime()).append(")");
            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}