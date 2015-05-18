package me.toufu.sdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhenghu on 15-4-29.
 */
public class AccountInfo implements Parcelable{
    private String imei;
    private String sn;

    public AccountInfo(Parcel src) {
        imei = src.readString();
        sn = src.readString();
    }

    public AccountInfo(String imei, String sn) {
        this.imei = imei;
        this.sn = sn;
    }

    public String getImei() {
        return imei;
    }

    public String getSn() {
        return sn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imei);
        dest.writeString(sn);
    }

    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {

        @Override
        public AccountInfo createFromParcel(Parcel source) {
            return new AccountInfo(source);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };
}
