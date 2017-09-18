package cinyida.com.car_driver.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.lib.Utils;
import cinyida.com.car_driver.net.result.OrderBean;
import cinyida.com.car_driver.ui.activity.UserCenterActivity;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.widget.VerticalViewPager;

/**
 * Created by Zheng on 2017/5/15.
 */

@SuppressLint("ValidFragment")
public class CatchOrderDialogMap extends DialogFragment implements View.OnClickListener{

    private VerticalViewPager vertical;
    private DisplayMetrics dm;
    private AMapLocationClientOption mLocationOption;
    private MapView mapView;
    private AMap aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private LatLng mPosition;
    private OrderBean orderBean;
    @SuppressLint("ValidFragment")
    public CatchOrderDialogMap(OrderBean orderBean){
        this.orderBean=orderBean;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        windowParams.gravity= Gravity.TOP;
        window.setAttributes(windowParams);
        Dialog dialog = getDialog();
        if (dialog != null) {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.9), (int) (dm.heightPixels * 0.80));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DisplayMetrics();
        initMap();
        //full screen dialog fragment
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view=inflater.inflate(R.layout.dialog_catchordermap,container);
        initView(savedInstanceState,view);
        return view;
    }

    private void initView(Bundle bundle,View v) {
        TextView tv_see_map= (TextView) v.findViewById(R.id.tv_see_map);
        tv_see_map.setOnClickListener(this);
        //初始化地图
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(bundle);
        aMap = mapView.getMap();
        aMap.getUiSettings().setScaleControlsEnabled(true);
        aMap.getUiSettings().setCompassEnabled(true);
        aMap.getUiSettings().setRotateGesturesEnabled(false);
        LatLng latLng1 = new LatLng(Double.parseDouble(orderBean.getData().getStart_latitude()),Double.parseDouble(orderBean.getData().getStart_longitude()));
        LatLng latLng2=new LatLng(Double.parseDouble(orderBean.getData().getStop_latitude()),Double.parseDouble(orderBean.getData().getStop_longitude()));
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
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        //启动定位
//        mLocationClient.startLocation();
//        final Marker marker = aMap.addMarker(new MarkerOptions().position(mPosition).title("北京").snippet("DefaultMarker"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_see_map:
                dismiss();
                break;
        }
    }


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation!=null) {
                if (aMapLocation.getErrorCode() == 0) {
                    mPosition = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                    if (aMap!=null){

                    }
                }
            }
        }
    };

    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mapView!=null){
            mapView.onResume();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView!=null){
            mapView.onPause();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView!=null){
            mapView.onSaveInstanceState(outState);
        }}

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("zyh","onDestroy");
        if (mapView!=null){
            mapView.onDestroy();
        }
        if (mLocationClient!=null){
            mLocationClient.onDestroy();
        }
    }
}
