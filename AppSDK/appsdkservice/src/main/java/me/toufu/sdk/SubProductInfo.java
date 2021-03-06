package me.toufu.sdk;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhenghu on 15-5-15.
 */
public class SubProductInfo implements Parcelable{
    public static final int SUBPRODUCT_TYPE_CONSUMER = 1;
    public static final int SUBPRODUCT_TYPE_NOT_CONSUMER = 2;

    public boolean isConsumer;
    public int price;
    public String subId;
    public int num;
    public String description;

    public SubProductInfo(JSONObject obj) throws JSONException {
        isConsumer = obj.getBoolean("isConsumer");
        price = obj.getInt("price");
        subId = obj.getString("subId");
        num = obj.getInt("num");
        description = obj.getString("description");
    }

    public JSONObject toJsonObj() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("isConsumer",isConsumer);
        obj.put("price", price);
        obj.put("subId", subId);
        obj.put("num", num);
        obj.put("description", description);
        return obj;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
