package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.UploadBean;
import cinyida.com.car_driver.ui.view.CertifyView;
import cinyida.com.car_driver.utils.ToastUtils;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/5.
 */

public class CertifyPresent {
    private CertifyView view;
    public CertifyPresent(CertifyView view){
        this.view=view;
    }
    public void uploadInfos(){
        view.showDialog();
        Observable<HttpResult<UploadBean>> result= ServiceApi.getInstance().getServiceContract().uploadInfo(view.getImages());
        result.map(new ResultFilter<UploadBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UploadBean>() {
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(UploadBean uploadBean) {
                        view.initData();
                        ToastUtils.show(MyApplication.getCtx(),uploadBean.getMessage(),0);
                    }
                });

    }
}
