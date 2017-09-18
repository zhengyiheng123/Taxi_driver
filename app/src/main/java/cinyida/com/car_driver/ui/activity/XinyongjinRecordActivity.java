package cinyida.com.car_driver.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.net.result.XinYongBean;
import cinyida.com.car_driver.ui.holder.Xinyongholder;
import cinyida.com.car_driver.ui.present.XinyongPresent;
import cinyida.com.car_driver.ui.view.XinyongView;

/**
 * Created by Zheng on 2017/6/20.
 */

public class XinyongjinRecordActivity extends BaseActivity implements XinyongView{

    private ImageView iv_back;
    private ListView lv_xinyong;
    private XinyongPresent present;

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initView() {
        present=new XinyongPresent(this);
        present.initData();
        lv_xinyong = (ListView) findViewById(R.id.lv_xinyong);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_xinyonglist;
    }

    @Override
    protected void BaseOnclick(View view) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void success(XinYongBean bean) {
        lv_xinyong.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Xinyongholder(context);
            }
        },bean.getCredit()));
    }
}
