package cinyida.com.car_driver.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.utils.GetImagePath;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;
import cinyida.com.car_driver.utils.permission.PermissionUtils;
import cinyida.com.car_driver.utils.permission.PermissionsManager;

/**
 * Created by Zheng on 2017/6/6.
 */

public class ChoosePhoto extends BaseActivity {
    private boolean isSDCard;
    private String outDir;
    private File file;
    private File outputFile;
    private ImageView iv_image;
    private Button btn_camera,btn_choose;

    @Override
    protected void bindEvent() {
        btn_camera.setOnClickListener(this);
        btn_choose.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        PermissionManager.with(this).request(new PermissionManager.Callback() {
            @Override
            public void call(PermissionResult result) {
                if (result.isSuccessful()){
//                    ToastUtils.show(ChoosePhoto.this,"成功",0);
                }
            }
        }, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);



        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_choose= (Button) findViewById(R.id.btn_choose);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        isSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDCard) {
            outDir = Environment.getExternalStorageDirectory().toString();
        } else {
            outDir = Environment.getDataDirectory().toString();
        }
        file = createImageFile(System.currentTimeMillis()+".jpg");
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
    private void chooseFromCamera() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider  cinyida.com.car_driver.fileprovider
            Uri uriForFile = FileProvider.getUriForFile(ChoosePhoto.this, "cinyida.com.car_driver.fileprovider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 1007);
        } else {
            startActivityForResult(intent, 1008);
        }
    }


    private void chooseFromAlbum() {
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
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
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
            iv_image.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
        }
    }
    @Override
    protected int getResourcesId() {
        return R.layout.activity_choosephoto;
    }

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_camera:
                chooseFromCamera();
                break;
            case R.id.btn_choose:
                chooseFromAlbum();
                break;
        }
    }
}
