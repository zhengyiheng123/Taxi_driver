package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.MybankBean;
import cinyida.com.car_driver.ui.view.MybankView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/8.
 */

public class MyBankPresent extends BasePresent<MybankBean> {
    private MybankView view;
    public MyBankPresent(MybankView view){
        this.view=view;
    }
    public void getNetData(){
        Observable<HttpResult<MybankBean>> result= ServiceApi.getInstance().getServiceContract().myBank();
        result.map(new ResultFilter<MybankBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MybankBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showAdd();
                    }

                    @Override
                    public void onNext(MybankBean mybankBean) {
                        t=mybankBean;
                        view.initData();
                    }
                });
    }
}
