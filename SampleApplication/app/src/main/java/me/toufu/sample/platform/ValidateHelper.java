package me.toufu.sample.platform;

import me.toufu.sample.platform.model.LicenseInfo;

/**
 * Created by zhenghu on 15-5-9.
 */
public class ValidateHelper {
    public static LicenseInfo parseLicenseInfo(String content) {
        // TODO：RSA解密并解析解构，解析失败返回空，表示密钥无效/保存内容出错
        if (content == null)
            return null;
        return null;
    }
}
