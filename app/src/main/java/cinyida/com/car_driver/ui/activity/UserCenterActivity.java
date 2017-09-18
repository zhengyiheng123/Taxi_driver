package cinyida.com.car_driver.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.dialog.CatchOrderDialogFailed;
import cinyida.com.car_driver.dialog.CatchOrderDialogRoute;
import cinyida.com.car_driver.lib.LocationTask;
import cinyida.com.car_driver.lib.OnLocationGetListener;
import cinyida.com.car_driver.lib.PositionEntity;
import cinyida.com.car_driver.net.BaseApi;
import cinyida.com.car_driver.net.result.CatchOrderBean;
import cinyida.com.car_driver.net.result.OrderBean;
import cinyida.com.car_driver.net.result.UserCenterResult;
import cinyida.com.car_driver.ui.holder.AppointmentHolder;
import cinyida.com.car_driver.ui.holder.Operating_Data_Holder;
import cinyida.com.car_driver.ui.present.UsercenterPresent;
import cinyida.com.car_driver.ui.view.UsercenterView;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.utils.common.PermissionManager;
import cinyida.com.car_driver.utils.common.PermissionResult;
import cinyida.com.car_driver.widget.CircleImageView;
import cinyida.com.car_driver.widget.CircleTextvie;
import cinyida.com.car_driver.widget.MyListView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by Zheng on 2017/4/26.
 */

public class UserCenterActivity extends BaseActivity implements UsercenterView, OnLocationGetListener ,SwipeRefreshLayout.OnRefreshListener{

    private ImageView iv_back;
    private TextView tv_title;
    private CircleImageView circle_head;
    private RelativeLayout rl_more;
    private CircleTextvie circle;
    private TextView tv_stop,tv_setting,tv_test,tv_username,tv_catch_num,tv_goodrate,tv_rank
            ,tv_allmoney,tv_allorder,tv_xinyong
            ;
    private MyListView lv_data,mylist_apponit;
    private SwipeRefreshLayout swipe_refresh;
    private LinearLayout ll_operating_data;
    private Timer timer;
    private TimerTask timerTask;
    private UsercenterPresent present;
    private PromptDialog dialog;
    private LocationTask mLocationTask;
    private LatLng mPosition;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    //是否可以返回刷新
    private boolean isResume;

