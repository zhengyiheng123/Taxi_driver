package cinyida.com.car_driver.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.BaseApi;
import cinyida.com.car_driver.net.result.CertificationBean;
import cinyida.com.car_driver.ui.present.CertificationPresent;
import cinyida.com.car_driver.ui.present.CertifyPresent;
import cinyida.com.car_driver.ui.view.CertificationView;
import cinyida.com.car_driver.utils.DateUtils;
import cinyida.com.car_driver.utils.PhotoUtils;
import cinyida.com.car_driver.utils.PicassoImageLoader;
import cinyida.com.car_driver.utils.ToastUtils;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static cinyida.com.car_driver.utils.PhotoUtils.CHOOSE_PHOTO;
import static cinyida.com.car_driver.utils.PhotoUtils.CROP_PHOTO;
import static cinyida.com.car_driver.utils.PhotoUtils.TAKE_PHOTO;

/**
 * Created by Zheng on 2017/5/4.
 */

public class Certification_Setting_Activity extends BaseActivity implements CertificationView{

    private TextView tv_start_time,tv_end_time;
    private EditText et_manager_phone,et_username,et_car_num,et_company_name;
    private PhotoUtils photoUtils;
    private int code=-1;
    private ImageView iv_supervise,iv_driving_card,iv_idcard,iv_idcard_fan
            ,iv_personand_car,iv_idcard_run,iv_weixin_card;

    private CertificationPresent present;
    private RadioGroup rg_all;
    private RadioButton rb_male,rb_female;
    private MultipartBody.Builder requestBody;
    //开始时间
    private String start_time_stamp;
    //结束时间
    private String end_time_stamp;
    //性别
    private int sex=-1;
    private ImagePicker imagePicker;

    //图片压缩的宽高
    private int width;
    private int height;

    //图片集合
    ArrayList<ImageItem> images = null;

    private static final int IMAGE_PICKER = 1;
    private TextView tv_extra;
    private CertificationBean certificationBean;

