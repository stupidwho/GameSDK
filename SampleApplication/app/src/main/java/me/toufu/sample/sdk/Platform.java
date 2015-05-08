package me.toufu.sample.sdk;

import android.content.Context;
import android.os.HandlerThread;

import me.toufu.sample.platform.PlatformImpl;
import me.toufu.sample.platform.model.AccountInfo;
import me.toufu.sample.platform.model.AppInfo;
import me.toufu.sample.platform.model.OrderInfo;

/**
 * Created by zhenghu on 15-4-29.
 */
public class Platform {
    /**
     * 完成应用初始化，获取并保存应用必要信息
     * @param context
     * @param appId
     * @param appKey
     */
    public static void init(Context context, String appId, String appKey) {
        PlatformImpl.getInstance().init(context, appId, appKey);
    }

    public static void validateApp(ValidateResponse validateResponse) {

    }

    public static void pay(AppInfo appInfo, AccountInfo accountInfo, OrderInfo orderInfo, PayResponse payResponse) {

    }

    public static void obtainTradeRecords(AccountInfo accountInfo, TradeRecordResponse tradeRecordResponse) {

    }
}
