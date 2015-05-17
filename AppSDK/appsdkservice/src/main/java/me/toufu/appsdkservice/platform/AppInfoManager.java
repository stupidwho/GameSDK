package me.toufu.appsdkservice.platform;

import me.toufu.sdk.AccountInfo;
import me.toufu.sdk.AppInfo;

/**
 * Created by toufu on 15-5-17.
 */
public class AppInfoManager {
    private static AppInfoManager sInstance;

    private AppInfoManager() {
    }

    public static AppInfoManager getInstance() {
        if (sInstance == null)
            sInstance = new AppInfoManager();
        return sInstance;
    }

    // 记录应用相关信息
    public AppInfo appInfo;
    // 记录用户相关信息，暂时以手机区分用户
    public AccountInfo accountInfo;
}
