// IPlatformService.aidl
package me.toufu.sdk;

// Declare any non-default types here with import statements

interface IPlatformService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void validateApp(ValidateResponse validateResponse);
    void pay(AppInfo appInfo, AccountInfo accountInfo, OrderInfo orderInfo, PayResponse payResponse);
    void obtainTradeRecords(AccountInfo accountInfo, TradeRecordResponse tradeRecordResponse)
}
