package me.toufu.sdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhenghu on 15-4-29.
 */
public class AppInfo implements Parcelable {
    private String appId;
    private String appKey;
    private String pkgName;

    public AppInfo(Parcel src) {
        appId = src.readString();
        appKey = src.readString();
        pkgName = src.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appId);
        dest.writeString(appKey);
        dest.writeString(pkgName);
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {

        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }

    };
}
