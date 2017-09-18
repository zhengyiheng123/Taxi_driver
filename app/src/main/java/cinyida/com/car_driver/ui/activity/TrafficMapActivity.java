package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.utils.permission.PermissionUtils;
import cinyida.com.car_driver.utils.permission.PermissionsManager;

/**
 * Created by Zheng on 2017/5/5.
 */

public class TrafficMapActivity extends AppCompatActivity  {

    private MapView mapView;
    private AMap mAMap;
    private MyLocationStyle myLocationStyle;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        PermissionUtils.location(this, new PermissionUtils.OnPermissionResult() {
            @Override
            public void onGranted() {
                initView(savedInstanceState);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults);
    }

    private void initView(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.navi_view);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (mAMap == null) {
            mAMap = mapView.getMap();
            mAMap.setTrafficEnabled(true);
            UiSettings uiSettings = mAMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            uiSettings.setRotateGesturesEnabled(false);
            uiSettings.setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            uiSettings.setCompassEnabled(true);
            uiSettings.setScaleControlsEnabled(true);
        }
        location();
    }
    //显示定位蓝点
    private void location(){
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        finish();
    }
}