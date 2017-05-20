package com.cucr.myapplication.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * ZIP压缩与解压工具
 * @author tongxu_li
 * Copyright (c) 2015 Shanghai P&C Information Technology Co., Ltd.
 */
public class ZipUtil {

    /**
     * 解压功能
     * @param is
     * @param os
     * @throws Exception
     */
    public static void unzip(InputStream is, OutputStream os) throws Exception {
        // 通过一个具备解压功能的输入流 读取压缩包
        // GZIPInputStream
        // 通过一个普通的输出流 输出到目的地
        // InputStream is = new FileInputStream(new File("location.zip"));
        // OutputStream os = new FileOutputStream(new File("location.db"));
        GZIPInputStream gis = new GZIPInputStream(is);

        int len = -1;
        byte[] buffer = new byte[1024];

        while ((len = gis.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            os.flush();
        }

        os.close();
        gis.close();
    }



    //压缩文件
    public static void zip(InputStream is, OutputStream os) throws Exception {
        // 输入流读取目标文件
        // 通过一个具备压缩功能的输出流 输出到另一个目的地
        // GZIPOutputStream
        // InputStream is = new FileInputStream(new File("location.db"));
        // OutputStream os = new FileOutputStream(new File("location.zip"));
        // 具备压缩功能的输出流
        GZIPOutputStream gos = new GZIPOutputStream(os);
        int len = -1;
        byte[] buffer = new byte[1024];

        while ((len = is.read(buffer)) != -1) {
            gos.write(buffer, 0, len);
            gos.flush();
        }

        is.close();
        gos.close();
    }
}
