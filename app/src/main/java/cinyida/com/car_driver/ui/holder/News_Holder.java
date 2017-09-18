package cinyida.com.car_driver.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseViewHolder;
import cinyida.com.car_driver.net.result.NewsBean;

/**
 * Created by Zheng on 2017/5/2.
 */

public class News_Holder extends BaseViewHolder<NewsBean.MessageBean> {

    private TextView tv_withdraw_text,tv_time,tv_details;

    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        tv_withdraw_text = (TextView) v.findViewById(R.id.tv_withdraw_text);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        tv_details= (TextView) v.findViewById(R.id.tv_details);
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, NewsBean.MessageBean news_holder) {
        tv_details.setText(news_holder.getB_details());
        tv_time.setText(news_holder.getB_time());
        tv_withdraw_text.setText("提现消息");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_news;
    }
}
