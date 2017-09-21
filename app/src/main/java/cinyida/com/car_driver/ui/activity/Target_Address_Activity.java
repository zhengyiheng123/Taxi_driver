package cinyida.com.car_driver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.help.Tip;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.result.HomeAddress;
import cinyida.com.car_driver.ui.present.Target_Address_Present;
import cinyida.com.car_driver.ui.view.Target_Address_View;
import cinyida.com.car_driver.utils.Constants;
import cinyida.com.car_driver.utils.ToastUtils;

/**
 * Created by Zheng on 2017/5/15.
 */

public class Target_Address_Activity extends BaseActivity implements Target_Address_View{

    private ImageView iv_back;
    private TextView tv_title;
    private EditText et_target_add;
    private Target_Address_Present present;
    private TextView tv_extra;
    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE_INPUTTIPS = 101;
    public static final int RESULT_CODE_KEYWORDS = 102;
    //经度
    private String Longitude="";
    //纬度
    private String Latitude="";
    @Override
    protected void bindEvent() {
        et_target_add.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("目的地订单设置");
        tv_extra = (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setText("保存");
        tv_extra.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        present = new Target_Address_Present(this);
        et_target_add = (EditText) findViewById(R.id.et_target_add);
        present.getAddress();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_target_address;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_extra_text:
                if (TextUtils.isEmpty(et_target_add.getText().toString())){
                    ToastUtils.showShort(getApplicationContext(),"请输入目的地");
                    return;
                }
                present.addtarget_Address(et_target_add.getText().toString(),Longitude,Latitude);
                break;
            case R.id.et_target_add:
                startActivityForResult(new Intent(getApplicationContext(),InputTipsActivity.class),REQUEST_CODE);
                break;
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public String getTargetAdd() {
        return et_target_add.getText().toString();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void initAddress(HomeAddress address) {
        et_target_add.setText(address.getD_close());
        Latitude=address.getLatitude();
        Longitude=address.getLongitude();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_CODE_INPUTTIPS && data!=null){
            Tip tip = data.getParcelableExtra(Constants.EXTRA_TIP);
            et_target_add.setText(tip.getName());
            if (tip.getPoint() == null){
                ToastUtils.showShort(getApplicationContext(),"请重新选择地点");
                return;
            }
            Latitude=tip.getPoint().getLatitude()+"";
            Longitude=tip.getPoint().getLongitude()+"";;
        }else if (resultCode == RESULT_CODE_KEYWORDS && data!=null){

        }
    }
}
