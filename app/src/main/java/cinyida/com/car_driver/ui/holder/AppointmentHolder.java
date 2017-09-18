package cinyida.com.car_driver.ui.holder;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseViewHolder;
import cinyida.com.car_driver.exception.ApiException;
import cinyida.com.car_driver.net.Filter.ResultFilter;
import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.CatchOrderBean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.UserCenterResult;
import cinyida.com.car_driver.ui.activity.Activity_Cancel;
import cinyida.com.car_driver.ui.activity.ToCatchCustom_Activity;
import cinyida.com.car_driver.ui.present.UsercenterPresent;
import cinyida.com.car_driver.utils.DateUtils;
import cinyida.com.car_driver.utils.ToastUtils;
import me.leefeng.promptlibrary.PromptDialog;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/6/16.
 */

public class AppointmentHolder extends BaseViewHolder<UserCenterResult.AppointmentBean> implements View.OnClickListener{

    private TextView order_time,tv_user_name,tv_ordernum,tv_start,tv_target;
    private BaseActivity activity;
    private Button tv_tocancel;
    private Button tv_tofinish;
    private final PromptDialog dialog;

    public AppointmentHolder(BaseActivity activity){
        this.activity=activity;
        dialog = new PromptDialog(activity);
    }
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        order_time = (TextView) v.findViewById(R.id.order_time);
        tv_user_name= (TextView) v.findViewById(R.id.tv_user_name);
        tv_ordernum= (TextView) v.findViewById(R.id.tv_ordernum);
        tv_start= (TextView) v.findViewById(R.id.tv_start);
        tv_target= (TextView) v.findViewById(R.id.tv_target);
        tv_tocancel = (Button) v.findViewById(R.id.tv_tocancel);
        tv_tofinish = (Button) v.findViewById(R.id.tv_tofinish);
        return this;
    }
    private UserCenterResult.AppointmentBean appointmentBean;
    @Override
    public void onBindViewHolder(Context context, final UserCenterResult.AppointmentBean appointmentBean) {
        this.appointmentBean=appointmentBean;
        order_time.setText("预约时间："+ DateUtils.timedatemmm(appointmentBean.getAppointment_time()));
        tv_ordernum.setText("订单号："+appointmentBean.getOrdernum());
        tv_user_name.setText(appointmentBean.getNickname());
        tv_start.setText("出发地："+appointmentBean.getStart_add());
        tv_target.setText("目的地："+appointmentBean.getStop_add());
        tv_tofinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryOrderInfo(appointmentBean.getOrdernum());
            }
        });
        tv_tocancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(activity);
                builder.setTitle("提示！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancelOrder(appointmentBean.getOrdernum(),1);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setMessage("是否确定取消订单，取消订单您可能会扣除信用分");
                builder.create().show();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_appointment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tocancel:

                break;
            case R.id.btn_finish:
                Intent intent=new Intent(activity,ToCatchCustom_Activity.class);
                activity.startActivity(intent);
                break;
        }
    }

    public void cancelOrder(String ordernum,int u_reason){
        Observable<HttpResult> result= ServiceApi.getInstance().getServiceContract().cancelOrder(ordernum,u_reason);
        result.map(new Func1<HttpResult, HttpResult>() {
            @Override
            public HttpResult call(HttpResult httpResult) {
                if (httpResult.getCode() == 1) {
                    return httpResult;
                }else {
                    throw new ApiException(httpResult.getMessage());
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e)
                    {  ToastUtils.show(activity,e.getMessage(),0);
                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        UsercenterPresent.instance.initGetData();
                        ToastUtils.show(activity,httpResult.getMessage(),0);
                    }
                });
    }

    /**
     * 查询订单信息
     */
    public void queryOrderInfo(String ordernum){
        dialog.showLoading("加载中",false);
        Observable<HttpResult<CatchOrderBean>> result=ServiceApi.getInstance().getServiceContract().getOrderInfo(ordernum);
        result.map(new ResultFilter<CatchOrderBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CatchOrderBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismissImmediately();
                        ToastUtils.show(activity,e.getMessage(),0);
                    }

                    @Override
                    public void onNext(CatchOrderBean catchOrderBean) {
                        dialog.dismissImmediately();
                        Intent intent=new Intent(activity,ToCatchCustom_Activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("orderbean",catchOrderBean);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });
    }
}
