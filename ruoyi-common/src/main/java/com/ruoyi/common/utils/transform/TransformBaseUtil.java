package com.ruoyi.common.utils.transform;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ruoyi.common.constant.TransformConstants.*;

/**
 * 转换NC代码基类
 *
 * @author dz
 * @date 2023-03-08
 */
public class TransformBaseUtil {

    private static String DELETE_FLAG;
    private static String[] TO_DELETE;
    private static String FILENAMEWITHOUTPREFIX;
    private static String FILENAME;
    private static String WHICH_PROGCAT;
    private static Map<String, String> ALL_TO_CHANGE;


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
            FILENAMEWITHOUTPREFIX = file.getName().split("\\.")[0];
            in = new FileInputStream(file);
            in.read(fileContext);
            String str = new String(fileContext, StandardCharsets.UTF_8);
            String[] content = str.split("\r\n");
            initProcess(flag);
            editFileHeader(content, flag);
            content = processAllDelete(content);
            processAllReplace(content);
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
     * 用来初始化增删改数组或map的函数
     *
     * @param flag 标识
     */
    static void initProcess(int flag) {
        switch (flag) {
            case 0:
                TO_DELETE = NH6300_M_TO_DELETE;
                FILENAME = NH6300_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = NH6300_M_PROGCAT;
                ALL_TO_CHANGE = NH6300_ALL_TO_CHANGE;
                DELETE_FLAG = "T72";
                break;
            case 1:
                TO_DELETE = NV7000_M_TO_DELETE;
                FILENAME = NV7000_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = NV7000_M_PROGCAT;
                ALL_TO_CHANGE = NV7000_ALL_TO_CHANGE;
                DELETE_FLAG = "(100, T100      PROBE)";
                break;
            case 2:
                TO_DELETE = MAZAK655_M_TO_DELETE;
                FILENAME = MAZAK655_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = MAZAK655_M_PROGCAT;
                ALL_TO_CHANGE = MAZAK655_ALL_TO_CHANGE;
                DELETE_FLAG = "(100, T100      PROBE)";
                break;
            default:
                break;
        }
    }

    /**
     * 编辑NC代码文件头
     *
     * @param content 老字符串
     * @param flag    机型标识
     */
    static void editFileHeader(String[] content, int flag) {
        for (int i = 0; i < 75; i++) {
            if (i == 2) {
                content[i] = FILENAME;
            }
            if (i == 3) {
                content[i] = WHICH_PROGCAT;
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
                    } else if (content[i].startsWith("(84")) {
                        content[i] = content[i].replace("(84", "(40");
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
     * 转换NC代码时，删除掉代码中所有需要删除的部分
     *
     * @param content 老字符串
     * @return 新字符串
     */
    static String[] processAllDelete(String[] content) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < content.length; i++) {
            // 删除掉T72相关代码块
            if (Objects.equals(content[i], DELETE_FLAG)) {
                indices.add(i);
                i++;
                while (true) {
                    if (Objects.equals(content[i], "N50")) {
                        break;
                    }
                    indices.add(i);
                    i++;
                    if (Objects.equals(content[i], "N60")) {
                        indices.add(i);
                        i++;
                        break;
                    }
                }
                i++;
            } else if (Objects.equals(content[i], "(72, T072      3\" FACE MILLING TOOL)") || Objects.equals(content[i], "(72   T072      3\" FACE MILLING TOOL)") || Objects.equals(content[i], "(TOOL NAME : T072)")) {
                indices.add(i);
            } else if (Arrays.asList(TO_DELETE).contains(content[i])) {
                indices.add(i);
            }
        }
        return ArrayUtils.removeAll(content, indices.stream().mapToInt(Integer::intValue).toArray());
    }


    /**
     * 为所有需要修改的字符串修改
     *
     * @param content 用来处理的字符串
     */
    static void processAllReplace(String[] content) {
        for (int i = 0; i < content.length; i++) {
            for (String s : ALL_TO_CHANGE.keySet()) {
                if (content[i].contains(s) && !content[i].startsWith("(")) {
                    content[i] = content[i].replace(s, ALL_TO_CHANGE.get(s));
                }
            }
        }
    }
}