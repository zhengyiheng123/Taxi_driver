package cinyida.com.car_driver.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.dialog.WarnDialog;
import cinyida.com.car_driver.lib.LocationTask;
import cinyida.com.car_driver.lib.OnLocationGetListener;
import cinyida.com.car_driver.lib.PositionEntity;
import cinyida.com.car_driver.lib.Utils;
import cinyida.com.car_driver.navigation.CalculateRouteActivity;
import cinyida.com.car_driver.net.result.CatchOrderBean;
import cinyida.com.car_driver.net.result.OrderBean;
import cinyida.com.car_driver.ui.present.TocatchOrder_Present;
import cinyida.com.car_driver.ui.view.TocatchActivity_View;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;
import cinyida.com.car_driver.widget.TimeCountDownTextView;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by Zheng on 2017/5/17.
 */

public class ToCatchCustom_Activity extends AppCompatActivity implements View.OnClickListener,TocatchActivity_View
    ,OnLocationGetListener
{

    private ImageView iv_back;
    private TextView tv_title;
    private MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings;
    private TimeCountDownTextView countdown;
    private ImageView iv_call;
    private Button btn_login,btn_finish;
    private Button btn_confirm;
    private int currentClick=0;
    private TextView tv_extra;
    private TocatchOrder_Present present;
    private CatchOrderBean orderBean;
    private LocationTask mLocationTask;
    private LatLng mPosition;
    private PromptDialog dialog;
    public static ToCatchCustom_Activity instance;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = (String) msg.obj;
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    };
    private PhoneContentObserver observer;
    private boolean isshow;
    private PositionEntity startPosition;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private TextView tv_target,tv_start;
    private int stateLocation;
    private PromptDialog dialog1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_catch_custom);
        observer = new PhoneContentObserver(this, handler);
        getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, observer);
        orderBean = (CatchOrderBean) getIntent().getSerializableExtra("orderbean");
        int state=orderBean.getState();

//        Log.e("zyh",bundle.toString());
        present = new TocatchOrder_Present(this);
        dialog = new PromptDialog(ToCatchCustom_Activity.this);

        initToolBar();
        initView(savedInstanceState);
//        switch (state){
//            case 8:
//                currentClick=1;
//                countdown.setVisibility(View.GONE);
//                btn_login.setVisibility(View.VISIBLE);
//                break;
//            case 9:
//                countdown.setVisibility(View.GONE);
//                btn_login.setVisibility(View.VISIBLE);
//                getCustomSuccess();
//                break;
//            case 10:
//                countdown.setVisibility(View.GONE);
//                btn_login.setVisibility(View.VISIBLE);
//                finishSuccess();
//                break;
//        }
        initEvent();
    }

    private void initEvent() {
        countdown.setOnCountDownFinishListener(new TimeCountDownTextView.onCountDownFinishListener() {
            @Override
            public void onFinish() {
                countdown.setText("倒计时结束");
                present.cancelOrder(orderBean.getOrdernum(),1);

            }
        });
        iv_call.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        countdown.setOnClickListener(this);
    }
    private void initView(Bundle bundle) {

//        MapView mapview=findViewById(R.id.)
        countdown = (TimeCountDownTextView)findViewById(R.id.countdown);
        countdown.start();
        initMap(bundle);
        iv_call = (ImageView) findViewById(R.id.iv_call);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_finish= (Button) findViewById(R.id.btn_finish);
        tv_target = (TextView) findViewById(R.id.tv_target);
        tv_start= (TextView) findViewById(R.id.tv_start);
        tv_start.setText("出发地："+orderBean.getStart_add());
        tv_target.setText("目的地："+orderBean.getStop_add());
        if (!TextUtils.isEmpty(orderBean.getPassengername())){
            tv_title.setText("乘客："+orderBean.getPassengername());
        }else {
            tv_title.setText("乘客");
        }

    }

    /**
     * 初始化高德地图
     */
    private void initMap(Bundle bundle){
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(bundle);
        aMap = mapView.getMap();
        //设置缩放比例
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        uiSettings = aMap.getUiSettings();
        LatLng latLng1 = new LatLng(orderBean.getStart_latitude(),orderBean.getStart_longitude());
        LatLng latLng2=new LatLng(orderBean.getStop_latitude(),orderBean.getStop_longitude());
        List<LatLng> latLngs=new ArrayList<>();
        latLngs.add(latLng1);
        latLngs.add(latLng2);
        BitmapDescriptor bitmapStart= BitmapDescriptorFactory
                .fromResource(R.drawable.ic_start_add);
        BitmapDescriptor bitmapEnd=BitmapDescriptorFactory
                .fromResource(R.drawable.ic_end_add);
        aMap.addMarker(new MarkerOptions().position(latLngs.get(0)).icon(bitmapStart));
        aMap.addMarker(new MarkerOptions().position(latLngs.get(1)).icon(bitmapEnd));
        Utils.zoomToSpan(latLngs,aMap,null);
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }
    private void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("等待出发");
        tv_extra = (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setText("导航");
        tv_extra.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                break;
            case R.id.tv_extra_text:
                       stateLocation =2;
                       dialog.showLoading("加载导航中",false);
                       mLocationTask.startSingleLocate();
                break;
            case R.id.iv_call:
                countdown.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT>=23){
                    PermissionManager.with(ToCatchCustom_Activity.this).request(new PermissionManager.Callback() {
                        @Override
                        public void call(PermissionResult result) {
                            if (!TextUtils.isEmpty(orderBean.getPhonenum())) {
                                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + orderBean.getPhonenum()));
                                startActivity(intent);
                            }
                        }
                    },Manifest.permission.CALL_PHONE,Manifest.permission.READ_CALL_LOG);
                }else {
                    if (!TextUtils.isEmpty(orderBean.getPhonenum())) {
                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + orderBean.getPhonenum()));
                        startActivity(intent);
                    }
                }
                currentClick=1;
                break;
            case R.id.btn_login:
                switch (currentClick){
                    //到达乘客上车地点
                    case 1:
                        View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_warn,null);
                        Button button1= (Button) view1.findViewById(R.id.button1);
                        final WarnDialog dialog=new WarnDialog(view1);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        btn_confirm = (Button) view1.findViewById(R.id.button2);
                        btn_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                present.arriveCustomAddress(orderBean.getOrdernum());
                            }
                        });
                        dialog.show(getSupportFragmentManager(),"");
                        break;
                    case 2:
                        //接到乘客
