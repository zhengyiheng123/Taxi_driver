package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.TousuBean;
import cinyida.com.car_driver.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/2.
 */

public class ServiceActivity extends BaseActivity {


    private EditText et_content,et_ordernum;
    private Button btn_login;
    private RadioGroup rg_problem;
    private int c_id;

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
        rg_problem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_1:
                        c_id = 1;
                        break;
                    case R.id.rb_2:
                        c_id =2;
                        break;
                }
            }
        });
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("客服中心");

    }

    @Override
    protected void initView() {
        et_content = (EditText) findViewById(R.id.et_content);
        et_ordernum= (EditText) findViewById(R.id.et_ordernum);
        btn_login = (Button) findViewById(R.id.btn_login);
        rg_problem = (RadioGroup) findViewById(R.id.rg_problem);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_service;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if (TextUtils.isEmpty(et_ordernum.getText())){
                    ToastUtils.show(context,"请输入订单号",0);
                    return;
                }
                Observable<HttpResult<TousuBean>> result=ServiceApi.getInstance().getServiceContract().tousu(c_id,et_content.getText().toString(),et_ordernum.getText().toString());
                result.map(new ResultFilter<TousuBean>())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<TousuBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                            ToastUtils.show(context,"订单号有误",0);
                            }

                            @Override
                            public void onNext(TousuBean tousuBean) {
                                ToastUtils.show(context,"投诉成功，请等候",0);
                                finish();
                            }
                        });
                break;
        }
    }
}
