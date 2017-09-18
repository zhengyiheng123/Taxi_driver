package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.RegCodeBean;
import cinyida.com.car_driver.net.result.ValidateBean;
import cinyida.com.car_driver.ui.model.GetRegcode_Biz;
import cinyida.com.car_driver.ui.view.GetRegcodeView;
import cinyida.com.car_driver.utils.ToastUtils;
import retrofit2.http.Field;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/1.
 */

public class GetRegcodePresent extends BasePresent{
    private GetRegcode_Biz getRegcodeBiz;
    private GetRegcodeView getRegcodeView;
    private ValidateBean validatebean;

    public GetRegcodePresent(GetRegcodeView getRegcodeView) {
        this.getRegcodeView = getRegcodeView;
        getRegcodeBiz=new GetRegcode_Biz();
    }
    public void getRegcode(String phone){
        Observable<HttpResult<RegCodeBean>> observable=ServiceApi.getInstance().getServiceContract().getRegcodeRegister(phone);
        observable.map(new ResultFilter<RegCodeBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RegCodeBean regCodeBean) {
                        t=regCodeBean;
                        getRegcodeView.initData();
                    }
                });
    }
    public void register(String phonenum,String regcode,String password) {
        getRegcodeView.showDialog();
//        ToastUtils.show(MyApplication.getCtx(),getRegcodeView.getPhoneNum()+"asd",0);
        Observable<HttpResult<ValidateBean>> observable=getRegcodeBiz.register(phonenum,regcode,password);
        observable.map(new ResultFilter<ValidateBean>())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ValidateBean>() {
            @Override
            public void onCompleted() {
                getRegcodeView.dismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                getRegcodeView.dismissDialog();
                errorMessage=e.toString();
                ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
            }

            @Override
            public void onNext(ValidateBean validateBean) {
                validatebean = validateBean;
                getRegcodeView.nextActivity();
            }
        });
    }
}
