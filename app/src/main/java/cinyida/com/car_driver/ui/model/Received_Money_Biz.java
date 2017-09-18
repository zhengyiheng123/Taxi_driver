package cinyida.com.car_driver.ui.model;

import java.util.ArrayList;
import java.util.List;

import cinyida.com.car_driver.net.result.Money_Record_Bean;

/**
 * Created by Zheng on 2017/4/27.
 */

public class Received_Money_Biz {
    public List<Money_Record_Bean> getData(){
        List<Money_Record_Bean> mList=new ArrayList<>();
        mList.add(new Money_Record_Bean());
        mList.add(new Money_Record_Bean());
        mList.add(new Money_Record_Bean());
        mList.add(new Money_Record_Bean());
        mList.add(new Money_Record_Bean());
        mList.add(new Money_Record_Bean());
        return mList;
    }
}
