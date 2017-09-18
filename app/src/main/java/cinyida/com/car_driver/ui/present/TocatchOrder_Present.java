package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.exception.ApiException;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.view.TocatchActivity_View;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/9.
 */

public class TocatchOrder_Present extends BasePresent {
    private TocatchActivity_View view;
    public TocatchOrder_Present(TocatchActivity_View view){
        this.view=view;
    }
    //设置听单状态
    public void listenOrder(final String type){
//        type 0空车  1载客  2顺风回家  3听单
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().listenOrder(type);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        if (type.equals("3")){
                            view.finishNow();
                        }
                    }
                });
    }
    /**
     * 到达上车地点
     */
    public void arriveCustomAddress(String ordernum){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().toArriveAddress(ordernum);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.arriveCustomFailed();
                    }

                    @Override
                    public void onNext(Object o) {
                        view.arriveCustomSuccess();
                    }
                });
    }

    /**
     * 接到乘客
     */
    public void getCustom(String ordernum){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().getCustom(ordernum);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getCustomFailed();
                    }

                    @Override
                    public void onNext(Object o) {
                        view.getCustomSuccess();
                    }
                });
    }
    /**
     * 到达目的地
     */
    public void toFinal(String location,String ordern){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().toFinal(location,ordern);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.finishFailed();
                    }

                    @Override
                    public void onNext(Object o) {
                        view.finishSuccess();
                    }
                });
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
                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        view.cancelFinish();
                    }
                });
    }
}
