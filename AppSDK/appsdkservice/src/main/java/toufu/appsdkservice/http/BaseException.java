package toufu.appsdkservice.http;

/**
 * Created by toufu on 15-5-18.
 */
public class BaseException extends Exception{
    private int statusCode;
    private int businessCode;
    private String message;

    public BaseException(int status, int business, String msg) {
        statusCode = status;
        businessCode = business;
        message = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getBusinessCode() {
        return businessCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
