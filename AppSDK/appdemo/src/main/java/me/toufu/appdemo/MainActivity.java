package me.toufu.appdemo;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import me.toufu.appsdklib.http.BaseException;
import me.toufu.appsdklib.http.ICallback;
import me.toufu.appsdklib.http.RequestBuilder;
import me.toufu.sdk.Platform;
import me.toufu.sdk.ValidateResponse;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Platform.init(MainActivity.this, AppConstants.appId, AppConstants.appKey);

        Platform.validateApp(MainActivity.this, new ValidateResponse() {
            @Override
            public void onResult(int code, String content) {
                switch(code) {
                    case ValidateResponse.RESULT_NOPROBLEM:
                        break;
                    case ValidateResponse.RESULT_ERROR_NETWORK_UNAVAILABLE:
                        break;
                    case ValidateResponse.RESULT_ERROR_UNKNOWN:
                        break;
                    case ValidateResponse.RESULT_ERROR_VALIDATE:
                        break;
                }
                WidgetHelper.showMessageDialog(MainActivity.this, "验证结果：", content);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
