package com.ruoyi.system.utils.transform;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ruoyi.system.constant.TransformConstants.*;

/**
 * @author dz
 * @date 2023-04-20
 */
public class TransformTo7000Finishing extends TransformBaseUtil {

    static StringBuilder processNcCode(String[] content) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            if (content[i].startsWith("G84")) {
                int j = 1;
                String mPattern = ".*S\\d{3}M\\d{2}$";
                Pattern pattern = Pattern.compile(mPattern);
                while (i - j >= 0 && j <= 50) {
                    Matcher matcher = pattern.matcher(content[i - j]);
                    if (matcher.matches()) {
                        String msCode = matcher.group().substring(matcher.group().indexOf("S"), matcher.group().indexOf("S") + 4);
                        newStr.append(TAPTEETH).append(msCode).append("\r\n").append(content[i]).append("\r\n");
                        break;
                    }
                    if (j == 49) {
                        String msCode = "S243";
                        newStr.append(TAPTEETH).append(msCode).append("\r\n").append(content[i]).append("\r\n");
                    }
                    j++;
                }
                i++;
                while (i < content.length && !content[i].startsWith("G80")) {
                    newStr.append(content[i]).append("\r\n");
                    i++;
                }
                newStr.append("G80").append("\r\n").append("G94");
            } else if (content[i].startsWith("G30X0.Y0.")) {
                newStr.append("G30Y0.");
            } else if (content[i].startsWith("G65P9220")) {
                i++;
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
            newStr.append("\r\n");
        }
        return newStr;
    }
}
