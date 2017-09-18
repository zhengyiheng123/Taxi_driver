package cinyida.com.car_driver.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cinyida.com.car_driver.utils.HttpDialogUtils;
import cinyida.com.car_driver.utils.ToastUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Zheng on 2017/4/25.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public Context context;
    private Intent intent;
   public BaseActivity(){
       context=this;
   }
    private static List<BaseActivity> onlineActivityList = new ArrayList<>();
    //安全退出，销毁所有activity
    public  void finishAll() {
        for (int i=0;i<onlineActivityList.size();i++){
            BaseActivity activity=onlineActivityList.get(i);
            activity.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (onlineActivityList.contains(this)){
            onlineActivityList.remove(this);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourcesId());
        initToolBar();
        initView();
        bindEvent();
        onlineActivityList.add(this);
    }

    protected abstract void bindEvent();

    protected abstract void initToolBar();

    protected abstract void initView();

    protected abstract int getResourcesId();

    /**
     * 不带参数
     * @param targetClass
     */
    protected void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }
    /**
     * 带参数
     * @param targetClass
     */
    protected void startActivityBundle(Class<?> targetClass,Bundle budle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(budle);
        startActivity(intent);
    }
    protected void startActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private static long exitTime = 0;

    public  void exitApp(Context context) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort(context, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            JPushInterface.stopPush(getApplicationContext());
            finishAll();
        }
    }
    public void showDialog(boolean isOutTouch,String message){
        HttpDialogUtils.showDialog(context,isOutTouch,message);
    }
    public void disMissDialog(){
        HttpDialogUtils.dismissDialog();
    }

    @Override
    public void onClick(View view) {
        BaseOnclick(view);
    }

    protected abstract void BaseOnclick(View view);
}
