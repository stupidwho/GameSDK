package me.toufu.sdk;

/**
 * Created by zhenghu on 15-5-8.
 */
public interface ValidateResponse {
    public static final int RESULT_NOPROBLEM = 0;
    public static final int RESULT_ERROR_NETWORK = -1;
    public static final int RESULT_ERROR_OUT_OF_DATE = -2;
    public static final int RESULT_ERROR_PARAM = -3;
    public static final int RESULT_ERROR_VALIDATE = -4;
    public static final int RESULT_ERROR_PARSE = -5;
    public static final int RESULT_ERROR_UNKNOWN = -6;

    public void onResult(int code, String content);
}
