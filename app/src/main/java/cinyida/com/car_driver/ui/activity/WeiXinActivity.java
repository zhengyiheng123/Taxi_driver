package cinyida.com.car_driver.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;

/**
 * Created by Zheng on 2017/6/20.
 */

public class WeiXinActivity extends BaseActivity {
    @Override
    protected void bindEvent() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("微信二维码");
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_weixin;
    }

    @Override
    protected void BaseOnclick(View view) {

    }
}
