package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.net.result.NewsBean;
import cinyida.com.car_driver.ui.holder.News_Holder;
import cinyida.com.car_driver.ui.present.NewsPresent;
import cinyida.com.car_driver.ui.view.NewsView;

/**
 * Created by Zheng on 2017/5/2.
 */

public class NewsActivity extends BaseActivity  implements NewsView{
    private NewsPresent newsPresent;
    private ListView lv_news;


    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("消息记录");
    }

    @Override
    protected void initView() {
        newsPresent = new NewsPresent(this);
        newsPresent.initDataNew();
        lv_news = (ListView) findViewById(R.id.lv_news);
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_news;
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
    public void initData() {
        if (newsPresent.getData()!=null){
            BaseArrayAdapter adapter=new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
                @Override
                public Object onCreateViewHolder() {
                    return new News_Holder();
                }
            },newsPresent.getData().getMessage());
            lv_news.setAdapter(adapter);
        }
    }
}
