package me.toufu.sdk;

import android.content.Context;

import me.toufu.appsdklib.platform.PlatformImpl;

/**
 * Created by zhenghu on 15-4-29.
 */
public class Platform {
    /**
     * 完成应用初始化，获取并保存应用必要信息
     *
     * @param context
     * @param appId
     * @param appKey
     */
    public static void init(Context context, String appId, String appKey) {
        TaskHelper.init();
        PlatformImpl.getInstance().init(context, appId, appKey);
    }

    /**
     * 验证应用的合法性
     * @param context
     * @param validateResponse
     */
    public static void validateApp(final Context context, final ValidateResponse validateResponse) {
        TaskHelper.runOnTask(new Runnable() {
            @Override
            public void run() {
                PlatformImpl.getInstance().validateApp(context, validateResponse);
            }
        });
    }

    /**
     * 支付购买产品
     * @param context
     * @param appInfo
     * @param accountInfo
     * @param orderInfo
     * @param payResponse
     */
    public static void pay(final Context context, final AppInfo appInfo, final AccountInfo accountInfo, final OrderInfo orderInfo, final PayResponse payResponse) {
        TaskHelper.runOnTask(new Runnable() {
            @Override
            public void run() {
                PlatformImpl.getInstance().pay(context, appInfo, accountInfo, orderInfo, payResponse);
            }
        });
    }

    /**
     * 根据用户购买情况获取已有的产品列表
     * @param context
     * @param accountInfo
     * @param tradeRecordResponse
     */
    public static void obtainTradeRecords(final Context context, final AccountInfo accountInfo, final TradeRecordResponse tradeRecordResponse) {
        TaskHelper.runOnTask(new Runnable() {
            @Override
            public void run() {
                PlatformImpl.getInstance().obtainTradeRecords(context, tradeRecordResponse);
            }
        });
    }
}
