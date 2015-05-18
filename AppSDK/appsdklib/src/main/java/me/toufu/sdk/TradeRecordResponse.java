package me.toufu.sdk;

/**
 * Created by zhenghu on 15-5-8.
 */
public interface TradeRecordResponse {
    public static final int TRADE_RESULT_NOPROBLEM = 0;
    public static final int TRADE_RESULT_ERROR_UNKNOWN = -1;
    public static final int TRADE_RESULT_ERROR_FORMAT = -2;

    public void onResult(int code, String message, ProductInfo productInfo);
}
