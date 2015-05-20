package me.toufu.appsdkservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import me.toufu.sdk.Platform;
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

        }

        @Override
        public void obtainTradeRecords(String accountInfo, ITradeRecordResponse tradeRecordResponse) throws RemoteException {

        }
    }
}
