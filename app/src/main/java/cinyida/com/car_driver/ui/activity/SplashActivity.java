package cinyida.com.car_driver.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;

/**
 * Created by Zheng on 2017/6/2.
 */

public class SplashActivity extends BaseActivity {
    private Handler handler=new Handler();
    private int loginState;
    private int is_first;

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {

    }
    /**
     * 申请权限
     */
    private void requestPermission() {
        PermissionManager.with(this).request(new PermissionManager.Callback() {
            @Override
            public void call(PermissionResult result) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (loginState == 1){
                            Intent intent=new Intent(getApplicationContext(),UserCenterActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        finish();
//                Intent intent=new Intent(getApplicationContext(),)
                    }
                },1000);
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    @Override
    protected void initView() {
        loginState = (int) SharedPreferenceUtils.getParam(getApplicationContext(),"loginstate",0);
        is_first = (int) SharedPreferenceUtils.getParam(context,"is_first",0);
        if (is_first == 1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                requestPermission();
            }else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (loginState == 1){
                            Intent intent=new Intent(getApplicationContext(),UserCenterActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        finish();
//                Intent intent=new Intent(getApplicationContext(),)
                    }
                },1000);
            }
        }else {
            Intent intent=new Intent(context,IntroduceActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void BaseOnclick(View view) {

    }
}
