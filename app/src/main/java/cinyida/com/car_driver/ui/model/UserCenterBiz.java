package cinyida.com.car_driver.ui.model;

import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.UserCenterResult;
import rx.Observable;

/**
 * Created by Zheng on 2017/6/2.
 */

public class UserCenterBiz {
    public Observable<HttpResult<UserCenterResult>> getUserCenterData(){
        return ServiceApi.getInstance().getServiceContract().getUserCenter();
    }
}
