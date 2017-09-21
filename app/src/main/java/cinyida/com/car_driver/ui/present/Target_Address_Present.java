package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HomeAddress;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.view.TargetAddBean;
import cinyida.com.car_driver.ui.view.Target_Address_View;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/8.
 */

public class Target_Address_Present extends BasePresent<TargetAddBean> {
    private Target_Address_View view;
    public Target_Address_Present(Target_Address_View view){
        this.view=view;
    }
    public void addtarget_Address(String atrgetadd,String longitude,String latitude){
        Observable<HttpResult<TargetAddBean>> result= ServiceApi.getInstance().getServiceContract().addTarget(atrgetadd,longitude,latitude);
        result.map(new ResultFilter<TargetAddBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TargetAddBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(TargetAddBean targetAddBean) {
                        ToastUtils.show(MyApplication.getCtx(),"添加成功",0);
                        view.close();
                    }
                });
    }

    public void getAddress(){
        Observable<HttpResult<HomeAddress>> result=ServiceApi.getInstance().getServiceContract().getHomeAddress();
        result.map(new ResultFilter<HomeAddress>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeAddress>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomeAddress homeAddress) {
                        view.initAddress(homeAddress);
                    }
                });
    }
}
