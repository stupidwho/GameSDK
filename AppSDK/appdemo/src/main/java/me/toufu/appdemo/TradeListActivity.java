package me.toufu.appdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import me.toufu.sdk.SubProductInfo;
import me.toufu.sdk.ProductInfo;


public class TradeListActivity extends ActionBarActivity {
    private ListView mListView;
    private TradeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        getSupportActionBar().setTitle("产品列表");

        String productInfo = getIntent().getStringExtra("productInfo");

        mListView = (ListView) findViewById(R.id.listViewTrade);
        mAdapter = new TradeAdapter(productInfo);
        mListView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_list, menu);
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

    private class TradeAdapter extends BaseAdapter {
        private List<SubProductInfo> mData = new ArrayList<>();

        public TradeAdapter(String pStr) {
            try {
                ProductInfo pInfo = new ProductInfo(pStr);
                for (SubProductInfo item : pInfo.subProducts) {
                    mData.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(TradeListActivity.this).inflate(R.layout.list_item_trade, null);
            TextView typeText = (TextView) convertView.findViewById(R.id.item_type);
            TextView desText = (TextView) convertView.findViewById(R.id.item_des);
            TextView priceText = (TextView) convertView.findViewById(R.id.item_price);
            TextView numText = (TextView) convertView.findViewById(R.id.item_num);
            SubProductInfo subInfo = mData.get(position);
            if (subInfo.isConsumer)
                typeText.setText("类型：可重复购买");
            else
                typeText.setText("类型：不可重复购买");
            desText.setText("描述：" + subInfo.description);
            priceText.setText("单价：" + subInfo.price);
            numText.setText("X" + subInfo.num);
            return convertView;
        }
    }
}
