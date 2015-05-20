package me.toufu.appsdklib.platform.pay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.libone.PayActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import me.toufu.appsdkservice.R;


public class TPayActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView mOrderText;
    private Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_pay);

        mOrderText = (TextView) findViewById(R.id.textOrder);
        mConfirmButton = (Button) findViewById(R.id.buttonBuyConfirm);

        mConfirmButton.setOnClickListener(this);
        mOrderText.setText(constructOrderShow());
    }

    private String constructOrderShow() {
        Intent intent = getIntent();

        String appInfo = intent.getStringExtra("appInfo");
        String accountInfo = intent.getStringExtra("accountInfo");
        String orderInfo = intent.getStringExtra("orderInfo");
        String content = "订单信息：\n";
        content += "应用id号：" + appInfo + "\n";
        content += "账户imei：" + accountInfo + "\n";
        content += "产品id：" + orderInfo;
        return content;
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
                Toast.makeText(
                        this,
                        data.getExtras().getString("result") + "  "
                                + data.getExtras().getInt("code"),
                        Toast.LENGTH_LONG).show();
                data.getExtras().getInt("code");// 1成功；-1失败；0取消
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
            payObj.put("amount", 1);
            String des = "";
            if (orderInfo.equals("1")) {
                des = "订阅xx报纸";
            } else {
                des = "解锁xx功能";
            }
            String display = "[{\"name\":\"商品\",\"contents\":[" + des + "]},]";
            payObj.put("display", display);

            extraObj.put("appInfo",appInfo);
            extraObj.put("accountInfo", accountInfo);
            extraObj.put("orderInfo", orderInfo);
            payObj.put("extras", extraObj.toString());
            String bill = payObj.toString();
            PayActivity.CallPayActivity(this, bill, PayConstants.URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
