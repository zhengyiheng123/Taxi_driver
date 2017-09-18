package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.BaseApi;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.WeixinCodeBean;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/22.
 */

public class Receive_Money_Activity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_extra;
    private Button btn_login;
    private String ordernum;
    private ImageView iv_myweixin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordernum = getIntent().getStringExtra("ordernum");
    }

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("收款结账");
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_receive_money;
    }


    @Override
    protected void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        iv_myweixin = (ImageView) findViewById(R.id.iv_myweixin);
        Observable<HttpResult<WeixinCodeBean>> result= ServiceApi.getInstance().getServiceContract().loadWeixin();
        result.map(new ResultFilter<WeixinCodeBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeixinCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeixinCodeBean weixinCodeBean) {
                        Glide.with(Receive_Money_Activity.this).load(BaseApi.getBaseUrl()+"/"+weixinCodeBean.getImage()).into(iv_myweixin);
                    }
                });
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                finishOrder();
                break;
        }
    }
private void finishOrder(){
    Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().finishOrder(ordernum);
    result.map(new ResultFilter())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.show(getApplicationContext(),e.getMessage(),0);
                }

                @Override
                public void onNext(Object o) {
                    ToastUtils.show(getApplicationContext(),"订单结束",0);
                    finish();
                    ToCatchCustom_Activity.instance.finish();
                }
            });
}
}
