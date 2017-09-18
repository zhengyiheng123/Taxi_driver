package cinyida.com.car_driver.ui.present;


import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.LoginResult;
import cinyida.com.car_driver.ui.model.LoginBiz;
import cinyida.com.car_driver.ui.view.LoginView;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/4/25.
 */

public class LoginPresent extends BasePresent{
    private LoginBiz loginBiz;
    private LoginView loginView;
    private MyApplication myApplication;

    public LoginPresent(LoginView loginView){
        this.loginBiz=new LoginBiz();
        this.loginView=loginView;
    }
    public void  login(){
        loginView.showDialog();
        Observable<HttpResult<LoginResult>> observable=loginBiz.login(loginView.getPhonenum(),loginView.getPassword());
        observable.map(new ResultFilter<LoginResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {
                        loginView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(MyApplication.getCtx(),e.getMessage(),0);
                        loginView.dismissDialog();
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        SharedPreferenceUtils.setParam(MyApplication.getCtx(),"loginstate",1);
                        SharedPreferenceUtils.setParam(MyApplication.getCtx(),"phonenum",loginResult.getPhonenum());
                        myApplication = new MyApplication();
                        myApplication.setUserInfo(loginResult);
                        loginView.nextActivity();
                    }
                });
    }
}
