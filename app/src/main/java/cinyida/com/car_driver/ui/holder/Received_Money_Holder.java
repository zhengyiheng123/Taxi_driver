package cinyida.com.car_driver.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseViewHolder;
import cinyida.com.car_driver.net.result.MoneyResult;
import cinyida.com.car_driver.net.result.Money_Record_Bean;

/**
 * Created by Zheng on 2017/4/27.
 */

public class Received_Money_Holder extends BaseViewHolder<MoneyResult.RecordBean> {

    private TextView tv_yuan,tv_time,tv_banknum,tv_withdraw_text;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_yuan = (TextView) v.findViewById(R.id.tv_yuan);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        tv_banknum= (TextView) v.findViewById(R.id.tv_banknum);
        tv_withdraw_text= (TextView) v.findViewById(R.id.tv_withdraw_text);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, MoneyResult.RecordBean bean) {
        tv_yuan.setText(bean.getB_amount()+"元");
        tv_time.setText(bean.getB_time());
        tv_banknum.setText(bean.getB_bank());
        int type=bean.getB_type();
        if (type==1){
            tv_withdraw_text.setText("提现");
        }else if (type==0){
            tv_withdraw_text.setText("收到车费");
        }
//        tv_banknum.setText(bean.get);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_money_record;
    }
}
