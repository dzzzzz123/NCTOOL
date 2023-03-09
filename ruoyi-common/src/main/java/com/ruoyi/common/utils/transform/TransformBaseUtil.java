package com.ruoyi.common.utils.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}