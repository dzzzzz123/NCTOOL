package com.ruoyi.common.utils.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.ruoyi.common.constant.TransformConstants.*;

/**
 * 转换NC代码基类
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformBaseUtil {

    /**
     * 转换NC代码基类
     *
     * @param file 输入进文件流处理的NC原始代码
     * @throws IOException IO异常
     */
    public static void transform(File file, int flag) throws IOException {
        byte[] fileContext = new byte[(int) file.length()];
        FileInputStream in = null;
        PrintWriter out = null;
        try {
            in = new FileInputStream(file);
            in.read(fileContext);
            String str = new String(fileContext, StandardCharsets.UTF_8);
            String[] content = str.split("\r\n");
            deleteT72Related(content);
            processAllReplace(content, flag);
            editFileHeader(content, flag);
            StringBuilder newStr = new StringBuilder();
            // 对NC原始代码进行操作
            switch (flag) {
                case 0:
                    newStr = TransformTo6300.processNcCode(content);
                    break;
                case 1:
                    newStr = TransformTo7000.processNcCode(content);
                    break;
                case 2:
                    newStr = TransformTo655.processNcCode(content);
                    break;
                default:
                    break;
            }
            out = new PrintWriter(file);
            out.write(newStr.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 转换NC代码时，删除掉代码中所有带有T72刀具的部分
     *
     * @param content 老字符串
     * @return 新字符串
     */
    static void deleteT72Related(String[] content) {
        for (int i = 0; i < content.length; i++) {
            // 删除掉T72相关代码块
            if (Objects.equals(content[i], "T72")) {
                content[i] = " ";
                i++;
                while (!Objects.equals(content[i], "N50")) {
                    content[i] = " ";
                    i++;
                }
                i++;
            } else if (Objects.equals(content[i], "(72   T072      3\" FACE MILLING TOOL)") || Objects.equals(content[i], "(TOOL NAME : T072)")) {
                content[i] = " ";
            }
        }
    }

    /**
     * 编辑NC代码文件头
     *
     * @param content 老字符串
     * @param flag    机型标识
     */
    static void editFileHeader(String[] content, int flag) {
        for (int i = 0; i < 150; i++) {
            if (i == 3) {
                switch (flag) {
                    case 0:
                        content[i] = NH6300_M_PROGCAT;
                        break;
                    case 1:
                        content[i] = NV7000_M_PROGCAT;
                        break;
                    case 2:
                        content[i] = MAZAK655_M_PROGCAT;
                        break;
                    default:
                        break;
                }
            } else if (i == 7) {
                content[i] = content[i] + "\r\n" + "(Processed by Platform: " + getTime() + ")";
            }
            // 给刀具最开始的(TOOL TABLE SUMMARY)加  ,
            else if (Objects.equals(content[i], "(TOOL NUMBER   TOOL ID OFFSET NO  TOOL COMMENT)") && (flag == 1 || flag == 2)) {
                i++;
                while (!content[i].startsWith("(TOOL NAME")) {
                    if (content[i].startsWith("(67") && flag == 1) {
                        content[i] = content[i].replace("(67", "(56");
                    } else if (content[i].startsWith("(67") && flag == 2) {
                        content[i] = content[i].replace("(67", "(43");
                    } else if (content[i].startsWith("(96")) {
                        content[i] = content[i].replace("(96", "(44");
                    } else if (content[i].startsWith(" ")) {
                        i++;
                        continue;
                    }
                    String[] str = content[i].split("\\s+", 2);
                    content[i] = str[0] + ", " + str[1];
                    i++;
                }
                i++;
            }
        }
    }

    /**
     * 为所有需要修改的字符串修改
     *
     * @param content 用来处理的字符串
     */
    static void processAllReplace(String[] content, int flag) {
        if (flag == 1 || flag == 2) {
            Map<String, String> temp = (flag == 1) ? NV7000_ALL_TO_CHANGE : MAZAK655_ALL_TO_CHANGE;
            for (int i = 0; i < content.length; i++) {
                for (String s : temp.keySet()) {
                    if (content[i].contains(s) && !content[i].startsWith("(")) {
                        content[i] = content[i].replace(s, temp.get(s));
                    }
                }
            }
        }
    }
}