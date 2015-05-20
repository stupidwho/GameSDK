package me.toufu.appsdklib.http;

/**
 * Created by toufu on 15-5-18.
 */
public class RequestBuilder {
    public static final String URL_HOST = "http://www.nidama.com/AppSDK/index.php?";

    public static Request createRequest(String url) {
        return new Request(URL_HOST + url);
    }
}
