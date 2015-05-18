package me.toufu.appsdklib.platform;

import android.content.Context;
import android.util.Log;

import me.toufu.appsdklib.http.BaseException;
import me.toufu.appsdklib.http.ICallback;
import me.toufu.appsdklib.http.RequestBuilder;
import me.toufu.sdk.AccountInfo;
import me.toufu.sdk.AppInfo;

/**
 * Created by toufu on 15-5-17.
 */
public class NetworkManager {
    private static final String TAG = "NetworkManager";

    public static final String PATH_USER = "user/";

    public static NetworkManager sInstance;

    public static NetworkManager getInstance(Context context) {
        if (sInstance == null)
            sInstance = new NetworkManager(context);
        return sInstance;
    }

    private Context mContext;

    private NetworkManager(Context context) {
        mContext = context;
    }

    public String getLicenseInfo() {
        // TODO：从服务端获取LicenseInfo
        /*AppInfo appInfo = AppInfoManager.getInstance().appInfo;
        AccountInfo accountInfo = AppInfoManager.getInstance().accountInfo;
        try {
            return RequestBuilder.createRequest(PATH_USER + accountInfo.getImei())
                    .get();
        } catch (BaseException e) {
            Log.e(TAG, e.toString());
        }*/
        return null;
    }
}
