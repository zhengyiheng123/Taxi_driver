package cinyida.com.car_driver.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseFragment;
import cinyida.com.car_driver.ui.adapter.MyFragmentAdapter;
import cinyida.com.car_driver.ui.fragment.Fragment1;
import cinyida.com.car_driver.ui.fragment.Fragment2;
import cinyida.com.car_driver.ui.fragment.Fragment3;

/**
 * Created by Zheng on 2017/8/14.
 */

public class IntroduceActivity extends BaseActivity {

    private ViewPager viewpager;
    private List<Fragment> imageList=new ArrayList<>();

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        initData();
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),imageList));
    }

    private void initData() {
        imageList.add(new Fragment1());
        imageList.add(new Fragment2());
        imageList.add(new Fragment3());
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_slide;
    }

    @Override
    protected void BaseOnclick(View view) {

    }

}
