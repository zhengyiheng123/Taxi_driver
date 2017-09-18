package cinyida.com.car_driver.ui.model;

import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.ValidateBean;
import rx.Observable;

/**
 * Created by Zheng on 2017/6/1.
 */

public class GetRegcode_Biz {
    /**
     * 获取验证码
     */
    public void getRegcode() {
    }
    /**
     * 验证验证码
     */
    public Observable<HttpResult<ValidateBean>> register(String phonenum,String code,String password){
        return ServiceApi.getInstance().getServiceContract().validateCode(phonenum,code,password);
    }

}
