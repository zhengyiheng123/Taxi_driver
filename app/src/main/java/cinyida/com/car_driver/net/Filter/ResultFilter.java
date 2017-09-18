package cinyida.com.car_driver.net.Filter;

import android.content.Intent;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.exception.ApiException;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.activity.LoginActivity;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.functions.Func1;

/**
 * Created by Zheng on 2017/4/25.
 */

public class ResultFilter<T> implements Func1<HttpResult<T>,T> {


    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.getCode()==1){
            if (tHttpResult.getData()!=null){
                return tHttpResult.getData();
            }else {
                throw new ApiException(tHttpResult.getMessage());
            }
        }else if(tHttpResult.getCode() == 2){
            Intent intent=new Intent(MyApplication.getCtx(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getCtx().startActivity(intent);
            SharedPreferenceUtils.setParam(MyApplication.getCtx(),"loginstate",0);
            throw new ApiException(tHttpResult.getMessage());
        }
        else {
            throw new ApiException(tHttpResult.getMessage());
        }
    }
}
