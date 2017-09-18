package cinyida.com.car_driver.ui.activity;

import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.present.CancelReasonPresent;
import cinyida.com.car_driver.ui.view.CancelReasonView;
import cinyida.com.car_driver.utils.ToastUtils;

/**
 * Created by Zheng on 2017/6/13.
 */

public class Activity_Cancel extends BaseActivity implements CancelReasonView{

    private EditText et_reason;
    private Button btn_login;
    private CancelReasonPresent present;
    private String ordernum;
    private RadioGroup rb_cancel_reason;
    private int reason=0;

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
        rb_cancel_reason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_reason1:
                        reason=1;
                        break;
                    case R.id.rb_reason2:
                        reason=2;
                        break;
                    case R.id.rb_reason3:
                        reason=3;
                        break;
                }
            }
        });
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("取消订单");

    }

    @Override
    protected void initView() {
        ordernum = getIntent().getStringExtra("ordernum");
        present=new CancelReasonPresent(this);
        et_reason = (EditText) findViewById(R.id.et_reason);
        btn_login = (Button) findViewById(R.id.btn_login);
        rb_cancel_reason = (RadioGroup) findViewById(R.id.rb_cancel_reason);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_cancel_reason;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                if (reason == 0){
                    ToastUtils.show(getApplicationContext(),"请选择取消理由",0);
                    return;
                }
                btn_login.setEnabled(false);
                present.cancelOrder(ordernum,reason);
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
    public void cancelSuccess(String message) {
        ToastUtils.show(getApplicationContext(),message,0);
        finish();
        ToCatchCustom_Activity.instance.finish();
        btn_login.setEnabled(true);
    }

    @Override
    public void cancelFailed(String message) {
        btn_login.setEnabled(true);
        ToastUtils.show(getApplicationContext(),message,0);
    }
}