    //单例对象
    public static UserCenterActivity instance=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    initAlis((String) msg.obj);
                    break;
                case 2:
                    present.queryOrderInfo((String) msg.obj);
                    break;
            }
        }
    };
    public static boolean isLight;
    private CatchOrderDialogRoute catchOrderDialog;
    private Dialog dialog1;


    @Override
    protected void bindEvent() {
        iv_back.setOnClickListener(this);
        circle_head.setOnClickListener(this);
        rl_more.setOnClickListener(this);
        circle.setOnClickListener(this);
        tv_stop.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        circle.setOnClickListener(this);
        tv_test.setOnClickListener(this);
        ll_operating_data.setOnClickListener(this);
        tv_allorder.setOnClickListener(this);
        tv_xinyong.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的");
        TextView tv_extra= (TextView) findViewById(R.id.tv_extra_text);
        tv_extra.setVisibility(View.VISIBLE);
        tv_extra.setText("更多");
        tv_extra.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        isResume = false;
        requirePermission();
        dialog = new PromptDialog(UserCenterActivity.this);
        present = new UsercenterPresent(this);

        circle_head = (CircleImageView) findViewById(R.id.circle_head);

        rl_more = (RelativeLayout) findViewById(R.id.rl_more);
        circle = (CircleTextvie) findViewById(R.id.circle);
        circle.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_circle_start));

        tv_stop = (TextView) findViewById(R.id.tv_stop);
        tv_setting= (TextView) findViewById(R.id.tv_setting);
        tv_username= (TextView) findViewById(R.id.tv_username);
        tv_catch_num= (TextView) findViewById(R.id.tv_catch_num);
        tv_test= (TextView) findViewById(R.id.tv_test);
        tv_goodrate= (TextView) findViewById(R.id.tv_goodrate);
        tv_rank= (TextView) findViewById(R.id.tv_rank);
        tv_allmoney= (TextView) findViewById(R.id.tv_allmoney);
        tv_allorder= (TextView) findViewById(R.id.tv_allorder);
        tv_xinyong= (TextView) findViewById(R.id.tv_xinyong);
        mylist_apponit= (MyListView) findViewById(R.id.mylist_apponit);
        mylist_apponit.setFocusable(false);
        lv_data = (MyListView) findViewById(R.id.lv_data);
        lv_data.setFocusable(false);
        ScrollView scroll= (ScrollView) findViewById(R.id.scroll);
        scroll.smoothScrollTo(0,0);
        ll_operating_data = (LinearLayout) findViewById(R.id.ll_operating_data);
        swipe_refresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.tool_bar_color));
        swipe_refresh.setOnRefreshListener(this);
        onRefresh();
    }
    //获取位置权限
    private void requirePermission() {
        PermissionManager.with(this).request(new PermissionManager.Callback() {
            @Override
            public void call(PermissionResult result) {
                if (!result.isSuccessful()){
                    ToastUtils.show(getApplicationContext(),"未开启定位权限，无法获取服务，请前往设置。",0);
                }
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION);
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_usercenter;
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.circle_head:
                startActivity(MyPageActivity.class);
                break;
            case R.id.rl_more:
//                startActivity(Received_Money_Activity.class);
                break;
            case R.id.tv_extra_text:
                startActivity(MoreActivity.class);
                break;
            case R.id.circle:
                int state=circle.getState();
                switch (state){
                    case 0:
                        present.listenOrder("3");
                        break;
                    case 1:
                        listenButtonSwitch("抢单中",-1);
                        present.catchOrder(orderBean.getData().getOrdernum(),orderBean.getData().getU_id()+"");
                        break;
                }
                break;
            case R.id.tv_stop:
                present.listenOrder("0");
                if (catchOrderDialog!=null){
                    catchOrderDialog.closeDialog(getSupportFragmentManager());
                }
                break;
            case R.id.tv_setting:
                present.listenOrder("0");
                if (catchOrderDialog!=null){
                    catchOrderDialog.closeDialog(getSupportFragmentManager());
                }
//                switchListenState(0,View.GONE,0,"出车");
                startActivity(Activity_Catch_Setting.class);
                break;
            case R.id.tv_test:
//                showOrderDialog();
                break;
            case R.id.tv_xinyong:
                startActivity(XinyongjinRecordActivity.class);
                break;
            case R.id.tv_allorder:
                startActivity(Car_Order_Records.class);
            break;
        }
    }


    //显示抢单弹框
    public void showOrderDialog() {
//        cancelTimer();
        soundPool.play(1,0.6f, 0.6f, 0, 0, 1);
        listenButtonSwitch("抢单",1);
        if (catchOrderDialog!=null){
            catchOrderDialog.closeDialog(getSupportFragmentManager());
        }
        catchOrderDialog = new CatchOrderDialogRoute(orderBean);
        catchOrderDialog.show(getSupportFragmentManager(),"tag");
    }

    /**
     * 清除定时器状态
     */
    private void cancelTimer() {
        if (timer!=null && timerTask!=null){
            timer.cancel();
            timerTask.cancel();
            timerTask=null;
            timer=null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.e("zyh","onDestroy()");
        JPushInterface.stopPush(this);
//        cancelTimer();
        present.listenOrder("0");
        if (mLocationTask!=null){
            mLocationTask.onDestroy();
        }
    }

    @Override
    public void showDialog() {
        dialog.showLoading("请稍后",false);
    }

    @Override
    public void dismissDialog() {
        swipe_refresh.setRefreshing(false);
        dialog.dismissImmediately();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSound();
    }

    @Override
    public void initData() {
        UserCenterResult result=present.getData();
        int state=result.getDriver().getState();//0未认证，2已认证，1审核中，3停用
            if (state ==0){
                showWarnDialog();
        }
        String alis= (String) SharedPreferenceUtils.getParam(getApplicationContext(),"phonenum","");
        handler.sendMessage(handler.obtainMessage(1,alis));
        JPushInterface.resumePush(this);
        SharedPreferenceUtils.setParam(getApplicationContext(),"isCertified",result.getDriver().getState());
        Glide.with(context).load(BaseApi.getBaseUrl()+"/"+result.getDriver().getImage()).error(getResources().getDrawable(R.mipmap.ic_head)).placeholder(getResources().getDrawable(R.mipmap.ic_head)).into(circle_head);
        if (result.getDriver().getName()!=null) {
            if (state == 1){
                tv_username.setText(result.getDriver().getName()+" 审核中");
            }else if (state == 0){
                tv_username.setText(result.getDriver().getName()+" 未认证");
            }else if (state ==2){
                tv_username.setText(result.getDriver().getName()+" 已认证");
            }else if (state ==3){
                tv_username.setText(result.getDriver().getName()+" 停用");
            }
        }
        tv_catch_num.setText(result.getDriver().getAllorder()+"");
        tv_goodrate.setText(result.getDriver().getGoodrate());
        tv_rank.setText(result.getDriver().getPaiming()+"");
        tv_allmoney.setText(result.getDriver().getAllmoney());
//        tv_allorder.setText("抢"+result.getDriver().getAllorder()+"单");
        tv_xinyong.setText(result.getDriver().getD_reputation()+"信用币");
        mylist_apponit.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new AppointmentHolder(UserCenterActivity.this);
            }
        },present.getData().getAppointment()));
        lv_data.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new Operating_Data_Holder();
            }
        },present.getData().getOrder()));
        startLocation();
        if (present.getData().getOrder().size()>0){
            UserCenterResult.OrderBean bean=present.getData().getOrder().get(0);
            if(bean.getU_type() ==0){
                if (bean.getState()==2 ||bean.getState() ==8){
                    showUnFinishedDialog(bean.getOrdernum());
                }
            }

        }
    }
    private void showUnFinishedDialog(final String ordernum){
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(UserCenterActivity.this);
//        builder.setCancelable(false);

        builder.setPositiveButton("去完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog1.dismiss();
                handler.sendMessage(handler.obtainMessage(2,ordernum));
            }
        });
        builder.setTitle("提示！");
        builder.setMessage("您有未完成订单");
        dialog1 = builder.create();
        dialog1.show();
    }
    private void startLocation() {
        if (mLocationTask == null){
            mLocationTask = LocationTask.getInstance(getApplicationContext());
            mLocationTask.setOnLocationGetListener(this);
            mLocationTask.startLocate();
        }else {
            mLocationTask.startLocate();
        }
    }

    /**
     * 设置极光推送Alis
     * @param alis
     */
    public void initAlis(String alis) {
        JPushInterface.setAlias(getApplicationContext(), "Taxi_Driver_"+alis, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i != 0){
                    ToastUtils.show(getApplicationContext(),"别名错误",0);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("zyh","onResume()"+isResume);
        present.listenOrder("0");
//        listenButtonSwitch("出车",0);
        canReceive=1;
        instance=this;
        isLight=true;
        if (wakeLock==null){
            ScreenLight();
        }else {
            wakeLock.acquire();
        }
            if (isResume){
                present.initGetData();
            }
        if (mLocationTask == null){
            mLocationTask = LocationTask.getInstance(getApplicationContext());
            mLocationTask.setOnLocationGetListener(this);
            mLocationTask.startLocate();
        }else {
            mLocationTask.startLocate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            present.initGetData();
        }
    }

    @Override
    public void showWarnDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(UserCenterActivity.this);
        builder.setMessage("您还未进行认证，请先进行认证后再使用");
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAll();
            }
        });
        builder.setPositiveButton("现在认证", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(RegisterActivity.class);
            }
        });
