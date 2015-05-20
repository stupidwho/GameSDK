// IPlatformService.aidl
package me.toufu.sdk.service;

import me.toufu.sdk.service.IPayResponse;
import me.toufu.sdk.service.ITradeRecordResponse;
import me.toufu.sdk.service.IValidateResponse;

interface IPlatformService {
     void validateApp(IValidateResponse validateResponse);
     void pay(String appInfo, String accountInfo, String orderInfo, IPayResponse payResponse);
     void obtainTradeRecords(String accountInfo, ITradeRecordResponse tradeRecordResponse);
}
