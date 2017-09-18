package cinyida.com.car_driver.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseFragment;

/**
 * Created by Zheng on 2017/8/14.
 */

public class Fragment1 extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,null,false);
        return view;
    }
}
