package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.present.Reset_Password_Present;
import cinyida.com.car_driver.ui.view.Reset_Password_View;

/**
 * Created by Zheng on 2017/5/4.
 */

public class Reset_Password_Activity extends BaseActivity implements Reset_Password_View{

    private EditText et_new_pwd;
    private EditText et_old_pwd;
    private Button btn_login;
    private Reset_Password_Present present;

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("修改密码");
    }

    @Override
    protected void initView() {
        present=new Reset_Password_Present(this);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                present.resetPassword();
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
    public String getNewPwd() {
        return et_new_pwd.getText().toString();
    }

    @Override
    public String getOldPwd() {
        return et_old_pwd.getText().toString();
    }

    @Override
    public void initData() {
        finish();
    }
}
