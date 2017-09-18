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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.net.BaseApi;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.WeixinCodeBean;
import cinyida.com.car_driver.utils.GetImagePath;
import cinyida.com.car_driver.utils.PicassoImageLoader;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;
import cn.qqtheme.framework.picker.OptionPicker;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/5/2.
 */

public class Receive_Fee_Activity extends BaseActivity {
    private ImageView iv_myweixin;
    private TextView tv_title,tv_extra;
    private MultipartBody.Builder requestBody;
    private PromptDialog dialog;
    private ImagePicker imagePicker;

    private static final int IMAGE_PICKER = 1;
    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_extra= (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setOnClickListener(this);
        tv_extra.setText("上传");
        tv_title.setText("收车费");

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
    protected void initView() {
        imagePicker = ImagePicker.getInstance();
        dialog = new PromptDialog(Receive_Fee_Activity.this);

        requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestPermission();
        iv_myweixin = (ImageView) findViewById(R.id.iv_myweixin);
       Observable<HttpResult<WeixinCodeBean>> result= ServiceApi.getInstance().getServiceContract().loadWeixin();
        result.map(new ResultFilter<WeixinCodeBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeixinCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    ToastUtils.show(context,e.getMessage(),0);
                    }

                    @Override
                    public void onNext(WeixinCodeBean weixinCodeBean) {
                        Glide.with(Receive_Fee_Activity.this).load(BaseApi.getBaseUrl()+"/"+weixinCodeBean.getImage()).into(iv_myweixin);
                    }
                });
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_receive_fee;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_extra_text:
                chooseFromAlbum();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                File file = new File(images.get(0).path);
                requestBody.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                dialog.showLoading("上传中",false);
                Observable<HttpResult<WeixinCodeBean>> result=ServiceApi.getInstance().getServiceContract().changeWxCode(requestBody.build());
                result.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<HttpResult<WeixinCodeBean>>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismissImmediately();
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtils.show(context,e.getMessage(),0);
                                dialog.dismissImmediately();
                            }

                            @Override
                            public void onNext(HttpResult<WeixinCodeBean> weixinCodeBeanHttpResult) {
                                ToastUtils.show(context,weixinCodeBeanHttpResult.getMessage(),0);
                                Glide.with(Receive_Fee_Activity.this).load(BaseApi.getBaseUrl()+"/"+weixinCodeBeanHttpResult.getData().getImage()).into(iv_myweixin);
                            }
                        });
            }
        }

        }
    ArrayList<ImageItem> images = null;
        //选取图片
        private void chooseFromAlbum() {
            imagePicker.setImageLoader(new PicassoImageLoader());
            imagePicker.setMultiMode(false);
            imagePicker.setShowCamera(true);
            imagePicker.setCrop(true);
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 700, getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 960, getResources().getDisplayMetrics());
                imagePicker.setOutPutX(700);
                imagePicker.setOutPutY(960);

            imagePicker.setFocusWidth(width);
            imagePicker.setFocusHeight(height);
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
            //ImagePicker.getInstance().setSelectedImages(images);
            startActivityForResult(intent, IMAGE_PICKER);
        }
    }

