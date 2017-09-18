package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.AddBankBean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.view.AddBankView;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/8.
 */

public class AddBankPresent extends BasePresent<AddBankBean> {
    AddBankView view;
    public AddBankPresent(AddBankView view){
        this.view=view;
    }
    public void addBank(){
        Observable<HttpResult<AddBankBean>> result= ServiceApi.getInstance().getServiceContract().addBank(view.getName(),view.getBanknum());
        result.map(new ResultFilter<AddBankBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddBankBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(AddBankBean addBankBean) {
                    view.finished();
                    }
                });
    }
}
