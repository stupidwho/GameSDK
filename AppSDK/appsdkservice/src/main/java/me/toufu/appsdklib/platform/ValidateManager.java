package me.toufu.appsdklib.platform;

import android.content.Context;

import me.toufu.appsdklib.platform.model.LicenseInfo;
import me.toufu.appsdklib.utils.NetworkUtil;
import me.toufu.appsdklib.utils.SignatureUtil;
import me.toufu.sdk.ValidateResponse;
import me.toufu.sdk.ValidateResult;

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
            mValidateResult.code = ValidateResponse.RESULT_ERROR_NETWORK_UNAVAILABLE;
            mValidateResult.message = "网络错误";
        }

        LicenseInfo contentLocal = getContentFromLocal();
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

    public LicenseInfo getContentFromNet() {
        // 从服务器获取数据
        String content = NetworkManager.getInstance(mContext).getLicenseInfo();
        return getLicenseInfoFromString(content);
    }

    public LicenseInfo getContentFromLocal() {
        // 从本地获取数据
        String content = FileManager.getLicense(mContext);
        return getLicenseInfoFromString(content);
    }

    public LicenseInfo getLicenseInfoFromString(String content) {
        LicenseInfo info = ValidateHelper.parseLicenseInfo(content);
        if (info != null) {
            if (ValidateHelper.validateSignature(info)) {
                return info;
            }
        }
        return null;
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
        return SignatureUtil.isSignatureLegal(null, null, null);
    }
}
