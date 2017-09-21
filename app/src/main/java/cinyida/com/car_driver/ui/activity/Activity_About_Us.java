package cinyida.com.car_driver.ui.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.AboutUs;
import cinyida.com.car_driver.net.result.HttpResult;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/16.
 */

public class Activity_About_Us extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private TextView title;
    private TextView content,textView5,tv_version;


    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("关于我们");

    }
    /**
     * 获取App版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(Context context, String packageName) {
        if (packageName == null || packageName.trim().length() == 0) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void initView() {
        title= (TextView) findViewById(R.id.title);
        content= (TextView) findViewById(R.id.content);
        textView5= (TextView) findViewById(R.id.textView5);
        tv_version= (TextView) findViewById(R.id.tv_version);
        tv_version.setText(getAppVersionName(context,getPackageName()));
        aboutUs();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_aboutus;
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
    //获取数据
    private void aboutUs(){
        Observable<HttpResult<AboutUs>> result= ServiceApi.getInstance().getServiceContract().aboutUs();
        result.map(new ResultFilter<AboutUs>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AboutUs>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AboutUs aboutUs) {
                        content.setText(aboutUs.getContent());
                        textView5.setText(aboutUs.getPhone());
                    }
                });
    }
}
