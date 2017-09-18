package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.ui.holder.Car_Order_Record_Holder;

/**
 * Created by Zheng on 2017/5/16.
 */

public class Orders_History_Activity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private ListView lv_order_records;


    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("车单历史记录");
    }

    @Override
    protected void initView() {
        List<String> mList=new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        lv_order_records = (ListView) findViewById(R.id.lv_order_records);
        lv_order_records.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Car_Order_Record_Holder();
            }
        },mList));
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_orders_history;
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