//                        mLocationTask.startSingleLocate();
                        present.getCustom(orderBean.getOrdernum());
                        break;
                    case 3:
                        //完成
                        btn_login.setEnabled(false);
                        stateLocation=1;
                        startLocation();
//                        present.toFinal("北京市丰台区南四环西路辅路160号靠近诺德中心1期",orderBean.getOrdernum());
                        break;
                }
                break;
            case R.id.btn_finish:
//                present.listenOrder("3");
                warnDialog();
                break;
            case R.id.countdown:
                ToastUtils.show(getApplicationContext(),"请先联系乘客",0);
                break;
        }
    }

    /**
     * 警告弹出框
     */
    private void warnDialog(){
        tv_extra.setEnabled(true);
        AlertDialog.Builder builder=new AlertDialog.Builder(ToCatchCustom_Activity.this);
        builder.setTitle("警告！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getApplicationContext(),Activity_Cancel.class);
                intent.putExtra("ordernum",orderBean.getOrdernum());
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setCancelable(false);
        builder.setMessage("是否确定取消订单，取消订单您将会扣除信用分");
        builder.create().show();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (wakeLock==null){
            ScreenLight();
        }else {
            wakeLock.acquire();
        }
        isshow =true;
        instance=this;
        if (mapView!=null){
            mapView.onResume();
        }
        if (mLocationTask == null){
            mLocationTask = LocationTask.getInstance(getApplicationContext());
            mLocationTask.setOnLocationGetListener(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isshow=false;
    }

    public boolean getShow(){
        return isshow;
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mapView!=null){
            mapView.onPause();
        }
        wakeLock.release();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView!=null){
            mapView.onSaveInstanceState(outState);
        }}

    private void startLocation() {
        showDialog();
        mLocationTask.startSingleLocate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
        if (mLocationTask!=null){
            mLocationTask.onDestroy();
        }
        if (mapView!=null){
            mapView.onDestroy();
        }
        finish();
    }

    @Override
    public void showDialog() {
        dialog.showLoading("结束订单中",false);
    }

    @Override
    public void dismissDialog() {
        dialog.dismissImmediately();
    }

    @Override
    public void finishNow() {
        finish();
    }

    /**
     * 到达乘客地点成功回调
     */
    @Override
    public void getCustomSuccess() {
        btn_login.setText("到达目的地");
        currentClick=3;
    }
    /**
     * 到达乘客地点失败回调
     */
    @Override
    public void getCustomFailed() {
        ToastUtils.show(getApplicationContext(),"请求失败+接到乘客",0);
    }

    @Override
    public void arriveCustomFailed() {
        btn_login.setEnabled(true);
        ToastUtils.show(getApplicationContext(),"请求失败+到达乘客地点",0);
    }

    @Override
    public void arriveCustomSuccess() {
        currentClick=2;
        btn_login.setEnabled(true);
        btn_login.setText("接到乘客");
    }

    @Override
    public void finishFailed() {
        dismissDialog();
        ToastUtils.show(getApplicationContext(),"结束订单失败",0);
        btn_login.setEnabled(true);
    }

    @Override
    public void finishSuccess() {
        dismissDialog();
        btn_login.setEnabled(true);
        Intent intent=new Intent(getApplicationContext(),Receive_Money_Activity.class);
        intent.putExtra("ordernum",orderBean.getOrdernum());
        startActivity(intent);
    }

    @Override
    public void cancelFinish() {
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(ToCatchCustom_Activity.this);
        builder.setCancelable(false);
        builder.setTitle("提示");
        builder.setMessage("由您长时间没有联系乘客，所以订单已取消。");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        startPosition = entity;
        if (dialog!=null){
            dialog.dismissImmediately();
        }
        if (stateLocation == 1){
            String address=entity.address;
            present.toFinal(address,orderBean.getOrdernum());
        }else if (stateLocation == 2){
            Intent intent=new Intent(getApplicationContext(),CalculateRouteActivity.class);
            Bundle bundle=new Bundle();
            PositionEntity endPosition=new PositionEntity();
            endPosition.latitue=orderBean.getStop_latitude();
            endPosition.longitude=orderBean.getStop_longitude();
            bundle.putSerializable("startPosition",startPosition);
            bundle.putSerializable("endPosition",endPosition);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {

    }

    class PhoneContentObserver extends ContentObserver {
        private Context context;
        private Handler handler;

        /**
         * Creates a content observer.  监听用户拨打手机号
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public PhoneContentObserver(Context context, Handler handler) {
            super(handler);
            this.context = context;
            this.handler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            /****************获取到通话记录表的最新一条消息******************/
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(getApplicationContext(),"没有权限",Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, // 使用系统URI，取得通话记录

                    new String[]{CallLog.Calls.NUMBER, // 电话号码

                            CallLog.Calls.CACHED_NAME, // 联系人

                            CallLog.Calls.TYPE, // 通话类型

                            CallLog.Calls.DATE, // 通话时间

                            CallLog.Calls.DURATION // 通话时长

                    }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));

            /** Call log type for incoming calls. */
            //public static final int INCOMING_TYPE = 1;
            /** Call log type for outgoing calls. */
            // public static final int OUTGOING_TYPE = 2;
            /** Call log type for missed calls. */
            // public static final int MISSED_TYPE = 3;
            /** Call log type for voicemails. */
            // public static final int VOICEMAIL_TYPE = 4;
            /** Call log type for calls rejected by direct user action. */
            // public static final int REJECTED_TYPE = 5;
            /** Call log type for calls blocked automatically. */
            //public static final int BLOCKED_TYPE = 6;
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            int duration=cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            Message msg = new Message();
            msg.obj = name + number + date + type;
//            countdown.setVisibility(View.GONE);
//            btn_login.setVisibility(View.VISIBLE);
            if (type == 2 && duration >1 && number.equals(orderBean.getPhonenum())){
                countdown.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
            }
//            handler.sendMessage(msg);
//            Log.e("zyh","姓名："+name + " 号码：" + number + " 日期：" + date + " 类型：" + type+" 时长："+duration);
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public void showCancelDialog(String message,String details){
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(ToCatchCustom_Activity.this);
        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setTitle("提示");
        if (!TextUtils.isEmpty(details)){
            builder.setMessage(message+"\n"+details);
        }else {
            builder.setMessage(message);
        }

        builder.setCancelable(false);
        builder.create().setCancelable(false);
        builder.create().setCanceledOnTouchOutside(false);
        builder.create().show();
    }


    /**
     * 手机亮屏
     */
    private void ScreenLight(){
        powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"My Lock");
        wakeLock.acquire();
    }
}
