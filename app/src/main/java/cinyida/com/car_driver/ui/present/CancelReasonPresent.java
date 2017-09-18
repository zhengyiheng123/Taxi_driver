package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.exception.ApiException;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.view.CancelReasonView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/13.
 */

public class CancelReasonPresent extends BasePresent {
    private CancelReasonView view;
    public CancelReasonPresent(CancelReasonView view){
        this.view=view;
    }
    public void cancelOrder(String ordernum,int u_reason){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().cancelOrder(ordernum,u_reason);
        result.map(new Func1<HttpResult, HttpResult>() {
            @Override
            public HttpResult call(HttpResult httpResult) {
                if (httpResult.getCode() == 1) {
                    return httpResult;
                }else {
                    throw new ApiException(httpResult.getMessage());
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.cancelSuccess(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        view.cancelSuccess(httpResult.getMessage());
                    }
                });
    }
}
