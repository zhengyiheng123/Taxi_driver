package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.lib.LocationTask;
import cinyida.com.car_driver.lib.OnLocationGetListener;
import cinyida.com.car_driver.lib.PositionEntity;
import cinyida.com.car_driver.ui.present.CatchSettingPresent;
import cinyida.com.car_driver.ui.view.CatchSettingView;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.widget.CheckSwitchButton;
import cinyida.com.car_driver.widget.StateTextview;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by Zheng on 2017/5/11.
 */

public class Activity_Catch_Setting extends BaseActivity  implements CatchSettingView,RadioGroup.OnCheckedChangeListener,OnLocationGetListener{

    private TextView tv_setting;
    private CatchSettingPresent present;
    private RadioGroup rb_preference,rg_distance;
    private CheckSwitchButton check_home,tv_check_order;
    private PromptDialog dialog;
    private RadioButton cb_automode;
    //距离临时值
    private int rangeTemp=0;
    //定位初始化
    private LocationTask mLocationTask;
    private LatLng mPosition;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationTask!=null){
            mLocationTask.onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationTask!=null){
            mLocationTask.stopLocate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationTask == null){
            mLocationTask = LocationTask.getInstance(getApplicationContext());
            mLocationTask.setOnLocationGetListener(this);
            mLocationTask.startLocate();
        }else {
            mLocationTask.startLocate();
        }
    }

    //开始定位
    private void startLocation() {
        if (mLocationTask == null){
            mLocationTask = LocationTask.getInstance(getApplicationContext());
            mLocationTask.setOnLocationGetListener(this);
            mLocationTask.startLocate();
        }else {
            mLocationTask.startLocate();
        }
    }

    @Override
    protected void bindEvent() {
        tv_setting.setOnClickListener(this);
        rb_preference.setOnCheckedChangeListener(this);
        //自动模式
        cb_automode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    present.setRange(0);
                }else {
                    present.setRange(rangeTemp);
                }
            }
        });
        //只听收车单
        check_home.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    present.onlyHome(2,mPosition.longitude+"",mPosition.latitude+"");
                }else {
                    present.onlyHome(1,mPosition.longitude+"",mPosition.latitude+"");
                }
            }
        });
        //只听预约车单
        tv_check_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    present.onlyOrder(2,mPosition.longitude+"",mPosition.latitude+"");
                }else {
                    present.onlyOrder(1,mPosition.longitude+"",mPosition.latitude+"");
                }
            }
        });
        //设置听单范围
        rg_distance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_1:
                        present.setRange(1);
                        break;
                    case R.id.rb_2:
                        present.setRange(2);
                        break;
                    case R.id.rb_3:
                        present.setRange(3);
                        break;
                    case R.id.rb_5:
                        present.setRange(5);
                        break;
                }
            }
        });
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        startLocation();
        dialog = new PromptDialog(Activity_Catch_Setting.this);
        present = new CatchSettingPresent(this);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        rb_preference = (RadioGroup) findViewById(R.id.rb_preference);
        rg_distance= (RadioGroup) findViewById(R.id.rg_distance);
        cb_automode= (RadioButton) findViewById(R.id.cb_automode);
        //订单偏好
        int preference= (int) SharedPreferenceUtils.getParam(getApplicationContext(),"rb_preference",0);
        //只听收车单
        int onlyHome= (int) SharedPreferenceUtils.getParam(getApplicationContext(),"onlyHome",1);
        //只听预约单
        int onlyOrder= (int) SharedPreferenceUtils.getParam(getApplicationContext(),"onlyOrder",1);

        //听单范围
        int distance= (int) SharedPreferenceUtils.getParam(context,"distance",0);
        switch (distance){
            case 0:
                rg_distance.clearCheck();
                cb_automode.setChecked(true);
                break;
            case 1:
                rg_distance.check(R.id.rb_1);
                break;
            case 2:
                rg_distance.check(R.id.rb_2);
                break;
            case 3:
                rg_distance.check(R.id.rb_3);
                break;
            case 5:
                rg_distance.check(R.id.rb_5);
                break;
        }
        switch (preference){
            case 0:
                rb_preference.check(R.id.rb_intime);
                break;
            case 1:
                rb_preference.check(R.id.rb_order);
                break;
            case 2:
                rb_preference.check(R.id.rb_all);
                break;
        }
        check_home = (CheckSwitchButton) findViewById(R.id.check_home);
        tv_check_order= (CheckSwitchButton) findViewById(R.id.tv_check_order);
        if (onlyHome == 1){
            check_home.setChecked(false);
        }else {
            check_home.setChecked(true);
        }
       if (onlyOrder == 1){
           tv_check_order.setChecked(false);
       }else {
           tv_check_order.setChecked(true);
       }
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_catch_setting;
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_setting:
                finish();
                break;
        }
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
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            //实时
            case R.id.rb_intime:
                present.setPreference(0,R.id.rb_intime);
                break;
            //预约
            case R.id.rb_order:
                present.setPreference(1,R.id.rb_order);
                break;
            //全部
            case R.id.rb_all:
                present.setPreference(2,R.id.rb_all);
                break;
        }
    }


    @Override
    public void success(int preference) {
        switch (preference){
            case 0:
                ToastUtils.show(context,"只听实时车单",0);
                break;
            case 1:
                ToastUtils.show(context,"只听预约车单车单",0);
                break;
            case 2:
                ToastUtils.show(context,"收听全部订单",0);
                break;
        }
        SharedPreferenceUtils.setParam(getApplicationContext(),"rb_preference",preference);
    }

    @Override
    public void failed(int id) {
        rb_preference.check(id);
    }

    @Override
    public void onlyOrderSuccess(int type) {
        ToastUtils.show(context,"只听预约车单",0);
        SharedPreferenceUtils.setParam(getApplicationContext(),"onlyOrder",type);
    }

    @Override
    public void onlyHomeSuccess(int type) {
        ToastUtils.show(context,"只听收车单",0);
        SharedPreferenceUtils.setParam(getApplicationContext(),"onlyHome",type);
    }

    @Override
    public void distanceSuccess(int distance) {
        if (distance == 0){
            ToastUtils.show(context,"自动模式",0);
        }else {
            rangeTemp=distance;
            cb_automode.setChecked(false);
            ToastUtils.show(context,"听单范围"+distance+"公里",0);
        }
        SharedPreferenceUtils.setParam(context,"distance",distance);
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        mPosition = new LatLng(entity.latitue,entity.longitude);

    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {

    }
}
