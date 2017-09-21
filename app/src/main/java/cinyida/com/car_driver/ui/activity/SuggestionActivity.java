package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/5.
 */

public class SuggestionActivity extends BaseActivity{

    private TextView et_suggestion;
    private Button btn_login;

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("设置");
    }

    @Override
    protected void initView() {
        et_suggestion = (TextView) findViewById(R.id.et_suggestion);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_suggestion;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                if (et_suggestion.getText().toString().equals("")){
                    ToastUtils.showShort(getApplicationContext(),"请输入内容");
                    return;
                }
                feedBack(et_suggestion.getText().toString());
                break;
        }
    }

    private void feedBack(final String content){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().feedback(content);
        result.map(new ResultFilter())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(getApplicationContext(),e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtils.show(getApplicationContext(),"感谢您的反馈，我们会尽快处理！",0);
                        finish();
                    }
                });
    }
}
