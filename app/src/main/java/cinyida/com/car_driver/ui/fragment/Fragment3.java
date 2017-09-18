package cinyida.com.car_driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseFragment;
import cinyida.com.car_driver.ui.activity.UserCenterActivity;
import cinyida.com.car_driver.utils.SharedPreferenceUtils;

/**
 * Created by Zheng on 2017/8/14.
 */

public class Fragment3 extends BaseFragment {

    private TextView tv_enter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment3,null,false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_enter = (TextView) view.findViewById(R.id.tv_enter);
        tv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), UserCenterActivity.class);
                getActivity().startActivity(intent);
                SharedPreferenceUtils.setParam(getActivity(),"is_first",1);
            }
        });
    }
}
