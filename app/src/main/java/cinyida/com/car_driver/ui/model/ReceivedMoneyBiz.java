package cinyida.com.car_driver.ui.model;

import cinyida.com.car_driver.net.HttpService;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.MoneyResult;
import rx.Observable;

/**
 * Created by Zheng on 2017/6/2.
 */

public class ReceivedMoneyBiz {
    public Observable<HttpResult<MoneyResult>> getBananceData(){
        return ServiceApi.getInstance().getServiceContract().getBananceData();
    }
}
