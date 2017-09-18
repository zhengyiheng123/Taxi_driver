package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.Car_Records_Bean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.view.Car_Records_View;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

/**
 * Created by Zheng on 2017/6/5.
 */

public class CCar_Order_Records_Present extends BasePresent<Car_Records_Bean> {
    Car_Records_View view;

    public CCar_Order_Records_Present(Car_Records_View view){
        this.view=view;
    }
    public void getNetData(){
        Observable<HttpResult<Car_Records_Bean>> resultObservable= ServiceApi.getInstance().getServiceContract().getCarRecords();
        resultObservable.map(new ResultFilter<Car_Records_Bean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Car_Records_Bean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(Car_Records_Bean car_records_bean) {
                        t=car_records_bean;
                    view.initData();
                    }
                });

    }
}
