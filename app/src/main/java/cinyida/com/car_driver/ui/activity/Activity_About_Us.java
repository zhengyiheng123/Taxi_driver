package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;

/**
 * Created by Zheng on 2017/5/16.
 */

public class Activity_About_Us extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;


    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("关于我们");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_aboutus;
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
