package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.lib.LocationTask;
import cinyida.com.car_driver.lib.OnLocationGetListener;
import cinyida.com.car_driver.lib.PositionEntity;
import cinyida.com.car_driver.ui.present.CatchSettingPresent;
import cinyida.com.car_driver.ui.view.CatchSettingView;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.widget.CheckSwitchButton;
import cinyida.com.car_driver.widget.StateTextview;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by Zheng on 2017/5/11.
 */

public class Activity_Catch_Setting extends BaseActivity  implements CatchSettingView,RadioGroup.OnCheckedChangeListener,OnLocationGetListener{

    private TextView tv_setting;
    private CatchSettingPresent present;
    private RadioGroup rb_preference,rg_distance;
    private CheckSwitchButton check_home,tv_check_order;
    private PromptDialog dialog;
    private RadioButton cb_automode;
    //距离临时值
    private int rangeTemp=0;
    //定位初始化
    private LocationTask mLocationTask;
    private LatLng mPosition;
    //讯飞语音
    private SpeechSynthesizer mTts;
    /**
     * 初始化讯飞监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("SHIXIN", "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(context, "初始化失败,错误码：" + code, Toast.LENGTH_SHORT).show();
                ;
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };
    /**
     * 使用SpeechSynthesizer合成语音，不弹出合成Dialog.
     *
     * @param
     */
    public void startSpeaking(String playText) {
        // 进行语音合成.
        if (mTts != null)
            mTts.startSpeaking(playText, new SynthesizerListener() {

                @Override
                public void onSpeakResumed() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSpeakProgress(int arg0, int arg1, int arg2) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSpeakPaused() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSpeakBegin() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onCompleted(SpeechError arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
                    // TODO Auto-generated method stub

                }
            });

    }
    public void init() {
        String text = context.getString(R.string.app_id);
        if ("57b3c4a9".equals(text)) {
            throw new IllegalArgumentException("你不应该用Demo中默认KEY,去讯飞官网申请吧!");
        }
        SpeechUtility.createUtility(context, "appid=" + text);

        // 初始化合成对象.
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
        initSpeechSynthesizer();
    }
    //设置讯飞语音参数
    private void initSpeechSynthesizer() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTts != null)
            mTts.stopSpeaking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationTask!=null){
            mLocationTask.onDestroy();
        }
        if (mTts != null) {
            mTts.stopSpeaking();
            mTts.destroy();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationTask!=null){
            mLocationTask.stopLocate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationTask == null){
            mLocationTask = LocationTask.getInstance(getApplicationContext());
            mLocationTask.setOnLocationGetListener(this);
            mLocationTask.startLocate();
        }else {
            mLocationTask.startLocate();
        }
    }

    //开始定位
    private void startLocation() {
        if (mLocationTask == null){
            mLocationTask = LocationTask.getInstance(getApplicationContext());
            mLocationTask.setOnLocationGetListener(this);
            mLocationTask.startLocate();
        }else {
            mLocationTask.startLocate();
        }
    }

    @Override
    protected void bindEvent() {
        tv_setting.setOnClickListener(this);
        rb_preference.setOnCheckedChangeListener(this);
        //自动模式
        cb_automode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    present.setRange(0);
                }else {
                    present.setRange(rangeTemp);
                }
            }
        });
        //只听收车单
        check_home.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    present.onlyHome(2);
                }else {
                    present.onlyHome(1);
                }
            }
        });
        //只听预约车单
        tv_check_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    present.onlyOrder(2,mPosition.longitude+"",mPosition.latitude+"");
                }else {
                    present.onlyOrder(1,mPosition.longitude+"",mPosition.latitude+"");
                }
            }
        });
        //设置听单范围
        rg_distance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_1:
                        present.setRange(1);
                        break;
                    case R.id.rb_2:
                        present.setRange(2);
                        break;
                    case R.id.rb_3:
                        present.setRange(3);
                        break;
                    case R.id.rb_5:
                        present.setRange(5);
                        break;
                }
            }
        });
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        startLocation();
        dialog = new PromptDialog(Activity_Catch_Setting.this);
        present = new CatchSettingPresent(this);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        rb_preference = (RadioGroup) findViewById(R.id.rb_preference);
        rg_distance= (RadioGroup) findViewById(R.id.rg_distance);
        cb_automode= (RadioButton) findViewById(R.id.cb_automode);
        //订单偏好
        int preference= (int) SharedPreferenceUtils.getParam(getApplicationContext(),"rb_preference",0);
        //只听收车单
        int onlyHome= (int) SharedPreferenceUtils.getParam(getApplicationContext(),"onlyHome",1);
        //只听预约单
        int onlyOrder= (int) SharedPreferenceUtils.getParam(getApplicationContext(),"onlyOrder",1);

        //听单范围
        int distance= (int) SharedPreferenceUtils.getParam(context,"distance",0);
        switch (distance){
            case 0:
                rg_distance.clearCheck();
                cb_automode.setChecked(true);
                break;
            case 1:
                rg_distance.check(R.id.rb_1);
                break;
            case 2:
                rg_distance.check(R.id.rb_2);
                break;
            case 3:
                rg_distance.check(R.id.rb_3);
                break;
            case 5:
                rg_distance.check(R.id.rb_5);
                break;
        }
        switch (preference){
            case 0:
                rb_preference.check(R.id.rb_intime);
                break;
            case 1:
                rb_preference.check(R.id.rb_order);
                break;
            case 2:
                rb_preference.check(R.id.rb_all);
                break;
        }
        check_home = (CheckSwitchButton) findViewById(R.id.check_home);
        tv_check_order= (CheckSwitchButton) findViewById(R.id.tv_check_order);
        if (onlyHome == 1){
            check_home.setChecked(false);
        }else {
            check_home.setChecked(true);
        }
       if (onlyOrder == 1){
           tv_check_order.setChecked(false);
       }else {
           tv_check_order.setChecked(true);
       }
       init();
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_catch_setting;
    }


    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_setting:
                finish();
                break;
        }
    }

    @Override
    public void showDialog() {
        dialog.showLoading("",false);
    }

    @Override
    public void dismissDialog() {
        dialog.dismissImmediately();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            //实时
            case R.id.rb_intime:
                present.setPreference(0,R.id.rb_intime);
                break;
            //预约
            case R.id.rb_order:
                present.setPreference(1,R.id.rb_order);
                break;
            //全部
            case R.id.rb_all:
                present.setPreference(2,R.id.rb_all);
                break;
        }
    }


    @Override
    public void success(int preference) {
        switch (preference){
            case 0:
                startSpeaking("实时车单");
//                ToastUtils.show(context,"只听实时车单",0);
                break;
            case 1:
                startSpeaking("预约车单");
//                ToastUtils.show(context,"只听预约车单",0);
                break;
            case 2:
                startSpeaking("收听全部订单");
//                ToastUtils.show(context,"收听全部订单",0);
                break;
        }
        SharedPreferenceUtils.setParam(getApplicationContext(),"rb_preference",preference);
    }

    @Override
    public void failed(int id) {
        rb_preference.check(id);
    }

    @Override
    public void onlyOrderSuccess(int type) {
        ToastUtils.show(context,"只听预约车单",0);
        SharedPreferenceUtils.setParam(getApplicationContext(),"onlyOrder",type);
    }

    @Override
    public void onlyHomeSuccess(int type) {
        if (type == 2){
            startSpeaking("只听收车单");
        }
//        ToastUtils.show(context,"只听收车单",0);
        SharedPreferenceUtils.setParam(getApplicationContext(),"onlyHome",type);
    }

    @Override
    public void distanceSuccess(int distance) {
        if (distance == 0){
//            ToastUtils.show(context,"自动模式",0);
            startSpeaking("自动模式");
        }else {
            rangeTemp=distance;
            cb_automode.setChecked(false);
            startSpeaking("听单范围"+distance+"公里");
//            ToastUtils.show(context,"听单范围"+distance+"公里",0);
        }
        SharedPreferenceUtils.setParam(context,"distance",distance);
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        mPosition = new LatLng(entity.latitue,entity.longitude);

    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {

    }
}
