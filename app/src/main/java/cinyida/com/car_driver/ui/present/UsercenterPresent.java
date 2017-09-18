package cinyida.com.car_driver.ui.present;

import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.CatchOrderBean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.UserCenterResult;
import cinyida.com.car_driver.ui.model.UserCenterBiz;
import cinyida.com.car_driver.ui.view.UsercenterView;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/2.
 */

public class UsercenterPresent extends BasePresent<UserCenterResult> {
    private UsercenterView view;
    private UserCenterBiz biz;
    public static UsercenterPresent instance;
    public UsercenterPresent(UsercenterView view){
        instance=this;
        this.view=view;
        biz=new UserCenterBiz();
    }

    /**
     * 获取网络数据
     */
    public void initGetData(){
        Observable<HttpResult<UserCenterResult>> result=biz.getUserCenterData();
        view.showDialog();
        result.map(new ResultFilter<UserCenterResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserCenterResult>() {
                    @Override
                    public void onCompleted() {
                    view.dismissDialog();
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                    }

                    @Override
                    public void onNext(UserCenterResult userCenterResult) {
                        t=userCenterResult;
                        view.initData();
//                        int state=t.getDriver().getState();//0未认证，1审核中，2已通过
//                        if (state ==0){
//                            view.showWarnDialog();
//                        }
                    }
                });
    }

    /**
     * 发送位置信息
     * @param longitude
     * @param latitude
     */
    public void sendPosition(String longitude,String latitude){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().sendPosition(longitude,latitude);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult httpResult) {

                    }
                });
    }

    //设置听单状态
    public void listenOrder(final String type){
//        type 0空车  1载客  2顺风回家  3听单
        Observable<HttpResult> result=ServiceApi.getInstance().getServiceContract().listenOrder(type);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.resetState();
                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        if (type .equals("0")){
                            view.processListen();
                        }else if (type.equals("3")){
                            view.processStop();
                        }
                    }
                });
    }

    /**
     * 抢单
     */
    public void catchOrder(String ordernum,String userId){
        Observable<HttpResult<CatchOrderBean>> result=ServiceApi.getInstance().getServiceContract().catchOrder(ordernum,userId);
        result.map(new ResultFilter<CatchOrderBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CatchOrderBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.catchFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(CatchOrderBean catchOrderBean) {
                        ToastUtils.show(MyApplication.getCtx().getApplicationContext(),"抢单成功",0);
                        view.getOrderSuccess(catchOrderBean);
                    }
                });
    }

    /**
     * 查询订单信息
     */
    public void queryOrderInfo(String ordernum){
        view.showDialog();
        Observable<HttpResult<CatchOrderBean>> result=ServiceApi.getInstance().getServiceContract().getOrderInfo(ordernum);
        result.map(new ResultFilter<CatchOrderBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CatchOrderBean>() {
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }

                    @Override
                    public void onNext(CatchOrderBean catchOrderBean) {
                        view.getOrderSuccess(catchOrderBean);
                    }
                });
    }
}
