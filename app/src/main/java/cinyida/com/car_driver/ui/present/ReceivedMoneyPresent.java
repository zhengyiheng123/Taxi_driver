package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.MoneyResult;
import cinyida.com.car_driver.ui.model.ReceivedMoneyBiz;
import cinyida.com.car_driver.ui.view.ReceivedMoneyView;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/2.
 */

public class ReceivedMoneyPresent extends BasePresent {
    private ReceivedMoneyView view;
    private ReceivedMoneyBiz biz;
    public MoneyResult moneyResult;

    public void setMoneyResult(MoneyResult moneyResult) {
        this.moneyResult = moneyResult;
    }

    public MoneyResult getMoneyResult() {
        return moneyResult;
    }

    public ReceivedMoneyPresent(ReceivedMoneyView view){
        this.view=view;
        biz=new ReceivedMoneyBiz();
    }
    public void getNetData(){
        Observable<HttpResult<MoneyResult>> observable=biz.getBananceData();
        observable.map(new ResultFilter<MoneyResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MoneyResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(MoneyResult moneyResult) {
                        setMoneyResult(moneyResult);
                        view.initData();
                    }
                });
    }
}
