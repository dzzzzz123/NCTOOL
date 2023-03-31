package com.ruoyi.system.utils.transform;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ruoyi.system.constant.TransformConstants.*;

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
    private static Map<String, String> BRACKETS_TO_CHANGE;

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
            processAllReplace(content, flag);
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
                BRACKETS_TO_CHANGE = NV7000_BRACKETS_T_TO_CHANGE;
                DELETE_FLAG = "(TOOL, NAME : T100)";
                break;
            case 2:
                TO_DELETE = MAZAK655_M_TO_DELETE;
                FILENAME = MAZAK655_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = MAZAK655_M_PROGCAT;
                ALL_TO_CHANGE = MAZAK655_ALL_TO_CHANGE;
                BRACKETS_TO_CHANGE = MAZAK655_BRACKETS_T_TO_CHANGE;
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
                .replaceFirst("\\b5\\b", "16")
                .replaceFirst("\\b16\\b", "43")
                .replaceFirst("\\b40\\b", "52")
                .replaceFirst("\\b44\\b", "51")
                .replaceFirst("\\b49\\b", "59")
                .replaceFirst("\\b60\\b", flag == 1 ? "54" : "56")
                .replaceFirst("\\b67\\b", flag == 1 ? "56" : "54")
                .replaceFirst("\\b77\\b", "44")
                .replaceFirst("\\b81\\b", "57")
                .replaceFirst("\\b84\\b", "40")
                .replaceFirst("\\b96\\b", "44")
                .replaceFirst("\\b108\\b", "33");
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
    static void processAllReplace(String[] content, int flag) {
        Arrays.parallelSetAll(content, i -> {
            if (!content[i].startsWith("(")) {
                for (String s : ALL_TO_CHANGE.keySet()) {
                    if (content[i].contains(s)) {
                        if (s.startsWith("T")) {
                            if (Objects.equals(content[i], s) || Objects.equals(content[i], "G90" + s)) {
                                return content[i].replaceFirst(s, ALL_TO_CHANGE.get(s));
                            }
                        } else if (s.startsWith("H")) {
                            String regex = "^.*?H(\\d+)$";
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(content[i]);
                            if (matcher.find()) {
                                String hValue = matcher.group(1);
                                return content[i].replaceFirst("H"+hValue, ALL_TO_CHANGE.get(s));
                            }
                        } else {
                            return content[i].replaceFirst(s, ALL_TO_CHANGE.get(s));
                        }
                    }
                }
            } else {
                if (flag == 1 || flag == 2) {
                    for (String s : BRACKETS_TO_CHANGE.keySet()) {
                        String pattern = "^\\(\\d*,?\\s*T\\d+.*\\)$";
                        if (content[i].matches(pattern)) {
                            return content[i].replaceFirst(s, BRACKETS_TO_CHANGE.get(s));
                        }
                    }
                }
            }
            return content[i];
        });
    }
}