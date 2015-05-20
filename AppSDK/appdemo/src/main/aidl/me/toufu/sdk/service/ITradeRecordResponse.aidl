// ITradeRecordResponse.aidl
package me.toufu.sdk.service;

// Declare any non-default types here with import statements

interface ITradeRecordResponse {
    void onResult(int code, String message, String productInfo);
}
