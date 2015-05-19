package toufu.appsdkservice.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toufu on 15-5-18.
 */
public class Request implements Runnable {
    private List<NameValuePair> mParams = new ArrayList<>();
    private String mUrl;
    private HttpUriRequest mUriRequest;
    private ICallback mCallback;

    public Request(String url) {
        mUrl = url;
    }

    public Request paramte(String key, String value) {
        mParams.add(new BasicNameValuePair(key, value));
        return this;
    }

    public Request paramte(String key, int value) {
        String content = Integer.toString(value);
        mParams.add(new BasicNameValuePair(key, content));
        return this;
    }

    public Request paramte(String key, long value) {
        String content = Long.toString(value);
        mParams.add(new BasicNameValuePair(key, content));
        return this;
    }

    public String get() throws BaseException {
        mUriRequest = new HttpGet(mUrl);
        return doRequest();
    }

    public void post(final ICallback callback) {
        HttpPost post = new HttpPost(mUrl);
        try {
            post.setEntity(new UrlEncodedFormEntity(mParams, HTTP.UTF_8));
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
            runOnUi(new Runnable() {
                @Override
                public void run() {
                    callback.onError(new BaseException(200, -400, "Url解码错误：" + e.toString()));
                }
            });
            return;
        }
        mUriRequest = post;
        mCallback = callback;
        postIntoThread();
    }

    public void put(final ICallback callback) {
        HttpPut put = new HttpPut(mUrl);
        try {
            put.setEntity(new UrlEncodedFormEntity(mParams, HTTP.UTF_8));
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
            runOnUi(new Runnable() {
                @Override
                public void run() {
                    callback.onError(new BaseException(200, -400, "Url解码错误：" + e.toString()));
                }
            });
            return;
        }
        mUriRequest = put;
        mCallback = callback;
        postIntoThread();
    }

    public void delete(ICallback callback) {
        mUriRequest = new HttpDelete(mUrl);
        mCallback = callback;
        postIntoThread();
    }

    public void asyncGet(ICallback callback) {
        mUriRequest = new HttpGet(mUrl);
        mCallback = callback;
        postIntoThread();
    }

    private void postIntoThread() {
        HttpManager.getInstance().runOnThread(this);
    }

    private String doRequest() throws BaseException {
        int status = -1;
        try {
            HttpResponse httpResponse;
            httpResponse = HttpManager.getInstance().httpClient.execute(mUriRequest);
            status = httpResponse.getStatusLine().getStatusCode();
            HttpEntity responseEntity = httpResponse.getEntity();
            final String response = EntityUtils.toString(responseEntity);
            return response;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            final int finalStatus = status;
            final String eString = e.toString();
            throw new BaseException(status, -400, eString);
        } catch (IOException e) {
            e.printStackTrace();
            final int finalStatus1 = status;
            final String eString = e.toString();
            throw new BaseException(status, -400, eString);
        }
    }

    @Override
    public void run() {
        try {
            doRequest();
        } catch (final BaseException e) {
            if (mCallback != null) {
                runOnUi(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onError(e);
                    }
                });
            }
        }
    }

    private void runOnUi(Runnable runnable) {
        HttpManager.getInstance().runOnUiThread(runnable);
    }
}
