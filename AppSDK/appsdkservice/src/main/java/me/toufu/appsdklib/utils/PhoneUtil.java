package me.toufu.appsdklib.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by zhenghu on 15-5-5.
 */
public class PhoneUtil {
    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getSn() {
        return Build.SERIAL;
    }
}
