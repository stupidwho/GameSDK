package me.toufu.sdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhenghu on 15-4-29.
 */
public class ProductInfo implements Parcelable{
    public String description;
    public SubProductInfo mSubProducts[];

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
