package com.ahao.androidlib.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Avalon on 2016/8/25.
 */
public class IOUtils {
    private static final String TAG = "IOUtils";
    private IOUtils(){}

    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /** 关闭IO流 */
    public static boolean close(Closeable is) {
        try {
            if (is != null) {
                is.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /** 从input拷贝到output,返回字节数 */
    public static long copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


    public static String readString(final InputStream is, String encoding) throws IOException {
        InputStreamReader reader = new InputStreamReader(is, encoding);
        StringBuilder sb = new StringBuilder();

        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int n;
        while (EOF != (n = reader.read(buffer))) {
            sb.append(buffer, 0, n);
        }

        return sb.toString();
    }

    public static byte[] getByte(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(is, baos);
        return baos.toByteArray();
    }

    private static String readAsciiLine(final InputStream in) throws IOException {return null;}



}
