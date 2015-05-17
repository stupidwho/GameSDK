package me.toufu.appsdkservice.platform;

import android.content.Context;

import me.toufu.sdk.AccountInfo;
import me.toufu.sdk.AppInfo;

/**
 * Created by toufu on 15-5-17.
 */
public class NetworkManager {
    public static NetworkManager sInstance;

    public static NetworkManager getInstance(Context context) {
        if (sInstance == null)
            sInstance = new NetworkManager(context);
        return sInstance;
    }

    private Context mContext;

    private NetworkManager(Context context) {
        mContext = context;
    }

    public String getLicenseInfo() {
        // TODO：从服务端获取LicenseInfo
        AppInfo appInfo = AppInfoManager.getInstance().appInfo;
        AccountInfo accountInfo = AppInfoManager.getInstance().accountInfo;
        return null;
    }
}
