package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.present.Target_Address_Present;
import cinyida.com.car_driver.ui.view.Target_Address_View;

/**
 * Created by Zheng on 2017/5/15.
 */

public class Target_Address_Activity extends BaseActivity implements Target_Address_View{

    private ImageView iv_back;
    private TextView tv_title;
    private EditText et_target_add;
    private Target_Address_Present present;
    private TextView tv_extra;

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("目的地订单设置");
        tv_extra = (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setText("保存");
        tv_extra.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        present = new Target_Address_Present(this);
        et_target_add = (EditText) findViewById(R.id.et_target_add);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_target_address;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_extra_text:
                present.addtarget_Address();
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
    public String getTargetAdd() {
        return et_target_add.getText().toString();
    }

    @Override
    public void close() {
        finish();
    }
}
