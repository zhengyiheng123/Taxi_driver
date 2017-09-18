package cinyida.com.car_driver.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Zheng on 2017/5/15.
 */

public class MyVerticalViewPager extends FragmentPagerAdapter {
    List<Fragment> mList;
    public MyVerticalViewPager(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList=mList;
    }

    @Override
    public Fragment getItem(int position) {
        return  mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