    @Override
    protected void bindEvent() {
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        iv_supervise.setOnClickListener(this);
        iv_driving_card.setOnClickListener(this);
        iv_idcard.setOnClickListener(this);
        iv_idcard_fan.setOnClickListener(this);
        iv_personand_car.setOnClickListener(this);
        iv_idcard_run.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_extra.setOnClickListener(this);
        iv_weixin_card.setOnClickListener(this);
        rg_all.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i==R.id.rb_male){
                    sex = 1;
                }else if (i==R.id.rb_female){
                    sex = 0;
                }
            }
        });
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("认证资料");
        tv_extra = (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setText("修改");
        tv_extra.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        imagePicker = ImagePicker.getInstance();
        present=new CertificationPresent(this);
        requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        present.getNetData();
        photoUtils = new PhotoUtils(Certification_Setting_Activity.this);
        et_username= (EditText) findViewById(R.id.et_username);
        et_company_name= (EditText) findViewById(R.id.et_company_name);
        et_manager_phone= (EditText) findViewById(R.id.et_manager_phone);
        et_car_num= (EditText) findViewById(R.id.et_car_num);
        rg_all = (RadioGroup) findViewById(R.id.rg_all);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_time= (TextView) findViewById(R.id.tv_end_time);
        iv_supervise = (ImageView) findViewById(R.id.iv_supervise);
        iv_driving_card= (ImageView) findViewById(R.id.iv_driving_card);
        iv_idcard= (ImageView) findViewById(R.id.iv_idcard);
        iv_idcard_fan= (ImageView) findViewById(R.id.iv_idcard_fan);
        iv_personand_car= (ImageView) findViewById(R.id.iv_personand_car);
        iv_idcard_run= (ImageView) findViewById(R.id.iv_idcard_run);
        iv_weixin_card= (ImageView) findViewById(R.id.iv_weixin_card);

        rb_male = (RadioButton) findViewById(R.id.rb_male);
//        rb_male.setEnabled(false);
        rb_female= (RadioButton) findViewById(R.id.rb_female);
//        rb_female.setEnabled(false);
    }



    @Override
    protected int getResourcesId() {
        return R.layout.activity_certification_setting;
    }



    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_start_time:
                showDatePickDialog(tv_start_time);
                break;
            case R.id.tv_end_time:
                showDatePickDialog(tv_end_time);
                break;
            case R.id.iv_supervise:
                //点击此处上传监督卡照片
                code =0;
                chooseFromAlbum();
                break;
            case R.id.iv_driving_card:
                //点击此处上传行驶本照片
                code=1;
                chooseFromAlbum();
                break;
            case R.id.iv_idcard:
                //点击此处上传身份证正面照片
                code=2;
                chooseFromAlbum();
                break;
            case R.id.iv_idcard_fan:
                //点击此处上传身份证反面照片
                code=3;
                chooseFromAlbum();
                break;
            case R.id.iv_personand_car:
                //人车合影：人车合影正面照，能看清人、车牌、顶灯
                code=4;
                chooseFromAlbum();
                break;
            case R.id.iv_idcard_run:
                //点击此处上传驾驶本照片
                code =5;
                chooseFromAlbum();
                break;
            case R.id.iv_weixin_card:
                //微信收款码
                code =6;
                chooseFromAlbum();
                break;
            case R.id.tv_extra_text:
                if (TextUtils.isEmpty(et_username.getText().toString()) || TextUtils.isEmpty(et_manager_phone.getText().toString())
                        || start_time_stamp==null || end_time_stamp==null || sex <0 || TextUtils.isEmpty(et_company_name.getText().toString()

                )|| TextUtils.isEmpty(et_car_num.getText().toString())){
                    ToastUtils.show(context,"请完善信息",0);
                    return;
                }
                if (et_username.getText().toString().equals(certificationBean.getName()) && et_manager_phone.getText().toString().equals(certificationBean.getManagerphone())
                        && start_time_stamp.equals(certificationBean.getJianducardstart()+"") && end_time_stamp.equals(certificationBean.getJianducardend()+"")
                        && sex==certificationBean.getSex() && et_company_name.getText().toString().equals(certificationBean.getCompany())
                        && et_car_num.getText().toString().equals(certificationBean.getCarnum())
                        ){
                    ToastUtils.show(context,"没有改动",0);
                    return;
                }
                present.uploadInfos();
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
    public void initData(CertificationBean bean) {
        certificationBean = bean;
        et_manager_phone.setText(bean.getManagerphone());
        et_company_name.setText(bean.getCompany());
        et_username.setText(bean.getName());
        if (bean.getSex() == 0){
            rg_all.check(R.id.rb_female);
        }else {
            rg_all.check(R.id.rb_male);
        }
        tv_start_time.setText(DateUtils.timedate(bean.getJianducardstart()+""));
        tv_end_time.setText(DateUtils.timedate(bean.getJianducardend()+""));
        start_time_stamp=bean.getJianducardstart()+"";
        end_time_stamp=bean.getJianducardend()+"";
        et_car_num.setText(bean.getCarnum());
        Glide.with(Certification_Setting_Activity.this).load(BaseApi.getBaseUrl()+"/"+bean.getJianduimg()).into(iv_supervise);
        Glide.with(Certification_Setting_Activity.this).load(BaseApi.getBaseUrl()+"/"+bean.getDrivecardimg()).into(iv_driving_card);
        Glide.with(Certification_Setting_Activity.this).load(BaseApi.getBaseUrl()+"/"+bean.getCard1()).into(iv_idcard);
        Glide.with(Certification_Setting_Activity.this).load(BaseApi.getBaseUrl()+"/"+bean.getCard2()).into(iv_idcard_fan);
        Glide.with(Certification_Setting_Activity.this).load(BaseApi.getBaseUrl()+"/"+bean.getPeoimg()).into(iv_personand_car);
        Glide.with(Certification_Setting_Activity.this).load(BaseApi.getBaseUrl()+"/"+bean.getCarimg()).into(iv_idcard_run);
        Glide.with(Certification_Setting_Activity.this).load(BaseApi.getBaseUrl()+"/"+bean.getWeixin()).into(iv_weixin_card);
    }

    @Override
    public RequestBody getImages() {
        requestBody.addFormDataPart("name", et_username.getText().toString())
                .addFormDataPart("sex", sex + "")
                .addFormDataPart("managerphone", et_manager_phone.getText().toString())
                .addFormDataPart("jianducardstart", start_time_stamp)
                .addFormDataPart("jianducardend", end_time_stamp)
                .addFormDataPart("company",et_company_name.getText().toString())
                .addFormDataPart("carnum",et_car_num.getText().toString());
        return requestBody.build();
    }
    //日期选择框
    private void showDatePickDialog(final View v) {
        DatePicker datePicker=new DatePicker(Certification_Setting_Activity.this, DateTimePicker.YEAR_MONTH_DAY);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                TextView tv= (TextView) v;
                String time=year+"年"+month+"月"+day+"日"+"00时00分00秒";
                String timsstamp= DateUtils.data(time);
                if (v.getId() == R.id.tv_start_time){
                    start_time_stamp=timsstamp;
                }
                else if (v.getId()==R.id.tv_end_time){
                    end_time_stamp=timsstamp;
                }
                tv.setText(year+"-"+month+"-"+day);
            }
        });
        datePicker.show();
    }
    private void chooseFromAlbum() {
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        if (code == 6){
            width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 700, getResources().getDisplayMetrics());
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 960, getResources().getDisplayMetrics());
            imagePicker.setOutPutX(700);
            imagePicker.setOutPutY(960);
        }else {
            width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics());
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            imagePicker.setOutPutX(800);
            imagePicker.setOutPutY(480);
        }

        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        startActivityForResult(intent, IMAGE_PICKER);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                File file=new File(images.get(0).path);
                switch (code){
                    case 0:
                        if (file.length()<0){
                            return;
                        }
                        file.length();
                        requestBody.addFormDataPart("jianduimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_supervise);
                        break;
                    case 1:
                        if (file.length()<0){
                            return;
                        }
                        requestBody.addFormDataPart("drivecardimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_driving_card);
                        break;
                    case 2:
                        if (file.length()<0){
                            return;
                        }
                        requestBody.addFormDataPart("card1", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_idcard);
                        break;
                    case 3:
                        if (file.length()<0){
                            return;
                        }
                        requestBody.addFormDataPart("card2", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_idcard_fan);
                        break;
                    case 4:
                        if (file.length()<0){
                            return;
                        }
                        requestBody.addFormDataPart("peoimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_personand_car);
                        break;
                    case 5:
                        if (file.length()<0){
                            return;
                        }
                        requestBody.addFormDataPart("carimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_idcard_run);
                        break;
                    case 6:
                        if (file.length()<0){
                            return;
                        }
                        requestBody.addFormDataPart("weixin", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_weixin_card);
                        break;
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
