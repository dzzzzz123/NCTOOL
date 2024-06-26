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
     */
    public static void transform(File file, int flag) {
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
            content = processAllDelete(content, flag);
            processAllReplace(content, flag);
            StringBuilder newStr = new StringBuilder();
            // 对NC原始代码进行操作
            switch (flag) {
                case 0:
                    newStr = TransformTo6300.processNcCode(content);
                    newStr = deleteLastDetection(newStr);
                    newStr = addLastDetection(newStr);
                    break;
                case 1:
                    newStr = TransformTo7000.processNcCode(content);
                    break;
                case 2:
                    newStr = TransformTo655.processNcCode(content);
                    break;
                case 3:
                    StringBuilder tempStr2 = TransformTo7000Finishing.processNcCode(content);
                    newStr = deleteLastG30X0(tempStr2);
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
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
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
     * 用来再次初始化增删改常量池
     *
     * @param flag 标识
     */
    static void initProcess(int flag) {
        switch (flag) {
            case 0:
                TO_DELETE = NH6300_TO_DELETE;
                FILENAME = NH6300_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = NH6300_M_PROGCAT;
                ALL_TO_CHANGE = NH6300_ALL_TO_CHANGE;
                DELETE_FLAG = "T72";
                break;
            case 1:
                TO_DELETE = NV7000_TO_DELETE;
                FILENAME = NV7000_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = NV7000_M_PROGCAT;
                ALL_TO_CHANGE = NV7000_ALL_TO_CHANGE;
                BRACKETS_TO_CHANGE = NV7000_BRACKETS_T_TO_CHANGE;
                DELETE_FLAG = "(TOOL, NAME : T100)";
                break;
            case 2:
                TO_DELETE = MAZAK655_TO_DELETE;
                FILENAME = MAZAK655_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = MAZAK655_M_PROGCAT;
                ALL_TO_CHANGE = MAZAK655_ALL_TO_CHANGE;
                BRACKETS_TO_CHANGE = MAZAK655_BRACKETS_T_TO_CHANGE;
                DELETE_FLAG = "(TOOL, NAME : T100)";
                break;
            case 3:
                TO_DELETE = NH6300_TO_DELETE;
                FILENAME = NV7000_FINISHING_FILENAME + FILENAMEWITHOUTPREFIX + ")";
                WHICH_PROGCAT = NV7000_FINISHING_M_PROGCAT;
                ALL_TO_CHANGE = NV7000_FINISHING_ALL_TO_CHANGE;
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
        for (int i = 0; i < content.length / 2; i++) {
            switch (i) {
                case 2:
                    content[i] = FILENAME;
                    break;
                case 3:
                    content[i] = WHICH_PROGCAT;
                    break;
                case 7:
                    if (flag != 3) {
                        content[i] += "\r\n" + "(Processed by Platform: " + getTime() + ")";
                    }
                    break;
                case 9:
                    if (flag == 3) {
                        content[i] = "(Processed by Platform: " + getTime() + ")";
                    }
                    break;
                default:
                    if (content[i].startsWith("(TOOL NUMBER") && (flag == 1 || flag == 2)) {
                        for (int j = i + 1; j < content.length; j++) {
                            content[j] = modifyToolNumber(content[j]);
                            if (content[j].startsWith("(TOOL, NAME")) {
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 对TOOl NUMBER后面所有的括号内的内容进行刀具的刀号进行替换
     *
     * @param line 给定的行内容
     * @return 已修改的行
     */
    static String modifyToolNumber(String line) {
        for (String s : BRACKETS_TO_CHANGE.keySet()) {
            if (line.startsWith("(" + s)) {
                line = line.replaceFirst("\\b" + s + "\\b", BRACKETS_TO_CHANGE.get(s));
            }
        }
        String[] str = line.split("\\s+", 2);
        line = str[0] + ", " + str[1];
        return line;
    }

    /**
     * 转换NC代码时，删除掉代码中所有需要删除的部分,主要是飞面与各种M值
     *
     * @param content 老字符串
     * @return 新字符串
     */
    static String[] processAllDelete(String[] content, int flag) {
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < content.length; i++) {
            if ((flag != 0) && (content[i].startsWith("T71") || content[i].startsWith("T93"))) {
                int temp = i - 10;
                while (temp < i) {
                    if (content[temp].startsWith("(")) {
                        indices.add(temp);
                    }
                    temp++;
                }
                while (!(content[i - 2].startsWith("M01") && content[i - 1].startsWith("M100"))) {
                    indices.add(i);
                    i++;
                }
            } else if ((flag != 3) && (content[i].startsWith(DELETE_FLAG) || content[i].startsWith("(72") || Objects.equals(content[i], "(TOOL NAME : T072)"))) {
                indices.add(i);
                if (content[i].startsWith(DELETE_FLAG)) {
                    while (!Objects.equals(content[i], "N50")) {
                        indices.add(i);
                        i++;
                        if (Objects.equals(content[i], "N60")) {
                            indices.add(i);
                            i++;
                            break;
                        }
                    }
                }
            } else if ((flag == 3) && Arrays.asList(TO_DELETE).contains(content[i])) {
                indices.add(i);
            } else if ((flag != 3) && (Arrays.asList(TO_DELETE).contains(content[i]) || content[i].startsWith("(100  T100") || content[i].startsWith("(100, T100"))) {
                indices.add(i);
            }
        }
        return ArrayUtils.removeAll(content, indices.stream().mapToInt(Integer::intValue).toArray());
    }

    /**
     * 为所有需要修改的字符串修改
     * 如果匹配到的字符串开头有 ( 那么利用正则表达式匹配需要修改的字符串
     * 正则：一个以左括号开始，包含数字和逗号以及空格的字符串，然后紧接着是大写字母T和数字组成的字符串，后面还可能包含其他字符。
     * 如："(数字, T数字其他字符)"这样的字符串
     * (T16, 6.0MM CARBIDE BALLMILL HELICAL FLUTES)
     * 如果不是以 ( 开头的字符串，那么就分为需要修改的H，T或是其他
     * T需要修改的就是刀具Txx或者G90Txx
     * H需要修改的为G43Z35.H58如同这种，修改其中的H值
     *
     * @param content 用来处理的字符串
     */
    static void processAllReplace(String[] content, int flag) {
        Arrays.parallelSetAll(content, i -> {
            if (content[i].startsWith("(")) {
                if (flag == 1 || flag == 2) {
                    for (String s : BRACKETS_TO_CHANGE.keySet()) {
                        String pattern = "^\\(\\d*,?\\s*T\\d+.*\\)$";
                        if (content[i].matches(pattern)) {
                            return content[i].replaceFirst(s, BRACKETS_TO_CHANGE.get(s));
                        }
                    }
                }
            } else {
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
                            boolean flag2 = matcher.find();
                            String hValue = matcher.group(1);
                            if (flag2 && Objects.equals(s, "H" + hValue)) {
                                return content[i].replaceFirst("H" + hValue, ALL_TO_CHANGE.get(s));
                            }
                        } else {
                            return content[i].replaceFirst(s, ALL_TO_CHANGE.get(s));
                        }
                    }
                }
            }
            return content[i];
        });
    }

    /**
     * 删除精加工NV7000代码中最后一个G30X0.
     *
     * @param tempSb 临时转换的字符串
     * @return 输出的字符串
     */
    static StringBuilder deleteLastG30X0(StringBuilder tempSb) {
        String[] content = tempSb.toString().split("\r\n");
        int indices = content.length;
        for (int i = content.length - 1; i >= 0; i--) {
            if (content[i].startsWith("G30X0.")) {
                indices = i;
                break;
            }
        }
        content = ArrayUtils.removeAll(content, indices);
        StringBuilder sb = new StringBuilder();
        for (String s : content) {
            sb.append(s).append("\r\n");
        }
        return sb;
    }

    /**
     * 删除最后一个对刀检测代码
     *
     * @param tempSb 已处理完的NH6300机床的G代码
     * @return newStr
     */
    static StringBuilder deleteLastDetection(StringBuilder tempSb) {
        String[] content = tempSb.toString().split("\r\n");
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = content.length - 1; i >= 0; i--) {
            if (content[i].startsWith("IF[#1004NE1]GOTO9000")) {
                do {
                    indices.add(i);
                    i--;
                } while (!content[i].startsWith("#991=-2"));
                indices.add(i);
                break;
            }
        }
        content = ArrayUtils.removeAll(content, indices.stream().mapToInt(Integer::intValue).toArray());
        StringBuilder sb = new StringBuilder();
        for (String s : content) {
            sb.append(s).append("\r\n");
        }
        return sb;
    }

    /**
     * 对最后一个M01之前加上断刀检测代码
     *
     * @param str G代码
     * @return {@link StringBuilder}
     */
    private static StringBuilder addLastDetection(StringBuilder str) {
        String[] content = str.toString().split("\r\n");
        for (int i = content.length - 1; i >= 0; i--) {
            if (content[i].equals("M01")) {
                content[i] = LAST_BROKEN_KNIFE_DETECTION;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : content) {
            sb.append(s).append("\r\n");
        }
        return sb;
    }


    /**
     * 处理D#51999相关逻辑 转换 H 数值
     *
     * @param content 每行G代码
     * @param newStr  生成出来新的每行G代码
     * @param i       指针
     */
    static void process51999(String[] content, StringBuilder newStr, int i) {
        int j = 1;
        boolean isMatch = false;
        while (i - j >= 0) {
            String mPattern = "(?<=\\.H)\\d+";
            Pattern pattern = Pattern.compile(mPattern);
            Matcher matcher = pattern.matcher(content[i - j]);
            if (matcher.find()) {
                isMatch = true;
                String hCode = matcher.group();
                newStr.append(content[i].replace("#51999", hCode));
                break;
            }
            j++;
        }
        if (!isMatch) {
            newStr.append(content[i]);
        }
    }

    /**
     * 处理攻牙相关代码逻辑
     *
     * @param content 每行G代码
     * @param newStr  生成出来新的每行G代码
     * @param i       指针
     * @return 返回新的指针
     */
    public static int processTAPTEETH(String[] content, StringBuilder newStr, int i) {
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
        return i;
    }

}