package cinyida.com.car_driver.ui.activity;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zheng on 2017/4/27.
 */

public class WithDrawActivity extends BaseActivity {

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_extra_text:
                startActivity(HelpActivity.class);
                break;
        }
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("提现");
        TextView tv_extra= (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.GONE);
        tv_extra.setText("需要帮助");
        tv_extra.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_withdraw;
    }
}
