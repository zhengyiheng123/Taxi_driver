package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.CertificationBean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.UploadBean;
import cinyida.com.car_driver.ui.view.CertificationView;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/13.
 */

public class CertificationPresent extends BasePresent {
private CertificationView view;
    public CertificationPresent(CertificationView view){
        this.view=view;
    }

    public void getNetData(){
        Observable<HttpResult<CertificationBean>> result= ServiceApi.getInstance().getServiceContract().getCertification();
        result.map(new ResultFilter<CertificationBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CertificationBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CertificationBean certificationBean) {
                        view.initData(certificationBean);
                    }
                });
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
//                        view.initData();
                        ToastUtils.show(MyApplication.getCtx(),uploadBean.getMessage(),0);
                    }
                });

    }
}
