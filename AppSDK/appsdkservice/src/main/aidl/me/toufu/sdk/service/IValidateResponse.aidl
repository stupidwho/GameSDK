// IValidateResponse.aidl
package me.toufu.sdk.service;

// Declare any non-default types here with import statements

interface IValidateResponse {
    void onResult(int code, String content);
}
