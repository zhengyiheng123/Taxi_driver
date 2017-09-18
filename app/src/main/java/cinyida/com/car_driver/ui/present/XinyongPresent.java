package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.XinYongBean;
import cinyida.com.car_driver.ui.view.XinyongView;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/20.
 */

public class XinyongPresent extends BasePresent {
    private XinyongView view;
    public XinyongPresent(XinyongView view){
        this.view=view;
    }
    public void initData(){
        Observable<HttpResult<XinYongBean>> result= ServiceApi.getInstance().getServiceContract().xinYongList();
        result.map(new ResultFilter<XinYongBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<XinYongBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),"没有数据",0);
                    }

                    @Override
                    public void onNext(XinYongBean xinYongBean) {
                        view.success(xinYongBean);
                    }
                });
    }
}
