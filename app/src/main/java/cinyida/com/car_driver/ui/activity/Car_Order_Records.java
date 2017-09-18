package cinyida.com.car_driver.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.net.result.Car_Records_Bean;
import cinyida.com.car_driver.ui.holder.Car_Order_Record_Holder;
import cinyida.com.car_driver.ui.present.CCar_Order_Records_Present;
import cinyida.com.car_driver.ui.view.Car_Records_View;
import cinyida.com.car_driver.utils.ToastUtils;

/**
 * Created by Zheng on 2017/5/16.
 */

public class Car_Order_Records extends BaseActivity implements Car_Records_View{

    private ImageView iv_back;
    private TextView tv_title,tv_extra_text;
    private ListView lv_order_records;
    private TextView tv_xinyong,tv_orders;
    private CCar_Order_Records_Present present;
    private ClipboardManager clipboardManager;
    private Car_Records_Bean bean;

    @Override
    protected void bindEvent() {
        lv_order_records.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null,bean.getRecord().get(i).getOrdernum()));
                ToastUtils.show(context,"订单号已复制到剪切板",0);
            }
        });
    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("车单记录");
    }

    @Override
    protected void initView() {
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        present = new CCar_Order_Records_Present(this);
        present.getNetData();
        lv_order_records = (ListView) findViewById(R.id.lv_order_records);
        tv_xinyong = (TextView) findViewById(R.id.tv_xinyong);
        tv_orders= (TextView) findViewById(R.id.tv_orders);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_car_order_records;
    }



    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void initData() {
        bean = present.getData();
        tv_xinyong.setText(bean.getDriver().getD_reputation()+"信用金");
        tv_orders.setText("抢"+ bean.getDriver().getAllorder()+"单");
        lv_order_records.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Car_Order_Record_Holder();
            }
        }, bean.getRecord()));
    }
}
