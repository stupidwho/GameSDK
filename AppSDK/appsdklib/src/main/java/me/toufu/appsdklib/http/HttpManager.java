package me.toufu.appsdklib.http;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by toufu on 15-5-18.
 */
public class HttpManager {
    private static HttpManager sInstance;

    public static HttpManager getInstance() {
        if (sInstance == null) {
            sInstance = new HttpManager();
        }
        return sInstance;
    }

    public HttpClient httpClient;
    private HandlerThread mRequestThread;
    private Handler mRequsetHandler;

    private Handler mUiHandler;

    private HttpManager() {
        httpClient = new DefaultHttpClient();
        mRequestThread = new HandlerThread("toufu-RequestThread");
        mRequestThread.start();
        mRequsetHandler = new Handler(mRequestThread.getLooper());
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    public void runOnThread(Request request) {
        mRequsetHandler.post(request);
    }

    public void runOnUiThread(Runnable runnable) {
        mUiHandler.post(runnable);
    }
}
