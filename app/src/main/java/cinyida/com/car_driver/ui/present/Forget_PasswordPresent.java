package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.ChangeBean;
import cinyida.com.car_driver.net.result.ForgetPassBean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.view.ForgetPassword_View;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/7.
 */

public class Forget_PasswordPresent extends BasePresent<ForgetPassBean> {
    ForgetPassword_View view;
    public Forget_PasswordPresent (ForgetPassword_View view){
        this.view=view;
    }
    public void getRegcode(){
        Observable<HttpResult<ForgetPassBean>> result= ServiceApi.getInstance().getServiceContract().getCodePass(view.getPhonenum());
        result.map(new ResultFilter<ForgetPassBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ForgetPassBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(ForgetPassBean forgetPassBean) {
                        ToastUtils.show(MyApplication.getCtx(),forgetPassBean.getMessage(),0);
                    }
                });
    }
    public void changePassword(){
        Observable<HttpResult<ChangeBean>> result=ServiceApi.getInstance().getServiceContract().changePassword(view.getPhonenum(),view.getCode(),view.getPassword());
        result.map(new ResultFilter<ChangeBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    view.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ChangeBean changeBean) {
                        view.finishi(changeBean.getMessage());
                    }
                });
    }
}
