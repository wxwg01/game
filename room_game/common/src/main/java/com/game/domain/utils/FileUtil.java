package com.game.domain.utils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/13 14:39
 * @Since 0.0.1
 */
public class FileUtil {

    public static String readFile(String path) {
        StringBuffer sb = new StringBuffer();
        try (
                FileReader reader = new FileReader(path);
                BufferedReader br = new BufferedReader(reader);
        ) {
            String string;
            while ((string = br.readLine()) != null) {//把读取的数据放到i中
                System.out.println(string);
                sb.append(string);
            }
            //关闭流
        } catch (Exception e) {
            //文件读写异常
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
}
