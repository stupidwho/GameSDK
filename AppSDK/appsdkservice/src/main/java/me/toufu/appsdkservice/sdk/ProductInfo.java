package me.toufu.appsdkservice.sdk;

import java.util.List;

/**
 * Created by zhenghu on 15-4-29.
 */
public class ProductInfo {
    public static final int PRODUCT_TYPE_ALL = 0;
    public static final int PRODUCT_TYPE_CONSUMER = 1;
    public static final int PRODUCT_TYPE_NOT_CONSUMER = 2;
    public int type;
    public String productId;
    public String description;
    public List<SubProductInfo> mSubProducts;
}
