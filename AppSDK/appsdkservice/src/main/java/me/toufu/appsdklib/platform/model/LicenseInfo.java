package me.toufu.appsdklib.platform.model;

import org.json.JSONException;
import org.json.JSONObject;

import me.toufu.sdk.ProductInfo;

/**
 * Created by zhenghu on 15-5-8.
 */
public class LicenseInfo {
    public String signature;
    public String content;

    public long timeStamp;
    public String packageName;
    public String appId;
    public String imei;

    public ProductInfo productInfo;

    public String getAll() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("signature", signature);
        object.put("content", content);
        return object.toString();
    }
}
