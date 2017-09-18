package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.net.result.Money_Record_Bean;
import cinyida.com.car_driver.ui.holder.Received_Money_Holder;
import cinyida.com.car_driver.ui.present.ReceivedMoneyPresent;
import cinyida.com.car_driver.ui.present.Received_Money_Present;
import cinyida.com.car_driver.ui.view.ReceivedMoneyView;
import cinyida.com.car_driver.ui.view.Received_Money_View;

/**
 * Created by Zheng on 2017/4/27.
 */

public class Received_Money_Activity extends BaseActivity implements ReceivedMoneyView{

    private ListView lv_money_record;
    private BaseArrayAdapter adapter;
    private List<Money_Record_Bean> mList;
    private Received_Money_Present moneyPresent;
    private Button btn_withdraw;
    private ReceivedMoneyPresent present;
    private TextView tv_id_money;


    @Override
    protected void bindEvent() {
        btn_withdraw.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的余额");
        TextView tv_extra_text= (TextView) findViewById(R.id.tv_extra_text);
        tv_extra_text.setText("需要帮助");
        tv_extra_text.setOnClickListener(this);
        tv_extra_text.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initView() {
        tv_id_money = (TextView) findViewById(R.id.tv_id_money);
        present=new ReceivedMoneyPresent(this);
        present.getNetData();
        btn_withdraw = (Button) findViewById(R.id.btn_withdraw);
        lv_money_record= (ListView) findViewById(R.id.lv_money_record);
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_received_money;
    }



    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_extra_text:
                startActivity(HelpActivity.class);
                break;
            case R.id.btn_withdraw:
                startActivity(WithDrawActivity.class);
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
        if (TextUtils.isEmpty(present.getMoneyResult().getBalance())){
            tv_id_money.setText("0元");
        }else {
            tv_id_money.setText(present.getMoneyResult().getBalance()+"元");
        }

        lv_money_record.setAdapter(new BaseArrayAdapter<>(getApplicationContext(), new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Received_Money_Holder();
            }
        },present.getMoneyResult().getRecord()));
    }
}
