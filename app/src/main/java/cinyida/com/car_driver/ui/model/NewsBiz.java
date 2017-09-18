package cinyida.com.car_driver.ui.model;

import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.net.result.NewsBean;

/**
 * Created by Zheng on 2017/5/2.
 */

public class NewsBiz {
    public List<NewsBean> getDataNet(){
        List<NewsBean> mList=new ArrayList<>();
        for ( int i=0;i<10;i++){
            mList.add(new NewsBean());
        }
        return mList;
    }

}
