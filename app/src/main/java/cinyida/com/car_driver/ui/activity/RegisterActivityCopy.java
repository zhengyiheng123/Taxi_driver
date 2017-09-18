package cinyida.com.car_driver.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.io.File;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.present.CertifyPresent;
import cinyida.com.car_driver.ui.view.CertifyView;
import cinyida.com.car_driver.utils.DateUtils;
import cinyida.com.car_driver.utils.GetImagePath;
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


/**
 * Created by Zheng on 2017/4/27.
 */

public class RegisterActivityCopy extends BaseActivity implements CertifyView{
    private boolean isSDCard;
    private String outDir;
    private File file;
    private File outputFile;
    private TextView tv_start_time,tv_end_time;

    private ImageView iv_supervise,iv_driving_card,iv_idcard,iv_idcard_fan
            ,iv_personand_car,iv_idcard_run,iv_weixin_card;
    private int code=-1;
    private Button btn_login;
    private MultipartBody.Part part;
    private CertifyPresent present;
    MultipartBody.Builder requestBody = null;

    private PromptDialog dialog;
    private EditText et_username,et_phone,et_company_name,et_cat_num;
    private RadioGroup rb_group;
    private RadioButton radioButton;
    private String start_time_stamp;
    private String end_time_stamp;

    private  int sex = -1;

    /**
     * 图片下选择方式
     */
    private void showPhotoSelect(final ImageView img){
        OptionPicker optionPicker=new OptionPicker(RegisterActivityCopy.this,new String[]{"拍照","相册"});
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                switch (index){
                    case 0:
                        chooseFromCamera(img);
                        break;
                    case 1:
                        chooseFromAlbum(img);
                        break;
                }
            }
        });
        optionPicker.show();


    }
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
                showPhotoSelect(iv_supervise);
                break;
            case R.id.iv_driving_card:
                code=1;
                showPhotoSelect(iv_driving_card);
                break;
            case R.id.iv_idcard:
                code=2;
                showPhotoSelect(iv_idcard);
                break;
            case R.id.iv_idcard_fan:
                code=3;
                showPhotoSelect(iv_idcard_fan);
                break;
            case R.id.iv_personand_car:
                code=4;
                showPhotoSelect(iv_personand_car);
                break;
            case R.id.iv_idcard_run:
                code =5;
                showPhotoSelect(iv_idcard_run);
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
                showPhotoSelect(iv_weixin_card);
                break;
        }
    }

    @Override
    protected void BaseOnclick(View view) {

    }

    //日期选择框
    private void showDatePickDialog(final View v) {
        DatePicker datePicker=new DatePicker(RegisterActivityCopy.this, DateTimePicker.YEAR_MONTH_DAY);
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
        dialog=new PromptDialog(RegisterActivityCopy.this);
        requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestPermission();
        isSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDCard) {
            outDir = Environment.getExternalStorageDirectory().toString();
        } else {
            outDir = Environment.getDataDirectory().toString();
        }
        file = createImageFile(System.currentTimeMillis()+".jpg");


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
    /**
     * 创建一个文件
     * @param fileName
     * @return
     */
    public  File createImageFile( String fileName) {
        File outDir1 = new File(outDir);
        if (!outDir1.exists()) {
            outDir1.mkdirs();
        }
        return new File(outDir1, fileName);
    }
    private void chooseFromAlbum(ImageView img) {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider  cinyida.com.car_driver.fileprovider
//            Uri uriForFile = FileProvider.getUriForFile(RegisterActivity.this, "cinyida.com.car_driver.fileprovider", file);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            startActivityForResult(intent, 1007);
//        } else {
//            startActivityForResult(intent, 1008);
//        }
    }


    private void chooseFromCamera(ImageView img) {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            Uri uriForFile = FileProvider.getUriForFile(this, "cinyida.com.car_driver.fileprovider", file);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(intentFromCapture, 1006);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param inputUri
     */
    public void startPhotoZoom(Uri inputUri) {

        if (inputUri == null) {
            Log.i("", "The uri is not exist.");
            return;
        }
        outputFile = createImageFile(System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri outPutUri = Uri.fromFile(outputFile);
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else {
            Uri outPutUri = Uri.fromFile(outputFile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String url = GetImagePath.getPath(this, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        if (code == 6){
            intent.putExtra("aspectX", 1.242);
            intent.putExtra("aspectY", 1.704);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 1242);
            intent.putExtra("outputY", 1704);
        }else {
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 800);
            intent.putExtra("outputY", 400);
        }

        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        startActivityForResult(intent, 1009);//这里就将裁剪后的图片的Uri返回了
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //7.0以下相册
        if (requestCode == 1008) {
            startPhotoZoom(data.getData());
        }
        //7.0以上相册
        if (requestCode == 1007) {
            if(data==null)
                return;
            File imgUri = new File(GetImagePath.getPath(this, data.getData()));
            Uri dataUri = FileProvider.getUriForFile(this, "cinyida.com.car_driver.fileprovider", imgUri);
            startPhotoZoom(dataUri);
        }
        //拍照
        if (requestCode == 1006) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri inputUri = FileProvider.getUriForFile(this, "cinyida.com.car_driver.fileprovider", file);//通过FileProvider创建一个content类型的Uri
                startPhotoZoom(inputUri);//设置输入类型
            } else {
                Uri inputUri = Uri.fromFile(file);
                startPhotoZoom(inputUri);
            }
        }
        //裁剪
        if (requestCode == 1009) {
            BitmapFactory.decodeFile(outputFile.getAbsolutePath());
            File file=new File(outputFile.getAbsolutePath());
            switch (code){
                case 0:
                    requestBody.addFormDataPart("jianduimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                    iv_supervise.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
                    break;
                case 1:
                    requestBody.addFormDataPart("drivecardimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                    iv_driving_card.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
                    break;
                case 2:
                    requestBody.addFormDataPart("card1", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                    iv_idcard.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
                    break;
                case 3:
                    requestBody.addFormDataPart("card2", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                    iv_idcard_fan.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
                    break;
                case 4:
                    requestBody.addFormDataPart("peoimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                    iv_personand_car.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
                    break;
                case 5:
                    requestBody.addFormDataPart("carimg", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                    iv_idcard_run.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
                    break;
                case 6:
                    requestBody.addFormDataPart("weixin", file.getName() , RequestBody.create(MediaType.parse("image/*"), file));
                    iv_weixin_card.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
                    break;
            }
        }
    }

}
