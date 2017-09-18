package cinyida.com.car_driver.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;

/**
 * Created by Zheng on 2017/5/31.
 */

public class Activity_SetPassword extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_extra;

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("设置登录密码");
        tv_extra = (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setText("完成");
        tv_extra.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_setpassword;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_extra_text:
                startActivity(RegisterActivity.class);
                break;
        }
    }
}
