package me.toufu.sample.platform;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import me.toufu.sample.platform.model.AccountInfo;
import me.toufu.sample.platform.model.AppInfo;
import me.toufu.sample.platform.model.LicenseInfo;
import me.toufu.sample.platform.model.OrderInfo;
import me.toufu.sample.platform.model.ProductInfo;
import me.toufu.sample.sdk.PayResponse;
import me.toufu.sample.sdk.TradeRecordResponse;
import me.toufu.sample.sdk.ValidateResponse;
import me.toufu.sample.utils.NetworkUtil;
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

    private HandlerThread mTaskThread;
    private Handler mTaskHandler;

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
        mTaskThread = new HandlerThread("PlatformThread");
        mTaskThread.start();
        mTaskHandler = new Handler(mTaskThread.getLooper());
    }

    public void validateApp(Context context, ValidateResponse validateResponse) {
        LicenseInfo contentNet = null;
        if (NetworkUtil.isNetworkConnected(context)) {
            contentNet = getContentFromNet();
        }
        LicenseInfo contentLocal = getContentFromLocal();

        LicenseInfo aimContent = compareContent(contentLocal, contentNet);
        if (aimContent != null) {
            validate(aimContent, validateResponse);
        } else {

        }
    }

    private LicenseInfo getContentFromNet() {
        // TODO：从服务器获取数据
        return null;
    }

    private LicenseInfo getContentFromLocal() {
        // TODO：从本地获取license内容
        return null;
    }

    private LicenseInfo compareContent(LicenseInfo contentLocal, LicenseInfo contentNet) {
        // TODO：返回应该解析的内容，有错则返回null
        return null;
    }

    private void validate(LicenseInfo info, ValidateResponse validateResponse) {
        // TODO：根据info验证是否通过，通过validateResponse返回结果
    }

    public void pay(Context context, AppInfo appInfo, AccountInfo accountInfo, OrderInfo orderInfo, PayResponse payResponse) {
        payOnline();
        // TODO：成功则更新本地内容/或直接获取网络最新
    }

    private void payOnline() {

    }

    public void obtainTradeRecords(Context context, AccountInfo accountInfo, TradeRecordResponse tradeRecordResponse) {
        ProductInfo productInfo = parseProductInfo(readFromLocal());
        // TODO：操作返回结果列表
    }

    private LicenseInfo readFromLocal() {
        // TODO：从文件读取License信息
        return null;
    }

    private ProductInfo parseProductInfo(LicenseInfo info) {
        // TODO：从license解析出产品信息
        return null;
    }

    private void runOnTask(Runnable runnable) {
        mTaskHandler.post(runnable);
    }
}
