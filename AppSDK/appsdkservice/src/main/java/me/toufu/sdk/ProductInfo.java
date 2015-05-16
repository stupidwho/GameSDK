package me.toufu.sdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhenghu on 15-4-29.
 */
public class ProductInfo implements Parcelable{
    public static final int PRODUCT_TYPE_ALL = 0;
    public static final int PRODUCT_TYPE_CONSUMER = 1;
    public static final int PRODUCT_TYPE_NOT_CONSUMER = 2;
    public int type;
    public String productId;
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
