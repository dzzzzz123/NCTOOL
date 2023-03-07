package com.ruoyi.common.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 获取文件夹下所有以tap结尾文件工具类
 * @author dz
 * @date 2023-02-13
 */
public class GetAllFilesUtils {

    /**
     * @param path 需要扫描的文件夹路径
     * @param data 得到的以tap结尾的文件夹列表
     * @return data
     */
    private static List<String> getData(String path, List<String> data) {

        File f=new File(path);
        if (f.isDirectory()) {
            File[] fs=f.listFiles();
            for (int i = 0; i< Objects.requireNonNull(fs).length; i++) {
                data=getData(fs[i].getPath(), data);
            }
        } else if (f.getName().endsWith(".tap")) {
            data.add(f.getName());
        }
        return data;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {

        String path = "D:\\Test";
        List<String> data= new ArrayList<>();
        data=getData(path, data);
        for (String datum : data) {
            System.out.println(datum);
        }
    }
}
