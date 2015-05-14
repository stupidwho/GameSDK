package me.toufu.appsdklib.platform.model;

/**
 * Created by zhenghu on 15-4-29.
 */
public class AccountInfo {
    private String imei;
    private String sn;

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
}
