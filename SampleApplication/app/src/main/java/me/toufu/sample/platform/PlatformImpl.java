package me.toufu.sample.platform;

import android.content.Context;

import me.toufu.sample.platform.model.AccountInfo;
import me.toufu.sample.platform.model.AppInfo;
import me.toufu.sample.utils.PhoneUtil;

/**
 * Created by zhenghu on 15-5-5.
 */
public class PlatformImpl {
    private static PlatformImpl sPlatform;

    // 记录应用相关信息
    private AppInfo mAppInfo;
    // 记录用户相关信息，暂时以手机区分用户
    private AccountInfo mAccountInfo;

    private PlatformImpl() {
    }

    public static PlatformImpl getInstance() {
        if (sPlatform == null) {
            sPlatform = new PlatformImpl();
        }
        return sPlatform;
    }

    public void init(Context context, String appId, String appKey) {
        context = context.getApplicationContext();
        mAppInfo = new AppInfo(appId, appKey, context.getPackageName());
        mAccountInfo = new AccountInfo(PhoneUtil.getImei(context), PhoneUtil.getSn());
    }
}
