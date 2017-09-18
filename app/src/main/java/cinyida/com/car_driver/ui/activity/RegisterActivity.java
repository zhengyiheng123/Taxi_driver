package cinyida.com.car_driver.ui.activity;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.present.CertifyPresent;
import cinyida.com.car_driver.ui.view.CertifyView;
import cinyida.com.car_driver.utils.DateUtils;
import cinyida.com.car_driver.utils.GetImagePath;
import cinyida.com.car_driver.utils.PicassoImageLoader;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
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
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Zheng on 2017/4/27.
 */

public class RegisterActivity extends BaseActivity implements CertifyView{
    private static final int IMAGE_PICKER = 1;
    private TextView tv_start_time,tv_end_time;

    private ImageView iv_supervise,iv_driving_card,iv_idcard,iv_idcard_fan
            ,iv_personand_car,iv_idcard_run,iv_weixin_card;
    private int code=-1;
    private Button btn_login;
    private CertifyPresent present;
    MultipartBody.Builder requestBody = null;

    private PromptDialog dialog;
    private EditText et_username,et_phone,et_company_name,et_cat_num;
    private RadioGroup rb_group;
    private String start_time_stamp;
    private String end_time_stamp;

    private  int sex = -1;
    private ImagePicker imagePicker;
    ArrayList<ImageItem> images = null;
    private int width;
    private int height;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_start_time:
                showDatePickDialog(tv_start_time);
                break;
            case R.id.tv_end_time:
                showDatePickDialog(tv_end_time);
                break;
            case R.id.iv_supervise:
                code =0;
                chooseFromAlbum();
                break;
            case R.id.iv_driving_card:
                code=1;
                chooseFromAlbum();
                break;
            case R.id.iv_idcard:
                code=2;
                chooseFromAlbum();
                break;
            case R.id.iv_idcard_fan:
                code=3;
                chooseFromAlbum();
                break;
            case R.id.iv_personand_car:
                code=4;
                chooseFromAlbum();
                break;
            case R.id.iv_idcard_run:
                code =5;
                chooseFromAlbum();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(et_username.getText().toString()) || TextUtils.isEmpty(et_phone.getText().toString())
                        || start_time_stamp==null || end_time_stamp==null || sex <0 || TextUtils.isEmpty(et_company_name.getText().toString()

                )|| TextUtils.isEmpty(et_cat_num.getText().toString())){
                    ToastUtils.show(context,"请完善信息",0);
                    return;
                }
                present.uploadInfos();
                break;
            case R.id.iv_weixin_card:
                code =6;
                chooseFromAlbum();
                break;
        }
    }

    @Override
    protected void BaseOnclick(View view) {

    }

    //日期选择框
    private void showDatePickDialog(final View v) {
        DatePicker datePicker=new DatePicker(RegisterActivity.this, DateTimePicker.YEAR_MONTH_DAY);
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

    @Override
    protected void bindEvent() {
        tv_end_time.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        iv_supervise.setOnClickListener(this);
        iv_driving_card.setOnClickListener(this);
        iv_idcard.setOnClickListener(this);
        iv_idcard_fan.setOnClickListener(this);
        iv_personand_car.setOnClickListener(this);
        iv_idcard_run.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        iv_weixin_card.setOnClickListener(this);
        rb_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i==R.id.rb_man){
                    sex = 1;
                }else {
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
        tv_title.setText("填写认证信息");
    }

    @Override
    protected void initView() {
        dialog=new PromptDialog(RegisterActivity.this);
        imagePicker = ImagePicker.getInstance();
        requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestPermission();

        present=new CertifyPresent(this);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_time= (TextView) findViewById(R.id.tv_end_time);
        iv_supervise = (ImageView) findViewById(R.id.iv_supervise);
        iv_driving_card= (ImageView) findViewById(R.id.iv_driving_card);
        iv_idcard= (ImageView) findViewById(R.id.iv_idcard);
        iv_idcard_fan= (ImageView) findViewById(R.id.iv_idcard_fan);
        iv_personand_car= (ImageView) findViewById(R.id.iv_personand_car);
        iv_idcard_run= (ImageView) findViewById(R.id.iv_idcard_run);
        iv_weixin_card= (ImageView) findViewById(R.id.iv_weixin_card);
        btn_login = (Button) findViewById(R.id.btn_login);

        et_username = (EditText) findViewById(R.id.et_username);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_company_name= (EditText) findViewById(R.id.et_company_name);
        et_cat_num= (EditText) findViewById(R.id.et_cat_num);
        rb_group = (RadioGroup) findViewById(R.id.rb_group);
//        radioButton = (RadioButton) findViewById(rb_group.getCheckedRadioButtonId());
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        PermissionManager.with(this).request(new PermissionManager.Callback() {
            @Override
            public void call(PermissionResult result) {
                if (result.isSuccessful()){
//                    ToastUtils.show(RegisterActivity.this,"成功",0);
                }
            }
        }, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_register;
    }


    @Override
    public void showDialog() {
        dialog.showLoading("上传中",false);
    }

    @Override
    public void dismissDialog() {
        dialog.dismissImmediately();
    }

    @Override
    public RequestBody getImages() {
        requestBody.addFormDataPart("name", et_username.getText().toString())
            .addFormDataPart("sex", sex + "")
            .addFormDataPart("managerphone", et_phone.getText().toString())
            .addFormDataPart("jianducardstart", start_time_stamp)
            .addFormDataPart("jianducardend", end_time_stamp)
            .addFormDataPart("company",et_company_name.getText().toString())
            .addFormDataPart("carnum",et_cat_num.getText().toString());
            return requestBody.build();
    }
    @Override
    public void initData() {
        startActivityForResult(new Intent(context,UserCenterActivity.class),1);
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
        //ImagePicker.getInstance().setSelectedImages(images);
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
                        requestBody.addFormDataPart("jianduimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_supervise);
                        break;
                    case 1:
                        requestBody.addFormDataPart("drivecardimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_driving_card);
                        break;
                    case 2:
                        requestBody.addFormDataPart("card1", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_idcard);
                        break;
                    case 3:
                        requestBody.addFormDataPart("card2", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_idcard_fan);
                        break;
                    case 4:
                        requestBody.addFormDataPart("peoimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_personand_car);
                        break;
                    case 5:
                        requestBody.addFormDataPart("carimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                        Glide.with(context).load(images.get(0).path).into(iv_idcard_run);
                        break;
                    case 6:
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
