package me.toufu.sdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhenghu on 15-5-15.
 */
public class SubProductInfo implements Parcelable{
    public boolean isConsumer;
    public int price;
    public String subId;
    public String productId;
    public long num;
    public String description;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
