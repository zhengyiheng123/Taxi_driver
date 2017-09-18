package cinyida.com.car_driver.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.present.LoginPresent;
import cinyida.com.car_driver.ui.view.LoginView;
import cinyida.com.car_driver.utils.HttpDialogUtils;
import cinyida.com.car_driver.utils.Sha1Util;
import cinyida.com.car_driver.utils.ToastUtils;
import me.leefeng.promptlibrary.PromptDialog;

public class LoginActivity extends BaseActivity implements LoginView {

    private Button btn_getData;
    private TextView tv_info;
    private LoginPresent loginPresent;
    private ImageView iv_back;
    private TextView tv_title;
    private Button btn_login;
    private TextView tv_forget_password,tv_register;
    private PromptDialog promptDialog;
    private EditText et_password;
    private EditText et_phonenum;

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        promptDialog = new PromptDialog(LoginActivity.this);
        loginPresent=new LoginPresent(this);
        btn_login= (Button) findViewById(R.id.btn_login);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        tv_register= (TextView) findViewById(R.id.tv_register);
        et_password = (EditText) findViewById(R.id.et_password);
        et_phonenum = (EditText) findViewById(R.id.et_phonenum);
    }




    @Override
    protected int getResourcesId() {
        return R.layout.activity_login;
    }

    @Override
    public String getPhonenum() {
        return et_phonenum.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void showDialog() {
        promptDialog.showLoading("登录中",false);
    }

    @Override
    public void dismissDialog() {
        promptDialog.dismissImmediately();
    }

    @Override
    public void nextActivity() {
        finish();
        startActivity(UserCenterActivity.class);
    }



    @Override
    public void initText() {

    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
//            case R.id.btn_getData:
//                loginPresent.getDataFromNet();
//                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
//                startActivity(UserCenterActivity.class);
                login();
                break;
            case R.id.tv_forget_password:
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.tv_register:
                startActivity(Activity_getRegCode.class);
//                String sha1=Sha1Util.sHA1(context);
//                Log.e("zyh",sha1);
                break;
        }
    }
    /**
     * 登录
     */
    private void login(){
        loginPresent.login();
    }

    @Override
    public void onBackPressed() {
        finishAll();
    }
}
