package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucenlee.countdownlibrary.CountdownButton;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.result.ForgetPassBean;
import cinyida.com.car_driver.ui.present.Forget_PasswordPresent;
import cinyida.com.car_driver.ui.view.ForgetPassword_View;
import cinyida.com.car_driver.utils.ToastUtils;

/**
 * Created by Zheng on 2017/4/26.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener,ForgetPassword_View{

    private CountdownButton tv_code;
    private Forget_PasswordPresent present;
    private EditText et_mobile,et_password,et_reg_code;
    private Button btn_login;

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
        tv_code.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("忘记密码");
    }

    @Override
    protected void initView() {
        present=new Forget_PasswordPresent(this);
        tv_code = (CountdownButton) findViewById(R.id.activity_main_btn_countdown);
        tv_code.setCount(60);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password= (EditText) findViewById(R.id.et_password);
        et_reg_code= (EditText) findViewById(R.id.et_reg_code);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_forget_password;
    }



    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.activity_main_btn_countdown:
                tv_code.startDown();
                if (TextUtils.isEmpty(et_mobile.getText().toString())){
                    ToastUtils.show(getApplicationContext(),"手机号不能为空",0);
                    return;
                }
                present.getRegcode();
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(et_password.getText().toString()) || TextUtils.isEmpty(et_mobile.getText().toString()) || TextUtils.isEmpty(et_reg_code.getText().toString())){
                    ToastUtils.show(getApplicationContext(),"请填写信息",0);
                    return;
                }
                btn_login.setEnabled(false);
                present.changePassword();
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
    public void initData() {

    }

    @Override
    public String getPhonenum() {
        return et_mobile.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public String getCode() {
        return et_reg_code.getText().toString();
    }

    @Override
    public void finishi(String message) {
        btn_login.setEnabled(true);
        ToastUtils.show(getApplicationContext(),message,0);
        finish();
    }

    @Override
    public void failed(String message) {
        btn_login.setEnabled(true);
        ToastUtils.show(getApplicationContext(),message,0);
    }
}
