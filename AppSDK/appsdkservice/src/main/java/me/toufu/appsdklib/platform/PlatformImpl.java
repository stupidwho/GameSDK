package me.toufu.appsdklib.platform;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.pingplusplus.libone.PayActivity;

import me.toufu.appsdklib.platform.model.LicenseInfo;
import me.toufu.appsdklib.utils.PhoneUtil;
import me.toufu.sdk.AccountInfo;
import me.toufu.sdk.AppInfo;
import me.toufu.sdk.OrderInfo;
import me.toufu.sdk.PayResponse;
import me.toufu.sdk.ProductInfo;
import me.toufu.sdk.TradeRecordResponse;
import me.toufu.sdk.ValidateResponse;
import me.toufu.sdk.ValidateResult;

/**
 * Created by zhenghu on 15-5-5.
 */
public class PlatformImpl {
    private static PlatformImpl sPlatform;

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
        AppInfoManager.getInstance().appInfo = new AppInfo(appId, appKey, context.getPackageName());
        AppInfoManager.getInstance().accountInfo = new AccountInfo(PhoneUtil.getImei(context), PhoneUtil.getSn());
    }

    public void validateApp(Context context, final ValidateResponse validateResponse) {
        ValidateManager manager = new ValidateManager(context);
        final ValidateResult result = manager.validateApp();
        runOnUi(new Runnable() {
            @Override
            public void run() {
                try {
                    validateResponse.onResult(result.code, result.message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void pay(Context context, AppInfo appInfo, AccountInfo accountInfo, OrderInfo orderInfo, PayResponse payResponse) {
        payOnline();
        // TODO：成功则更新本地内容/或直接获取网络最新
    }

    private void payOnline() {

    }

    public void obtainTradeRecords(Context context, TradeRecordResponse tradeRecordResponse) {
        ValidateManager validateManager = new ValidateManager(context);
        ProductInfo productInfo = getProductInfo(validateManager.getContentFromLocal());
        if (productInfo != null) {
            tradeRecordResponse.onResult(TradeRecordResponse.TRADE_RESULT_NOPROBLEM, "获取成功", productInfo);
        } else {
            tradeRecordResponse.onResult(TradeRecordResponse.TRADE_RESULT_ERROR_FORMAT, "读取失败", null);
        }
    }

    private ProductInfo getProductInfo(LicenseInfo info) {
        return info.productInfo;
    }

    private void runOnUi(Runnable runnable) {
        mUiHandler.post(runnable);
    }
}
