package toufu.appsdkservice.platform;

import me.toufu.appsdklib.platform.model.LicenseInfo;

/**
 * Created by zhenghu on 15-5-9.
 */
public class ValidateHelper {
    public static boolean validateSignature(LicenseInfo info) {
        return true;
    }

    public static LicenseInfo parseLicenseInfo(String content) {
        if (content != null) {
            // TODO：解析解构，解析失败返回空，表示内容出错
        }
        return null;
    }
}
