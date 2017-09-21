package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.view.CatchSettingView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/12.
 */

public class CatchSettingPresent extends BasePresent {
    private CatchSettingView view;
    public CatchSettingPresent(CatchSettingView view){
        this.view=view;
    }

    /**
     * 偏好设置
     * @param preference
     * @param id
     */
    public void setPreference(final int preference, final int id){
        view.showDialog();
        Observable<HttpResult> observable= ServiceApi.getInstance().getServiceContract().preference(preference);
        observable.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.failed(id);
                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(Object o) {
                        view.success(preference);

                    }
                });

    }

    /**
     * 只听收车单
     */
    public void onlyHome(final int type){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().onlyHome(type);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        view.onlyHomeSuccess(type);
                    }
                });
    }

    /**
     * 只听预约单
     */
    public void onlyOrder(final int type,String longitude,String latitude){
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().onlyOrder(type,longitude,latitude);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        view.onlyOrderSuccess(type);
                    }
                });
    }

    /**
     * 设置听单范围
     */
    public void setRange(final int distance){
        view.showDialog();
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().distance(distance);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(Object o) {
                        view.distanceSuccess(distance);
                    }
                });
    }
}
