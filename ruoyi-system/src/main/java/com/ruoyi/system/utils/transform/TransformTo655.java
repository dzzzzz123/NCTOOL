package com.ruoyi.system.utils.transform;


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
            if (false) {

            } else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}