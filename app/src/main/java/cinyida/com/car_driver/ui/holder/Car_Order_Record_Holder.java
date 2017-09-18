package cinyida.com.car_driver.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseViewHolder;
import cinyida.com.car_driver.net.result.Car_Records_Bean;
import cinyida.com.car_driver.utils.DateUtils;

/**
 * Created by Zheng on 2017/5/16.
 */

public class Car_Order_Record_Holder extends BaseViewHolder<Car_Records_Bean.RecordBean> {

    private TextView tv_start,textView12,textView10,tv_time,tv_ordertype,tv_end;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_start = (TextView) v.findViewById(R.id.tv_start);
        tv_end= (TextView) v.findViewById(R.id.tv_end);
        textView12= (TextView) v.findViewById(R.id.textView12);
        textView10= (TextView) v.findViewById(R.id.textView10);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        tv_ordertype= (TextView) v.findViewById(R.id.tv_ordertype);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, Car_Records_Bean.RecordBean s) {
        textView12.setText(s.getAddtime()+"");
        textView10.setText("订单号:"+s.getOrdernum());
        tv_start.setText("起点:"+s.getStart_add());
        tv_end.setText("终点:"+s.getStop_add());
        int state =s.getState();
        tv_time.setText(DateUtils.timedatemm(s.getAddtime()+""));
        int u_type=s.getU_type();
        if (u_type == 0){
            tv_ordertype.setText("实时");
        }else {
            tv_ordertype.setText("预约");
        }
        switch (state){
            case 1:
                textView12.setText("预约中");
                break;
            case 2:
                textView12.setText("待出发");
                break;
            case 3:
                textView12.setText("待支付");
                break;
            case 4:
                textView12.setText("等待乘客评价");
                break;
            case 5:
                textView12.setText("已完成");
                break;
            case 6:
                textView12.setText("乘客取消");
                break;
            case 7:
                textView12.setText("司机取消");
                break;
            case 8:
                textView12.setText("接到乘客");
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_car_order_record;
    }
}
