package com.ruoyi.common.utils.transform;

import org.apache.commons.lang3.ArrayUtils;

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
                DELETE_FLAG = "(TOOL, NAME : T100)";
                break;
            case 2:
                TO_DELETE = MAZAK655_M_TO_DELETE;
                FILENAME = MAZAK655_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = MAZAK655_M_PROGCAT;
                ALL_TO_CHANGE = MAZAK655_ALL_TO_CHANGE;
                DELETE_FLAG = "(TOOL, NAME : T100)";
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
        for (int i = 0; i < content.length; i++) {
            switch (i) {
                case 2:
                    content[i] = FILENAME;
                    break;
                case 3:
                    content[i] = WHICH_PROGCAT;
                    break;
                case 7:
                    content[i] = content[i] + "\r\n" + "(Processed by Platform: " + getTime() + ")";
                    break;
                default:
                    if (content[i].startsWith("(TOOL NUMBER") && (flag == 1 || flag == 2)) {
                        while (true) {
                            i++;
                            content[i] = modifyToolNumber(content[i], flag);
                            if (content[i].startsWith("(TOOL, NAME")) {
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    /**
     * @param line 给定的行内容
     * @param flag 分辨机型标识的唯一标识
     * @return 已修改的行
     */
    static String modifyToolNumber(String line, int flag) {
        line = line
                .replace("(5", "(16")
                .replace("(16", "(43")
                .replace("(40", "(52")
                .replace("(44", "(51")
                .replace("(49", "(59")
                .replace("(60", flag == 1 ? "(54" : "(56")
                .replace("(67", flag == 1 ? "(56" : "(54")
                .replace("(77", "(44")
                .replace("(81", "(57")
                .replace("(84", "(40")
                .replace("(96", "(44")
                .replace("(108", "(33");
        String[] str = line.split("\\s+", 2);
        line = str[0] + ", " + str[1];
        return line;
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
            if (content[i].startsWith(DELETE_FLAG)) {
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
            } else if (content[i].startsWith("(72, T072") || content[i].startsWith("(72   T072") || Objects.equals(content[i], "(TOOL NAME : T072)")) {
                indices.add(i);
            } else if (Arrays.asList(TO_DELETE).contains(content[i])) {
                indices.add(i);
            } else if (content[i].startsWith("(100  T100") || content[i].startsWith("(100, T100")) {
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
                    // if (content[i].contains(s)) {
                    content[i] = content[i].replace(s, ALL_TO_CHANGE.get(s));
                }
            }
        }
    }
}