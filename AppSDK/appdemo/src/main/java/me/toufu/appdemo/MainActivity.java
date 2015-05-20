package me.toufu.appdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import me.toufu.sdk.ValidateResponse;
import me.toufu.sdk.service.IPlatformService;
import me.toufu.sdk.service.IValidateResponse;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private ServiceConnection mServiceConnection;
    private IPlatformService mPlatformService;

    private Button button;

    private Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPlatformService.validateApp(new IValidateResponse.Stub() {
                        @Override
                        public void onResult(final int code, final String content) throws RemoteException {
                            runOnUi(new Runnable() {
                                @Override
                                public void run() {
                                    switch (code) {
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
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mPlatformService = IPlatformService.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // Do nothing
            }
        };
        Intent intent = new Intent("me.toufu.sdk.action.service");
        intent.putExtra("appId", AppConstants.appId);
        intent.putExtra("appKey", AppConstants.appKey);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
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

    @Override
    protected void onDestroy() {
        if (mServiceConnection != null)
            unbindService(mServiceConnection);
        super.onDestroy();
    }

    private void runOnUi(Runnable r) {
        mUiHandler.post(r);
    }
}
