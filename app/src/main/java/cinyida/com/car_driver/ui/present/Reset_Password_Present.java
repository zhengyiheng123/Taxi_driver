package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.RegCodeBean;
import cinyida.com.car_driver.ui.view.Reset_Password_View;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/7.
 */

public class Reset_Password_Present extends BasePresent<RegCodeBean> {
    private Reset_Password_View view;
    public Reset_Password_Present(Reset_Password_View view){
        this.view=view;
    }

    public void resetPassword(){
        Observable<HttpResult<RegCodeBean>> result= ServiceApi.getInstance().getServiceContract().resetPass(view.getOldPwd(),view.getNewPwd());
        result.map(new ResultFilter<RegCodeBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(RegCodeBean regCodeBean) {
                        ToastUtils.show(MyApplication.getCtx(),regCodeBean.getMessage(),0);
                        view.initData();
                    }
                });
    }
}
