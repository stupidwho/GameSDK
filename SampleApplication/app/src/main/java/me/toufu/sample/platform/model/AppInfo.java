package me.toufu.sample.platform.model;

/**
 * Created by zhenghu on 15-4-29.
 */
public class AppInfo {
    private String appId;
    private String appKey;
    private String pkgName;

    public AppInfo(String appId, String appKey, String pkgName) {
        this.appId = appId;
        this.appKey = appKey;
        this.pkgName = pkgName;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getPkgName() {
        return pkgName;
    }
}
