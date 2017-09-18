package cinyida.com.car_driver.dialog;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.ui.activity.ToCatchCustom_Activity;
import cinyida.com.car_driver.widget.VerticalViewPager;

/**
 * Created by Zheng on 2017/5/15.
 */
@SuppressLint("ValidFragment")
public class WarnDialog extends DialogFragment{

    private VerticalViewPager vertical;
    private DisplayMetrics dm;
    private TextView tv_failed;

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
//        windowParams.dimAmount = 0.0f;
//        windowParams.gravity= Gravity.TOP;
        window.setAttributes(windowParams);
        Dialog dialog = getDialog();
        if (dialog != null) {
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), windowParams.WRAP_CONTENT);
        }
    }
private View view;

    public WarnDialog(View view) {
        this.view=view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DisplayMetrics();

        //full screen dialog fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        return view;
    }
}
