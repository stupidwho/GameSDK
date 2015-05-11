package me.toufu.sample.platform;

import android.content.Context;

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
import me.toufu.sample.utils.SignatureUtil;

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

    public void validateApp(Context context, ValidateResponse validateResponse) {
        LicenseInfo contentNet = null;
        if (NetworkUtil.isNetworkConnected(context)) {
            contentNet = getContentFromNet();
        }
        LicenseInfo contentLocal = getContentFromLocal(context);

        LicenseInfo aimContent = validateContent(contentLocal, contentNet, validateResponse);
        if (aimContent != null) {
            validate(aimContent, validateResponse);
        }
    }

    private LicenseInfo getContentFromNet() {
        // TODO：从服务器获取数据
        String content = "";
        LicenseInfo info = ValidateHelper.parseLicenseInfo(content);
        return info;
    }

    private LicenseInfo getContentFromLocal(Context context) {
        String content = FileManager.getLicense(context);
        LicenseInfo info = ValidateHelper.parseLicenseInfo(content);
        return info;
    }

    private LicenseInfo validateContent(LicenseInfo contentLocal, LicenseInfo contentNet, ValidateResponse validateResponse) {
        // TODO：返回应该解析的内容，返回null不作处理
        if (contentLocal == null && contentNet == null) {
            validateResponse.onResult(ValidateResponse.RESULT_ERROR_CONTENT, "内容解析错误");
        } else if (contentLocal == null && contentNet != null) {
            // TODO：返回contentNet
        } else if (contentLocal != null && contentNet == null) {
            // TODO：检查时效，返回无网络内容
        } else {
            // TODO：比较时间戳
        }
        return null;
    }

    private void validate(LicenseInfo info, ValidateResponse validateResponse) {
        // TODO：根据info验证是否通过，通过validateResponse返回结果
        if (isLegal(info)) {

        } else {

        }
    }

    private boolean isLegal(LicenseInfo info) {
        String content = "";
        return SignatureUtil.isSignatureLegal(content, "", "");
    }

    public void pay(Context context, AppInfo appInfo, AccountInfo accountInfo, OrderInfo orderInfo, PayResponse payResponse) {
        payOnline();
        // TODO：成功则更新本地内容/或直接获取网络最新
    }

    private void payOnline() {

    }

    public void obtainTradeRecords(Context context, AccountInfo accountInfo, TradeRecordResponse tradeRecordResponse) {
        ProductInfo productInfo = parseProductInfo(getContentFromLocal(context));
        // TODO：操作返回结果列表
        if (productInfo != null) {
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
}
