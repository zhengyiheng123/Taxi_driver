package cinyida.com.car_driver.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import cinyida.com.car_driver.net.result.LoginResult;
import cinyida.com.car_driver.utils.PicassoImageLoader;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Zheng on 2017/6/1.
 */
public class MyApplication extends Application{
    private static Context ctx;
    private LoginResult loginResult=null;
    public void setUserInfo(LoginResult loginResult){
        this.loginResult=loginResult;
    }
    public LoginResult getUserInfo(){
        return loginResult;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        if (ctx ==null) {
            ctx = getApplicationContext();
        }
    }


    public static Context getCtx() {
        return ctx;
    }
    
}
