package me.toufu.sample.platform;

import android.content.Context;

import me.toufu.sample.platform.model.LicenseInfo;
import me.toufu.sample.sdk.ValidateResponse;
import me.toufu.sample.sdk.ValidateResult;
import me.toufu.sample.utils.NetworkUtil;
import me.toufu.sample.utils.SignatureUtil;

/**
 * Created by zhenghu on 15-5-11.
 */
public class ValidateManager {
    private Context mContext;

    private ValidateResult mValidateResult;

    public ValidateManager(Context context) {
        mContext = context;
        mValidateResult = new ValidateResult();
    }

    public ValidateResult validateApp() {
        LicenseInfo contentNet = null;
        if (NetworkUtil.isNetworkConnected(mContext)) {
            contentNet = getContentFromNet();
        } else {
            mValidateResult.code = ValidateResponse.RESULT_ERROR_NETWORK;
            mValidateResult.message = "网络错误";
        }

        LicenseInfo contentLocal = getContentFromLocal(mContext);
        LicenseInfo aimContent = compareContent(contentLocal, contentNet);
        if (aimContent != null) {
            if (isLegal(aimContent)) {
                mValidateResult.code = ValidateResponse.RESULT_NOPROBLEM;
                mValidateResult.message = "验证无误";
                FileManager.saveLicense(mContext, aimContent.toString());
            } else {
                mValidateResult.code = ValidateResponse.RESULT_ERROR_VALIDATE;
                mValidateResult.message = "签名不合法";
            }
        }
        return mValidateResult;
    }

    public static LicenseInfo getContentFromNet() {
        // TODO：从服务器获取数据
        String content = "";
        LicenseInfo info = ValidateHelper.parseLicenseInfo(content);
        return info;
    }

    public static LicenseInfo getContentFromLocal(Context context) {
        String content = FileManager.getLicense(context);
        LicenseInfo info = ValidateHelper.parseLicenseInfo(content);
        return info;
    }

    private LicenseInfo compareContent(LicenseInfo contentLocal, LicenseInfo contentNet) {
        if (contentNet != null)
            return contentNet;
        if (contentLocal != null)
            return contentLocal;
        // 均为null无法验证
        mValidateResult.code = ValidateResponse.RESULT_ERROR_PARSE;
        mValidateResult.message = "内容解析错误";
        return null;
    }

    // 验证签名是否合法
    private boolean isLegal(LicenseInfo info) {
        String content = "";
        return SignatureUtil.isSignatureLegal(content, "", "");
    }
}
