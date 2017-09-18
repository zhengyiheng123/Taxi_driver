package cinyida.com.car_driver.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
import cinyida.com.car_driver.net.result.ChangeHeanBean;
import cinyida.com.car_driver.net.result.Driver_Bean;
import cinyida.com.car_driver.ui.adapter.MyGridView;
import cinyida.com.car_driver.ui.present.Driver_Bean_Present;
import cinyida.com.car_driver.ui.view.Driver_View;
import cinyida.com.car_driver.utils.GetImagePath;
import cinyida.com.car_driver.utils.PicassoImageLoader;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;
import cinyida.com.car_driver.widget.CircleImageView;
import cn.qqtheme.framework.picker.OptionPicker;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Zheng on 2017/4/26.
 */

public class MyPageActivity extends BaseActivity implements Driver_View{


    private static final int IMAGE_PICKER = 1;
    private ImageView iv_back;
    private TextView tv_title,company,tv_allorder,tv_rank,phonenum;

    private Driver_Bean_Present present;
    private CircleImageView circle_head;
    private TextView tv_name;
    private RatingBar rating;
    private GridView gd_view;
    private MultipartBody.Builder builder;
    private PromptDialog dialog;
    private ImagePicker imagePicker;
    ArrayList<ImageItem> images = null;


    @Override
    protected void bindEvent() {
        circle_head.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("司机主页");
    }

    @Override
    protected void initView() {
        imagePicker = ImagePicker.getInstance();
        dialog = new PromptDialog(MyPageActivity.this);
        initPhoto();
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        present=new Driver_Bean_Present(this);
        present.getNetData();
        circle_head = (CircleImageView) findViewById(R.id.circle_head);
        tv_name = (TextView) findViewById(R.id.tv_name);
        company= (TextView) findViewById(R.id.company);
        tv_allorder= (TextView) findViewById(R.id.tv_allorder);
        rating = (RatingBar) findViewById(R.id.rating);
        tv_rank= (TextView) findViewById(R.id.tv_rank);
        gd_view = (GridView) findViewById(R.id.gd_view);
        phonenum= (TextView) findViewById(R.id.phonenum);

    }

    private void initPhoto() {
        requestPermission();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_mypage;
    }



    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.circle_head:
                PermissionManager.with(this).request(new PermissionManager.Callback() {

                    private int height;
                    private int width;

                    @Override
                    public void call(PermissionResult result) {
                        if (result.isSuccessful()){
                            imagePicker.setImageLoader(new PicassoImageLoader());
                            imagePicker.setMultiMode(false);
                            imagePicker.setShowCamera(true);
                            imagePicker.setCrop(true);
                                width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
                                height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
                                imagePicker.setOutPutX(500);
                                imagePicker.setOutPutY(500);

                            imagePicker.setFocusWidth(width);
                            imagePicker.setFocusHeight(height);
                            Intent intent = new Intent(context, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                            //ImagePicker.getInstance().setSelectedImages(images);
                            startActivityForResult(intent, IMAGE_PICKER);
                        }
                    }
                }, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
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
    public void initData() {
        Driver_Bean bean=present.getData();
        Glide.with(this).load(BaseApi.getBaseUrl()+"/"+bean.getDriver().getImage()).error(getResources().getDrawable(R.mipmap.ic_head)).into(circle_head);
        tv_name.setText(bean.getDriver().getName());
        company.setText(bean.getDriver().getCompany());
        tv_allorder.setText(bean.getDriver().getAllorder()+"");
        tv_rank.setText(bean.getDriver().getPaiming()+"");
        rating.setRating(bean.getDriver().getStar());
        gd_view.setAdapter(new MyGridView(bean.getBiaoqian()));
        phonenum.setText("手机号:"+bean.getDriver().getPhonenum());
    }

    @Override
    public RequestBody getRequestBody() {
        return builder.build();
    }

    @Override
    public void changeHead(ChangeHeanBean bean) {
        finish();
        Glide.with(this).load(BaseApi.getBaseUrl()+"/"+bean.getImage()).error(getResources().getDrawable(R.mipmap.ic_head)).into(circle_head);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                File file = new File(images.get(0).path);
                builder.addFormDataPart("image", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                present.changeHead();
            }
        }
    }
}
