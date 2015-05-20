package me.toufu.appsdklib.platform.pay;

import com.pingplusplus.libone.PayActivity;

/**
 * Created by toufu on 15-5-20.
 */
public class PayHelper {
    public static void setWay() {
        //设置要使用的支付方式

        //打开微信支付按钮
        PayActivity.SHOW_CHANNEL_WECHAT = true;
        //打开银联支付按钮
        PayActivity.SHOW_CHANNEL_UPMP = false;
        //打开百度支付按钮
        PayActivity.SHOW_CHANNEL_BFB = true;
        //打开支付宝按钮
        PayActivity.SHOW_CHANNEL_ALIPAY = true;
    }
}
