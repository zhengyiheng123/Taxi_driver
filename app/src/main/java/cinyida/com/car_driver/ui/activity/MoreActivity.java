package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.navigation.CalculateRouteActivity;
import cinyida.com.car_driver.navigation.RouteNaviActivity;
import cinyida.com.car_driver.widget.HomeButtonView;

/**
 * Created by Zheng on 2017/4/28.
 */

public class MoreActivity extends BaseActivity implements HomeButtonView.HomeClickListener{

    private HomeButtonView tv_receive_fee,home_setting,home_service
            ,home_news,home_route_state;


    @Override
    protected void bindEvent() {
        tv_receive_fee.setOnHomeClick(this);
        home_setting.setOnHomeClick(this);
        home_service.setOnHomeClick(this);
        home_news.setOnHomeClick(this);
        home_route_state.setOnHomeClick(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("更多");
    }

    @Override
    protected void initView() {
        tv_receive_fee = (HomeButtonView) findViewById(R.id.tv_receive_fee);
        home_setting= (HomeButtonView) findViewById(R.id.home_setting);
        home_service= (HomeButtonView) findViewById(R.id.home_service);
        home_news= (HomeButtonView) findViewById(R.id.home_news);
        home_route_state= (HomeButtonView) findViewById(R.id.home_route_state);
    }



    @Override
    protected int getResourcesId() {
        return R.layout.activity_more;
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onclick(HomeButtonView homebtn) {
        switch (homebtn.getId()){
            case R.id.tv_receive_fee:
                startActivity(Receive_Fee_Activity.class);
                break;
            case R.id.home_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.home_service:
                startActivity(ServiceActivity.class);
                break;
            case R.id.home_news:
                startActivity(NewsActivity.class);
                break;
            case R.id.home_route_state:
                startActivity(TrafficMapActivity.class);
                break;
        }
    }
}
