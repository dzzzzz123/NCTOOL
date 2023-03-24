package com.ruoyi.common.utils.transform;

import java.util.Objects;

import static com.ruoyi.common.constant.TransformConstants.TAPPING_TEETH;

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
            // 在每一把刀之前都加上G91G30X0.Y0.Z0.
            if (content[i].startsWith("T")) {
                newStr.append("G91G30X0.Y0.Z0.\n");
            }
            if (content[i].startsWith("G84")) {
                newStr.append(TAPPING_TEETH).append(content[i]).append("\r\n");
                for (int j = 0; j < 4; j++) {
                    i++;
                    newStr.append(content[i]).append("\r\n");
                }
                newStr.append("G94");
            } else if (Objects.equals(content[i], "T1") || Objects.equals(content[i], "G90T1") ) {
                newStr.append(content[i].replace("T1", "T9901"));
            } else if (Objects.equals(content[i], "T3") || Objects.equals(content[i], "G90T3")) {
                newStr.append(content[i].replace("T3", "T9903"));
            } else if (Objects.equals(content[i], "T5") || Objects.equals(content[i], "G90T5")) {
                newStr.append(content[i].replace("T5", "T9916"));
            } else if (Objects.equals(content[i], "T8") || Objects.equals(content[i], "G90T8") ) {
                newStr.append(content[i].replace("T8", "T9908"));
            }  else {
                newStr.append(content[i]);
            }
            newStr.append("\r\n");
        }
        return newStr;
    }
}