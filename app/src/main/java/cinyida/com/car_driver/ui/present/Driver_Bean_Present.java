package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.exception.ApiException;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.ChangeHeanBean;
import cinyida.com.car_driver.net.result.Driver_Bean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.model.Driver_Model_Biz;
import cinyida.com.car_driver.ui.view.Driver_View;
import cinyida.com.car_driver.utils.ToastUtils;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/5.
 */

public class Driver_Bean_Present extends BasePresent<Driver_Bean> {
    private Driver_Model_Biz biz;
    private Driver_View view;

    public Driver_Bean_Present(Driver_View view) {
        this.view = view;
    }

    public void getNetData(){
        Observable<HttpResult<Driver_Bean>> resultObservable= ServiceApi.getInstance().getServiceContract().getDriverBean();
        resultObservable.map(new Func1<HttpResult<Driver_Bean>, Driver_Bean>() {
            @Override
            public Driver_Bean call(HttpResult<Driver_Bean> driver_beanHttpResult) {
                if (driver_beanHttpResult.getCode() == 0){
                    throw new ApiException(driver_beanHttpResult.getMessage());
                }else {
                    return driver_beanHttpResult.getData();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Driver_Bean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(Driver_Bean driver_bean) {
                        t=driver_bean;
                        view.initData();
                    }
                });
    }

    public void changeHead(){
        view.showDialog();
        Observable<HttpResult<ChangeHeanBean>> result=ServiceApi.getInstance().getServiceContract().change(view.getRequestBody());
        result.map(new ResultFilter<ChangeHeanBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangeHeanBean>() {
                    @Override
                    public void onCompleted() {
view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(ChangeHeanBean changeHeanBean) {
                    view.changeHead(changeHeanBean);
                    }
                });
    }
}

