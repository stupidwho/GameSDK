package me.toufu.appsdklib.platform.pay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pingplusplus.libone.PayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import me.toufu.appsdklib.http.BaseException;
import me.toufu.appsdklib.http.ICallback;
import me.toufu.appsdklib.http.RequestBuilder;
import me.toufu.appsdklib.platform.AppInfoManager;
import me.toufu.appsdklib.utils.WidgetHelper;
import me.toufu.appsdkservice.R;
import me.toufu.sdk.SubProductInfo;
import me.toufu.sdk.TaskHelper;


public class TPayActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView appText;
    private TextView imeiText;
    private TextView desText;
    private TextView typeText;
    private TextView priceText;
    private Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_pay);

        getSupportActionBar().setTitle("产品");

        appText = (TextView) findViewById(R.id.textApp);
        imeiText = (TextView) findViewById(R.id.textAccount);
        desText = (TextView) findViewById(R.id.textDes);
        typeText = (TextView) findViewById(R.id.textType);
        priceText = (TextView) findViewById(R.id.textPrice);

        mConfirmButton = (Button) findViewById(R.id.buttonBuyConfirm);
        mConfirmButton.setOnClickListener(this);

        showOrderInfo();
    }

    private void showOrderInfo() {
        Intent intent = getIntent();

        String appInfo = intent.getStringExtra("appInfo");
        String accountInfo = intent.getStringExtra("accountInfo");
        String orderInfo = intent.getStringExtra("orderInfo");
        String des = "描述：";
        String price = "单价：";
        String type = "产品类型：";
        try {
            SubProductInfo subProduct = new SubProductInfo(new JSONObject(orderInfo));
            des += subProduct.description;
            price += subProduct.price;
            if (subProduct.isConsumer) {
                type += "可重复购买";
            } else {
                type += "不可重复购买";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        appInfo = "应用id号：" + appInfo;
        accountInfo = "账户imei：" + accountInfo;

        appText.setText(appInfo);
        imeiText.setText(accountInfo);
        desText.setText(des);
        typeText.setText(type);
        priceText.setText(price);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayActivity.PAYACTIVITY_REQUEST_CODE) {
            if (resultCode == PayActivity.PAYACTIVITY_RESULT_CODE) {
                WidgetHelper.showMessageDialog(this, "支付结果",
                        data.getExtras().getString("result")
                        + "  "
                        + data.getExtras().getInt("code"));
                if (data.getExtras().getInt("code") == 1) {
                    SubProductInfo info = AppInfoManager.getInstance().licenseInfo.productInfo.subProducts[0];
                    info.num ++;
                    try {
                        RequestBuilder.createRequest("r=user/update&id=1")
                                .paramte("id", "1")
                                .paramte("imei", AppInfoManager.getInstance().accountInfo.getImei())
                                .paramte("app_id", AppInfoManager.getInstance().appInfo.getAppId())
                                .paramte("products_record",info.toJsonObj().toString())
                                .asyncPost(new ICallback() {
                                    @Override
                                    public void onSuccess(String content) {

                                    }

                                    @Override
                                    public void onError(BaseException exception) {

                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonBuyConfirm) {
            payConfirm();
        }
    }

    private void payConfirm() {
        Intent intent = getIntent();

        String appInfo = intent.getStringExtra("appInfo");
        String accountInfo = intent.getStringExtra("accountInfo");
        String orderInfo = intent.getStringExtra("orderInfo");

        PayHelper.setWay();
        JSONObject payObj = new JSONObject();
        JSONObject extraObj = new JSONObject();
        try {
            payObj.put("order_no", "" + System.currentTimeMillis());
            payObj.put("amount", 1*100);
            String des;
            if (orderInfo.equals("1")) {
                des = "订阅xx报纸";
            } else {
                des = "解锁xx功能";
            }
            String display = "[{\"name\":\"商品\",\"contents\":[" + des + "]},]";
            payObj.put("display", display);

            extraObj.put("appInfo", appInfo);
            extraObj.put("accountInfo", accountInfo);
            extraObj.put("orderInfo", orderInfo);
            payObj.put("extras", extraObj.toString());
            String bill = payObj.toString();
//            bill = " {\"order_no\":\"201503230255221\",\"amount\":10,\"display\":[{\"name\":\"商品\",\"contents\":[\"橡胶花盆 x 1\",\"搪瓷水壶 x 1\",\"扫把和簸箕 x 1\"]}]}";
            PayActivity.CallPayActivity(this, bill, PayConstants.URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
