package com.meizu.game.demo.mz;

import org.json.JSONObject;

import com.meizu.gamecenter.sdk.LoginResultCode;
import com.meizu.gamecenter.sdk.MzAccountInfo;
import com.meizu.gamecenter.sdk.MzBuyInfo;
import com.meizu.gamecenter.sdk.MzGameBarPlatform;
import com.meizu.gamecenter.sdk.MzGameCenterPlatform;
import com.meizu.gamecenter.sdk.MzLoginListener;
import com.meizu.gamecenter.sdk.MzPayListener;
import com.meizu.gamecenter.sdk.PayResultCode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private TextView mMsgText;
	private String mUid;
    private String mSession;
    //gamebar操作实例,不需要悬浮窗的可以不用
    MzGameBarPlatform mzGameBarPlatform;
    
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		mMsgText = (TextView)findViewById(R.id.msgText);
		
		//初始化，可以指定gamebar第一次显示的位置，在游戏退出时会记住用户操作的最后一次位置，再次启动时使用上一次的位置
        //第一次显示的位置可以指定四个方向，左上，左下，右上，右下
        //    public static final int GRAVITY_LEFT_TOP = 1;
        //    public static final int GRAVITY_LEFT_BOTTOM = 2;
        //    public static final int GRAVITY_RIGHT_TOP = 3;
        //    public static final int GRAVITY_RIGHT_BOTTOM = 4;
		//　游戏登录后不能显示悬浮窗问题应检查下系统是否允许魅族游戏框架使用悬浮窗权限（在MIUI中可能会遇到）,具体可查看接入文档
        mzGameBarPlatform = new MzGameBarPlatform(this, MzGameBarPlatform.GRAVITY_RIGHT_BOTTOM);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 //调一下onActivityResume
        mzGameBarPlatform.onActivityResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 //调一下onActivityPause
        mzGameBarPlatform.onActivityPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// TODO 游戏退出调用该方法
		MzGameCenterPlatform.logout(this);
		 //调一下onActivityDestroy
		//如果游戏用Sysetm.exit(0)或者killProcess方式退出，需在退出前主动调用mzGameBarPlatform.onActivityPause()和mzGameBarPlatform.onActivityDestroy();
		//否则可能会出现游戏退出后悬浮窗还在桌面
		mzGameBarPlatform.onActivityDestroy();
	}
	
	private void displayMsg(String msg){
		mMsgText.setText(msg);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.loginBtn:
			login();
			break;
		case R.id.payBtn:
			// TODO 订单信息需要游戏客户端从自己的服务端生成，签名的方式需要保存在服务端已确保安全
			pay();
			break;
		}
	}
	
	private void login(){
		// TODO 调用登录接口
		MzGameCenterPlatform.login(MainActivity.this, new MzLoginListener() {			
			@Override
			public void onLoginResult(int code, MzAccountInfo accountInfo, String errorMsg) {
				// TODO 登录结果回调，该回调跑在应用主线程
				switch(code){
				case LoginResultCode.LOGIN_SUCCESS:
					// TODO 登录成功，拿到uid 和 session到自己的服务器去校验session合法性
					mUid = accountInfo.getUid();
                    mSession = accountInfo.getSession();
//                    validate();
					displayMsg("登录成功！\r\n 用户名：" + accountInfo.getName() + "\r\n Uid：" + accountInfo.getUid() + "\r\n session：" + accountInfo.getSession());
					break;
				case LoginResultCode.LOGIN_ERROR_CANCEL:
					// TODO 用户取消登陆操作
					break;
				default:
					// TODO 登陆失败，包含错误码和错误消息。
					// TODO 注意，错误消息需要由游戏展示给用户，错误码可以打印，供调试使用
					displayMsg("登录失败 : " + errorMsg + " , code = " + code);
					break;
				}				
			}
		});
	}

    private void validate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String resonse = UrlRequest.request("http://192.168.1.106/AppSDK/index.php?r=pay/validate&uid="+mUid+"&session"+mSession);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, resonse, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();

    }
	
	private void pay(){
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("请稍等...");
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		AsyncTask<Void, Void, MzBuyInfo> task = new AsyncTask<Void, Void, MzBuyInfo>(){
			@Override
			protected MzBuyInfo doInBackground(Void... params) {
				MzBuyInfo info = createOrder();
				progressDialog.dismiss();
				return info;
			}

			@Override
			protected void onPostExecute(MzBuyInfo result) {
				if(result != null){
					startPay(result);
				}else{
					displayMsg("生成订单失败.");
				}
			}
			
		};
		task.execute();
	}
	
	private MzBuyInfo createOrder(){
		if(!TextUtils.isEmpty(mUid)){
			try{
				// TODO 以下信息的获取需要游戏厂商访问自己的服务器，生成对应的订单信息
				//　其中cp_order_id、sign、sign_type、appid、uid参数不能为空或空字符串
//				String url = "http://192.168.1.106/AppSDK/index.php?r=pay/order&product_id=1";
                String url = "http://192.168.1.106/AppSDK/index.php?r=pay/order&product_id=1&uid="+mUid+"&session=" + mSession;
				String res = UrlRequest.request(url);
				JSONObject object = new JSONObject(res);
				object = object.getJSONObject("value");
				String orderId = object.getString("cp_order_id");
				String sign = object.getString("sign");
				String signType = object.getString("sign_type");
				int buyCount = object.getInt("buy_amount");
//				String cpUserInfo = object.has("user_info") ? object.getString("user_info") : "";
				String amount = object.has("total_price") ? object.getString("total_price") : "0";
				String productId = object.getString("product_id");
//				String productSubject = object.getString("product_subject");
//				String productBody = object.getString("product_body");
//				String productUnit = object.getString("product_unit");
				String appid = object.getString("app_id");
				String uid = object.getString("uid");
//				String perPrice = object.getString("product_per_price");
//				long createTime = object.getLong("create_time");
//				int payType = object.getInt("pay_type");
				return new MzBuyInfo().setBuyCount(buyCount)./*setCpUserInfo(cpUserInfo)
						.*/setOrderAmount(amount).setOrderId(orderId)./*setPerPrice(perPrice)
						.setProductBody(productBody).*/setProductId(productId)./*setProductSubject(productSubject)
						.setProductUnit(productUnit).*/setSign(sign).setSignType(signType)/*.setCreateTime(createTime)*/
						.setAppid(appid).setUserUid(uid)/*.setPayType(payType)*/;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void startPay(MzBuyInfo buyInfo){
		MzGameCenterPlatform.payOnline(MainActivity.this, buyInfo, new MzPayListener() {			
			@Override
			public void onPayResult(int code, MzBuyInfo info, String errorMsg) {
				// TODO 支付结果回调，该回调跑在应用主线程
				switch(code){
				case PayResultCode.PAY_SUCCESS:
					// TODO 如果成功，接下去需要到自己的服务器查询订单结果
					displayMsg("支付成功 : " + info.getOrderId());
					break;
				case PayResultCode.PAY_ERROR_CANCEL:
					// TODO 用户取消支付操作
					break;
				default:
					// TODO 支付失败，包含错误码和错误消息。
					// TODO 注意，错误消息需要由游戏展示给用户，错误码可以打印，供调试使用
					displayMsg("支付失败 : " + errorMsg + " , code = " + code);
					break;
				}
			}
		});
	}
}
