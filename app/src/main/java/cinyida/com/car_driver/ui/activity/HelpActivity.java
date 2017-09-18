package cinyida.com.car_driver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HelpBean;
import cinyida.com.car_driver.net.result.HelperDetails;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.ui.holder.HelpHolder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/4/27.
 */

public class HelpActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv_help;
    private HelpBean bean;

    @Override
    protected void bindEvent() {
        lv_help.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(HelpActivity.this, Activity_Problem_Details.class);
                Bundle bundle=new Bundle();
                intent.putExtra("c_id",bean.getQuestion().get(i).getId()+"");
                HelpActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("问题列表");
    }

    @Override
    protected void initView() {
        lv_help = (ListView) findViewById(R.id.lv_help);
        Observable<HttpResult<HelpBean>> result= ServiceApi.getInstance().getServiceContract().getHelpInfo();
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<HelpBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<HelpBean> helpBeanHttpResult) {
                        bean = helpBeanHttpResult.getData();
                        lv_help.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                            @Override
                            public Object onCreateViewHolder() {
                                return new HelpHolder();
                            }
                        },helpBeanHttpResult.getData().getQuestion()));
                    }
                });
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_help;
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
