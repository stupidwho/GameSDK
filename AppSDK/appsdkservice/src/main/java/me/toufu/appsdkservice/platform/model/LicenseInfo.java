package me.toufu.appsdkservice.platform.model;

import java.util.List;

import me.toufu.appsdkservice.sdk.ProductInfo;

/**
 * Created by zhenghu on 15-5-8.
 */
public class LicenseInfo {
    public byte signature[];
    public byte content[];

    public long timeStamp;
    public String appId;
    public String appKey;
    public String pkgName;

    public String imei;
    public String sn;

    public List<ProductInfo> productInfos;

    public String toString() {
        return null;
    }
}
