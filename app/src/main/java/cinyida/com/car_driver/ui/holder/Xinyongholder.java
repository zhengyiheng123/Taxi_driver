package cinyida.com.car_driver.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseViewHolder;
import cinyida.com.car_driver.net.result.XinYongBean;
import cinyida.com.car_driver.utils.DateUtils;

/**
 * Created by Zheng on 2017/6/20.
 */

public class Xinyongholder  extends BaseViewHolder<XinYongBean.CreditBean>{

    private TextView tv_time,tv_content,tv_fenshu;
    Context context;
    public Xinyongholder(Context context){
        this.context=context;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_time = (TextView) v.findViewById(R.id.tv_time);
        tv_content= (TextView) v.findViewById(R.id.tv_content);
        tv_fenshu= (TextView) v.findViewById(R.id.tv_fenshu);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, XinYongBean.CreditBean o) {
        tv_content.setText(o.getD_details());
        tv_time.setText(DateUtils.timedatemm(o.getD_time()+""));
        int type=o.getD_type();
        if (type == 0){
            tv_fenshu.setTextColor(context.getResources().getColor(R.color.material_red_500));
            tv_fenshu.setText("-"+o.getD_number());
        }else {
            tv_fenshu.setTextColor(context.getResources().getColor(R.color.material_green_500));
            tv_fenshu.setText("+"+o.getD_number());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_xinyong;
    }
}
