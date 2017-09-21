package cinyida.com.car_driver.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/16.
 */

public class ConnectUs_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_title,tel_phone;
    private EditText et_suggestion;
    private Button button;

    @Override
    protected void bindEvent() {
        button.setOnClickListener(this);
        tel_phone.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("联系我们");
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        et_suggestion= (EditText) findViewById(R.id.textView9);
        button= (Button) findViewById(R.id.button);
        tel_phone= (TextView) findViewById(R.id.tel_phone);
    }
    private void connectUs(final String phone){
        if (Build.VERSION.SDK_INT>=23){
            PermissionManager.with(ConnectUs_Activity.this).request(new PermissionManager.Callback() {
                @Override
                public void call(PermissionResult result) {
                    if (!TextUtils.isEmpty(phone)) {
                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                        startActivity(intent);
                    }
                }
            }, Manifest.permission.CALL_PHONE,Manifest.permission.READ_CALL_LOG);
        }else {
            if (!TextUtils.isEmpty(phone)) {
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_connect_us;
    }



    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.button:
                if (et_suggestion.getText().toString().equals("")){
                    ToastUtils.showShort(getApplicationContext(),"请输入内容");
                    return;
                }
                feedBack(et_suggestion.getText().toString());
                break;
            case R.id.textView7:
                connectUs(tel_phone.getText().toString());
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
