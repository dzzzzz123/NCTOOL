package com.ruoyi.system.utils.transform;

/**
 * @author dz
 * @date 2023-04-20
 */
public class TransformTo7000Finishing extends TransformBaseUtil {

    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            if (content[i].startsWith("G84")) {
                i = processTAPTEETH(content, newStr, i);
            } else if (content[i].startsWith("G30X0.Y0.")) {
                newStr.append("G30Y0.");
            } else if (content[i].startsWith("G65P9220")) {
                i++;
            } else {
                TransformTo7000.replace99(content, newStr, i);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}
