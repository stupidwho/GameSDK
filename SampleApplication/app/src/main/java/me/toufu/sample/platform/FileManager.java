package me.toufu.sample.platform;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhenghu on 15-5-11.
 */
public class FileManager {
    public static final String LICENSE_DIR = "toufu_sdk_license";
    public static final String LICENSE_FILE = "toufu_license";

    /**
     * 从文件读取license字符串，如果不存在则返回空，会文件夹、文件不存在会创建自动创建
     * @param context
     * @return
     */
    public static String getLicense(Context context) {
        File rootDir = context.getFilesDir();
        File licenseDir = new File(rootDir, LICENSE_DIR);
        if (licenseDir.exists() && licenseDir.isDirectory()) {
            File licenseFile = new File(licenseDir, LICENSE_FILE);
            if (licenseFile.exists() && licenseFile.isFile()) {
                FileInputStream fis = null;
                InputStreamReader isr = null;
                try {
                    fis = new FileInputStream(licenseFile);
                    isr = new InputStreamReader(fis);
                    char tmp[] = new char[1024];
                    StringBuilder sb = new StringBuilder();
                    int length;
                    while ((length = isr.read(tmp)) != -1) {
                        sb.append(tmp, 0, length);
                    }
                    return sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null)
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (isr != null)
                        try {
                            isr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            } else {
                try {
                    licenseFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            licenseDir.mkdirs();
        }
        return null;
    }

    public static void saveLicense(Context context, String content) {

    }
}
