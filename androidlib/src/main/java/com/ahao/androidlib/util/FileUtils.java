package com.ahao.androidlib.util;

import android.support.annotation.CheckResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Avalon on 2016/8/25.
 */
public class FileUtils {
    private static final String TAG = "FileUtils";
    private FileUtils() {
    }

    /** 确保文件存在,不存在就创建 */
    public static boolean ensureFile(File file) {
        try {
            return file!=null && (file.exists() ? file.isFile() : file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 确保目录存在,不存在就创建 */
    public static boolean ensureDirectory(File file) {
        return file!=null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 将byte转化为易读的单位(国际单位si或者二进制单位binary),
     * 来源:http://stackoverflow.com/questions/3758606/
     * 解释:http://beterhans.blogspot.tw/2012/03/si-prefix-binary-prefix-1mb-1024kb.html
     */
    @CheckResult
    public static String readableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.US, "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * 删除文件及其子文件
     */
    public static boolean delete(File file) {
        return deleteContent(file) && file.delete();
    }

    /**
     * 删除目录下的内容
     */
    public static boolean deleteContent(File file) {
        if (file == null) {
            return false;
        }
        boolean success = true;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                success &= deleteContent(f);
                success &= f.delete();
            }
        }
        return success;
    }

    /** 从InputStream中读取Serializable对象 */
    public static Serializable readSerializable(InputStream is){
        if (is == null) {
            return false;
        }
//        Log.i(TAG, "readSerializable: ");
        ObjectInputStream oos = null;
        try {
            oos = new ObjectInputStream(is);
            return (Serializable) oos.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(oos);
        }
    }

    /** 从file中读取Serializable对象 */
    public static Serializable readSerializable(File file){
        if (file == null || !ensureFile(file)) {
            return false;
        }
//        Log.i(TAG, "readSerializable: ");
        ObjectInputStream oos = null;
        try {
            oos = new ObjectInputStream(new FileInputStream(file));
            return (Serializable) oos.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(oos);
        }
    }


    public static boolean writeString(File file, String str) {
        if (file == null || !ensureFile(file) || str==null) {
            return false;
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(str.getBytes("utf-8"));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(os);
        }
    }

    /** 将Serializable写入OutputStream,失败返回false */
    public static boolean writeSerializable(OutputStream os, Serializable obj) {
        if (os == null) {
            return false;
        }
//        Log.i(TAG, "writeSerializable: ");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(oos);
        }
    }

    /** 将Serializable写入File,失败返回false */
    public static boolean writeSerializable(File file, Serializable obj, boolean forceable) {
        if (file == null || !ensureFile(file) || obj==null) {
            return false;
        }
        if(forceable){
            ensureFile(file);
        }
//        Log.i(TAG, "writeSerializable: ");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(obj);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(oos);
        }
    }
}
