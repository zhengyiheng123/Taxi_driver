package cinyida.com.car_driver.ui.present;

import java.util.List;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.NewsBean;
import cinyida.com.car_driver.ui.model.NewsBiz;
import cinyida.com.car_driver.ui.view.NewsView;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/2.
 */

public class NewsPresent extends BasePresent<NewsBean> {
    private NewsView newsView;

    public NewsPresent(NewsView view){
        this.newsView=view;
    }
    public void initDataNew(){
        Observable<HttpResult<NewsBean>> result=ServiceApi.getInstance().getServiceContract().getNews();
        result.map(new ResultFilter<NewsBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),"暂无记录，请稍后再试",0);
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        t=newsBean;
                        newsView.initData();
                    }
                });

    }
}
