package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/2.
 */

public class SettingActivity extends BaseActivity {

    private RelativeLayout rl_certification_set,rl_certification_suggestion;
    private LinearLayout ll_bank,ll_reset_password,ll_change_mobile,ll_target,ll_about_us,ll_reset_call_us;
    private TextView tv_certified;
    private Button btn_login;


    @Override
    protected void bindEvent() {
        rl_certification_set.setOnClickListener(this);
        ll_bank.setOnClickListener(this);
        ll_reset_password.setOnClickListener(this);
        ll_change_mobile.setOnClickListener(this);
        rl_certification_suggestion.setOnClickListener(this);
        ll_target.setOnClickListener(this);
        ll_about_us.setOnClickListener(this);
        ll_reset_call_us.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("设置");
    }

    @Override
    protected void initView() {
        rl_certification_set = (RelativeLayout) findViewById(R.id.rl_certification_set);
        ll_bank = (LinearLayout) findViewById(R.id.ll_bank);
        ll_reset_password= (LinearLayout) findViewById(R.id.ll_reset_password);
        ll_change_mobile= (LinearLayout) findViewById(R.id.ll_change_mobile);
        rl_certification_suggestion= (RelativeLayout) findViewById(R.id.rl_certification_suggestion);
        ll_target= (LinearLayout) findViewById(R.id.ll_target);
        ll_about_us= (LinearLayout) findViewById(R.id.ll_about_us);
        ll_reset_call_us= (LinearLayout) findViewById(R.id.ll_reset_call_us);
        tv_certified = (TextView) findViewById(R.id.tv_certified);
        int state= (int) SharedPreferenceUtils.getParam(getApplicationContext(),"isCertified",0);
        if (state == 0){
            tv_certified.setText("未认证");
        }else if (state == 1){
            tv_certified.setText("审核中");
        }else if (state ==2){
            tv_certified.setText("已认证");
        }else if (state == 3){
            tv_certified.setText("禁用");
        }
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_certification_set:
                startActivity(Certification_Setting_Activity.class);
                break;
            case R.id.ll_bank:
                startActivity(BankListActivity.class);
                break;
            case R.id.ll_reset_password:
                startActivity(Reset_Password_Activity.class);
                break;
            case R.id.ll_change_mobile:
                startActivity(ChangeMobile_Activity.class);
                break;
            case R.id.rl_certification_suggestion:
                startActivity(SuggestionActivity.class);
                break;
            case R.id.ll_target:
                startActivity(Target_Address_Activity.class);
                break;
            case R.id.ll_about_us:
                startActivity(Activity_About_Us.class);
                break;
            case R.id.ll_reset_call_us:
                startActivity(ConnectUs_Activity.class);
                break;
            case R.id.btn_login:
                Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().loginOut();
                result.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<HttpResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(HttpResult httpResult) {
                                SharedPreferenceUtils.setParam(getApplicationContext(),"loginstate",0);
                                startActivity(LoginActivity.class);
                            }
                        });
                break;
        }
    }

}
