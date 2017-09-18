package cinyida.com.car_driver.ui.activity;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HelperDetails;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/19.
 */

public class Activity_Problem_Details extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_problem;

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("问题详情");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initView() {
        tv_problem = (TextView) findViewById(R.id.tv_problem);
        String c_id=getIntent().getStringExtra("c_id");
        Observable<HelperDetails> result= ServiceApi.getInstance().getServiceContract().problemDetails(c_id);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HelperDetails>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HelperDetails helperDetails) {
                        tv_problem.setText(helperDetails.getData());
                    }
                });

    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_problem_details;
    }

    @Override
    protected void BaseOnclick(View view) {

    }
}
