package cinyida.com.car_driver.ui.activity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucenlee.countdownlibrary.CountdownButton;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.model.GetRegcode_Biz;
import cinyida.com.car_driver.ui.present.GetRegcodePresent;
import cinyida.com.car_driver.ui.view.GetRegcodeView;
import cinyida.com.car_driver.utils.CustomWaitDialogUtil;
import cinyida.com.car_driver.utils.ToastUtils;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by Zheng on 2017/5/31.
 */

public class Activity_getRegCode extends BaseActivity implements GetRegcodeView{

    private ImageView iv_back;
    private TextView tv_title,tv_extra;
    private CountdownButton countdownButton;
    private EditText et_reg_code,et_mobile,et_password;
    private PromptDialog dialog;
    private GetRegcodePresent getRegcodePresent;

    @Override
    protected void bindEvent() {
        countdownButton.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("获取验证码");
        tv_extra= (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setText("注册");
        tv_extra.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        getRegcodePresent=new GetRegcodePresent(this);
        dialog = new PromptDialog(Activity_getRegCode.this);
        countdownButton = (CountdownButton) findViewById(R.id.activity_main_btn_countdown);
        countdownButton.setCount(60);
        et_reg_code = (EditText) findViewById(R.id.et_reg_code);
        et_mobile= (EditText) findViewById(R.id.et_mobile);
        et_password= (EditText) findViewById(R.id.et_password);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_getregcode;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.activity_main_btn_countdown:
                countdownButton.startDown();
                getRegcodePresent.getRegcode(et_mobile.getText().toString());
                break;
            case R.id.tv_extra_text:
                    getRegcodePresent.register(et_mobile.getText().toString(),et_reg_code.getText().toString(),et_password.getText().toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog=null;
    }

    @Override
    public void showDialog() {
        dialog.showLoading("",false);
    }

    @Override
    public void dismissDialog() {
        dialog.dismissImmediately();
    }


    @Override
    public void nextActivity() {
        finish();
        startActivity(LoginActivity.class);
    }

    @Override
    public void initData() {

    }
}
