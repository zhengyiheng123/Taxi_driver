package cinyida.com.car_driver.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.net.result.OrderBean;
import cinyida.com.car_driver.net.result.UserCenterResult;
import cinyida.com.car_driver.ui.activity.ToCatchCustom_Activity;
import cinyida.com.car_driver.ui.activity.UserCenterActivity;
import cinyida.com.car_driver.utils.DateUtils;
import cinyida.com.car_driver.utils.Myutils;
import cinyida.com.car_driver.widget.CircleTextvie;
import cinyida.com.car_driver.widget.VerticalViewPager;

/**
 * Created by Zheng on 2017/5/15.
 */
@SuppressLint("ValidFragment")
public class CatchOrderDialogRoute extends DialogFragment implements View.OnClickListener {

    private VerticalViewPager vertical;
    private DisplayMetrics dm;
    private TextView tv_failed;
    private TextView tv_dont_order, rl_title, tv_start, tv_end,tv_type,tv_time;
    private CircleTextvie circle;
    private UserCenterActivity activity;
    private OrderBean orderBean;
    TimerTask task=null;
    Timer timer;

    public CatchOrderDialogRoute(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        windowParams.gravity = Gravity.TOP;
        window.setAttributes(windowParams);
        Dialog dialog = getDialog();
        if (dialog != null) {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.9), (int) (dm.heightPixels * 0.80));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

         if (timer != null ){
            timer.cancel();
            task.cancel();
             timer=null;
             task=null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer != null ){
            timer.cancel();
            task.cancel();
            timer=null;
            task=null;
        }else {
            task=new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UserCenterActivity.instance.listenButtonSwitch("听单中",-1);
                        }
                    });
                    dismiss();
                }
            };
            timer=new Timer();
            timer.schedule(task,10000,1000);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        circle = new CircleTextvie(getActivity());
        super.onCreate(savedInstanceState);
        dm = new DisplayMetrics();
        //full screen dialog fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getDialog().setCanceledOnTouchOutside(true);
        this.setCancelable(false);
        View view = inflater.inflate(R.layout.dialog_catchorder1, container);
        initView(view);
        return view;
    }

    private void initView(View v) {
        activity = (UserCenterActivity) getActivity();
        tv_dont_order = (TextView) v.findViewById(R.id.tv_dont_order);
        tv_dont_order.setOnClickListener(this);
        rl_title = (TextView) v.findViewById(R.id.rl_title);
        tv_start = (TextView) v.findViewById(R.id.tv_start);
        tv_end = (TextView) v.findViewById(R.id.tv_end);
        tv_type= (TextView) v.findViewById(R.id.tv_type);
        tv_time= (TextView) v.findViewById(R.id.tv_time);
        if (orderBean.getData().getU_type() .equals("0")){
            tv_type.setText("实时");
            tv_time.setVisibility(View.GONE);
        }else {
            tv_type.setText("预约");
            tv_time.setVisibility(View.VISIBLE);
            tv_time.setText("预约时间："+DateUtils.timedatemmm(orderBean.getData().getAppointment_time()));
        }
        //绑定数数据
        tv_end.setText("目的地："+orderBean.getData().getStop_add());
        Myutils.setTextColorOfPart(getActivity(),tv_end,R.color.tool_bar_color,0,3);
        tv_start.setText("出发地："+orderBean.getData().getStart_add());
        Myutils.setTextColorOfPart(getActivity(),tv_start,R.color.tool_bar_color,0,3);
        if (Double.parseDouble(orderBean.getData().getDistance())<1000.00f){
            rl_title.setText(Double.parseDouble(orderBean.getData().getDistance())+"米");
        }else {
            rl_title.setText(Double.parseDouble(orderBean.getData().getDistance()) / 1000 + "公里");
        }

        TextView tv_see_map = (TextView) v.findViewById(R.id.tv_see_map);
        tv_see_map.setOnClickListener(this);
        ImageView iv_close = (ImageView) v.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dont_order:
                circle.setState(-1);
                activity.toListen();
                dismiss();
                break;
            case R.id.tv_see_map:
                CatchOrderDialogMap mapDialog = new CatchOrderDialogMap(orderBean);
                mapDialog.show(getChildFragmentManager(), "");
                break;
            case R.id.iv_close:
                activity.toListen();
                circle.setState(-1);
                dismiss();
                break;
        }
    }

    public void closeDialog(FragmentManager manager) {
        CatchOrderDialogRoute dialogFragment = (CatchOrderDialogRoute) manager.findFragmentByTag("tag");
        if (dialogFragment != null) {
            dialogFragment.dismissAllowingStateLoss();
        }
    }
}
