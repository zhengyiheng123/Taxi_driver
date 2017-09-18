package cinyida.com.car_driver.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseViewHolder;
import cinyida.com.car_driver.net.result.HelpBean;

/**
 * Created by Zheng on 2017/6/19.
 */

public class HelpHolder extends BaseViewHolder<HelpBean.QuestionBean> {

    private TextView tv_title;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, HelpBean.QuestionBean helpBean) {
        tv_title.setText(helpBean.getTitle());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_helper;
    }
}
