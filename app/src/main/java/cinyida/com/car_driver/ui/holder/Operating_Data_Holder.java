package cinyida.com.car_driver.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseViewHolder;
import cinyida.com.car_driver.net.result.UserCenterResult;
import cinyida.com.car_driver.utils.DateUtils;

/**
 * Created by Zheng on 2017/5/16.
 */

public class Operating_Data_Holder extends BaseViewHolder<UserCenterResult.OrderBean> {

    private TextView textView,tv_have_cancel,tv_target,tv_start,tv_ordernum,tv_ordertype;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_have_cancel= (TextView) v.findViewById(R.id.tv_have_cancel);
        textView = (TextView) v.findViewById(R.id.textView);
        tv_target= (TextView) v.findViewById(R.id.tv_target);
        tv_start= (TextView) v.findViewById(R.id.tv_start);
        tv_ordernum= (TextView) v.findViewById(R.id.tv_ordernum);
        tv_ordertype= (TextView) v.findViewById(R.id.tv_ordertype);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, UserCenterResult.OrderBean s) {
        tv_start.setText("目的地："+s.getStop_add());
        tv_target.setText("出发地："+s.getStart_add());
        int u_type=s.getU_type();
        if (u_type == 0){
            tv_ordertype.setText("实时");
        }else {
            tv_ordertype.setText("预约");
        }
        switch (s.getState()){
            case 1:
                tv_have_cancel.setText("预约中");
                break;
            case 2:
                tv_have_cancel.setText("待出发");
                break;
            case 3:
                tv_have_cancel.setText("待支付");
                break;
            case 4:
                tv_have_cancel.setText("等待乘客评价");
                break;
            case 5:
                tv_have_cancel.setText("已完成");
                break;
            case 6:
                tv_have_cancel.setText("乘客取消");
                break;
            case 7:
                tv_have_cancel.setText("司机取消");
                break;
            case 8:
                tv_have_cancel.setText("接到乘客");
                break;
        }
        textView.setText(DateUtils.timedatemm(s.getAddtime()+""));
        tv_ordernum.setText("订单号:"+s.getOrdernum());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_operating;
    }
}
