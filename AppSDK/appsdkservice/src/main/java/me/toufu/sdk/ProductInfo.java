package me.toufu.sdk;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhenghu on 15-4-29.
 */
public class ProductInfo implements Parcelable{
    public String description;
    public SubProductInfo subProducts[];

    public ProductInfo(String jsonStr) throws JSONException {
        JSONObject productObj = new JSONObject(jsonStr);
        description = productObj.getString("description");
        JSONArray jsonArray = productObj.getJSONArray("subProducts");
        subProducts = new SubProductInfo[jsonArray.length()];
        for(int i=0; i<subProducts.length; i++) {
            subProducts[i] = new SubProductInfo(jsonArray.getJSONObject(i));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