//        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 听单状态改变
     */
    public void toListen(){
//        circle.setText("听单中");
//        circle.setState(-1);
        listenButtonSwitch("听单中",-1);
        tv_stop.setVisibility(View.VISIBLE);
    }

    /**
     * 听单按钮切换
     * @param text  按钮文字
     * @param state  按钮状态码
     */
    public void listenButtonSwitch(String text,int state){
        circle.setText(text);
        circle.setState(state);
    }
    private int canReceive=0;

    /**
     * 正在接单中
     */
    @Override
    public void processStop() {
        switchListenState(1,View.VISIBLE,-1,"听单中");
    }

    public int getReceive(){
        return canReceive;
    }

    /**
     * 停止接单了
     */
    @Override
    public void processListen() {
        switchListenState(0,View.GONE,0,"出车");
        if (catchOrderDialog!=null){
            catchOrderDialog.closeDialog(getSupportFragmentManager());
        }
    }

    /**
     *
     * @param iscanReceive  是否接单
     * @param visible  停止接单按钮是否可见
     * @param state  circle 按钮的状态 0  点击开启听单，1 点击抢单
     * @param text
     */
    private void switchListenState(int iscanReceive,int visible,int state,String text) {
        canReceive=iscanReceive;
        tv_stop.setVisibility(visible);
        circle.setState(state);
        circle.setText(text);
    }

    @Override
    public void getOrderSuccess(CatchOrderBean catchorder) {
        if (dialog1 !=null){
            dialog1.dismiss();
        }
        int u_type=catchorder.getU_type();
        if (catchOrderDialog!=null){
            catchOrderDialog.closeDialog(getSupportFragmentManager());
        }
        if ( u_type == 0){
            //实时
            present.listenOrder("0");
            Intent intent=new Intent(UserCenterActivity.this,ToCatchCustom_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("orderbean",catchorder);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (u_type == 1){
            //预约
            present.initGetData();
        }
        toListen();
    }

    /**
     * 抢单失败
     */
    @Override
    public void catchFailed(String message) {
        ToastUtils.show(getApplicationContext(),message,0);
        if(catchOrderDialog!=null) {
            catchOrderDialog.closeDialog(getSupportFragmentManager());
        }
        toListen();
    }
        public LatLng getLatLng(){
            return new LatLng(Double.parseDouble(orderBean.getData().getStart_latitude()),Double.parseDouble(orderBean.getData().getStart_longitude()));
        }
    /**
     * 开启听单和关闭听单状态失败后的回调
     */
    @Override
    public void resetState() {
        listenButtonSwitch("出车",0);
        ToastUtils.show(getApplicationContext(),"服务器连接出错，请稍后再试",0);
    }

    @Override
        public void onBackPressed() {
            exitApp(getApplicationContext());
        }
    @Override
    public void onLocationGet(PositionEntity entity) {
        mPosition = new LatLng(entity.latitue,entity.longitude);
//        Log.e("zyh",mPosition.latitude+" ,"+mPosition.longitude);
        present.sendPosition(mPosition.longitude+"",mPosition.latitude+"");
    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {

    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("zyh","onPause()");
        present.listenOrder("0");
        isResume = true;
        isLight=false;
        canReceive=0;
        mLocationTask.stopLocate();
        wakeLock.release();
    }

    private void ScreenLight(){
        powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"My Lock");
        wakeLock.acquire();
    }
    //返回的推送订单信息
    private OrderBean orderBean;
    public void setOrderBean(OrderBean orderBean){
        this.orderBean=orderBean;
    }

    private SoundPool soundPool;
    private void initSound(){
        soundPool= new SoundPool(1, AudioManager.STREAM_SYSTEM,5);
        soundPool.load(this,R.raw.dingding,1);

    }

    @Override
    public void onRefresh() {
        present.initGetData();
    }
}
