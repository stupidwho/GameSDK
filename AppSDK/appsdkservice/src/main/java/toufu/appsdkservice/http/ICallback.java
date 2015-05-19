package toufu.appsdkservice.http;

/**
 * Created by toufu on 15-5-18.
 */
public interface ICallback {
    public void onSuccess(String content);
    public void onError(BaseException exception);
}
