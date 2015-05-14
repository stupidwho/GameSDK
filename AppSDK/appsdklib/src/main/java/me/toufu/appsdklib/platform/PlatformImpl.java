package me.toufu.appsdklib.platform;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import me.toufu.sample.platform.model.AccountInfo;
import me.toufu.sample.platform.model.AppInfo;
import me.toufu.sample.platform.model.LicenseInfo;
import me.toufu.sample.platform.model.OrderInfo;
import me.toufu.sample.platform.model.ProductInfo;
import me.toufu.sample.sdk.PayResponse;
import me.toufu.sample.sdk.TradeRecordResponse;
import me.toufu.sample.sdk.ValidateResponse;
import me.toufu.sample.sdk.ValidateResult;
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

    private Handler mUiHandler;

    private PlatformImpl() {
        mUiHandler = new Handler(Looper.getMainLooper());
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

    public void validateApp(Context context, final ValidateResponse validateResponse) {
        ValidateManager manager = new ValidateManager(context);
        final ValidateResult result = manager.validateApp();
        runOnUi(new Runnable() {
            @Override
            public void run() {
                validateResponse.onResult(result.code, result.message);
            }
        });
    }

    public void pay(Context context, AppInfo appInfo, AccountInfo accountInfo, OrderInfo orderInfo, PayResponse payResponse) {
        payOnline();
        // TODO：成功则更新本地内容/或直接获取网络最新
    }

    private void payOnline() {

    }

    public void obtainTradeRecords(Context context, AccountInfo accountInfo, TradeRecordResponse tradeRecordResponse) {
        ProductInfo productInfo = parseProductInfo(ValidateManager.getContentFromLocal(context));
        if (productInfo != null) {
            tradeRecordResponse.onResult(TradeRecordResponse.TRADE_RESULT_NOPROBLEM, "获取成功", productInfo);
        } else {
            tradeRecordResponse.onResult(TradeRecordResponse.TRADE_RESULT_ERROR_CONTENT, "解析错误", null);
        }
    }

    private ProductInfo parseProductInfo(LicenseInfo info) {
        // TODO：从license解析出产品信息
        if (info == null)
            return null;
        return null;
    }

    private void runOnUi(Runnable runnable) {
        mUiHandler.post(runnable);
    }
}
