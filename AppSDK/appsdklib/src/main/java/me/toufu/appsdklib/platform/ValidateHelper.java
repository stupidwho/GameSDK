package me.toufu.appsdklib.platform;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import me.toufu.appsdklib.platform.model.LicenseInfo;
import me.toufu.appsdklib.utils.SignatureUtil;

/**
 * Created by zhenghu on 15-5-9.
 */
public class ValidateHelper {
    private static final String TAG = "ValidateHelper";

    public static boolean validateSignature(LicenseInfo info) {
        return SignatureUtil.isSignatureLegal(info.content.getBytes(),
                Base64.decode(info.signature, Base64.DEFAULT),
                AppInfoManager.getInstance().appInfo.getAppKey());
    }

    public static LicenseInfo parseLicenseInfo(String content) {
        if (content != null) {
            final String msg = content;
            try {
                JSONObject obj = new JSONObject(content);
                String body = obj.getString("content");
                String sign = obj.getString("signature");
                LicenseInfo info = new LicenseInfo();
                info.signature = sign;
                info.content = body;
                return info;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
