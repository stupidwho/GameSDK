package me.toufu.appsdkservice;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import org.json.JSONException;

import me.toufu.appsdklib.platform.AppInfoManager;
import me.toufu.appsdklib.platform.pay.TPayActivity;
import me.toufu.sdk.AccountInfo;
import me.toufu.sdk.Platform;
import me.toufu.sdk.ProductInfo;
import me.toufu.sdk.TradeRecordResponse;
import me.toufu.sdk.ValidateResponse;
import me.toufu.sdk.service.IPayResponse;
import me.toufu.sdk.service.IPlatformService;
import me.toufu.sdk.service.ITradeRecordResponse;
import me.toufu.sdk.service.IValidateResponse;

public class PlatformService extends Service {
    public PlatformService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        String appId = intent.getStringExtra("appId");
        String appKey = intent.getStringExtra("appKey");
        Platform.init(getApplicationContext(), appId, appKey);
        return new ServiceBinder();
    }

    public class ServiceBinder extends IPlatformService.Stub {
        @Override
        public void validateApp(final IValidateResponse validateResponse) throws RemoteException {
            Platform.validateApp(getApplicationContext(), new ValidateResponse() {
                @Override
                public void onResult(int code, String content) {
                    try {
                        validateResponse.onResult(code, content);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void pay(String appInfo, String accountInfo, String orderInfo, IPayResponse payResponse) throws RemoteException {
            Intent intent = new Intent(PlatformService.this, TPayActivity.class);
            intent.putExtra("appInfo", appInfo);
            intent.putExtra("accountInfo", accountInfo);
            intent.putExtra("orderInfo", orderInfo);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        @Override
        public void obtainTradeRecords(String accountInfo, final ITradeRecordResponse tradeRecordResponse) throws RemoteException {
            AccountInfo theAccountInfo = AppInfoManager.getInstance().accountInfo;
            Platform.obtainTradeRecords(PlatformService.this, theAccountInfo, new TradeRecordResponse() {
                @Override
                public void onResult(int code, String message, ProductInfo productInfo) {
                    try {
                        tradeRecordResponse.onResult(code, message, productInfo.toJsonStr());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
