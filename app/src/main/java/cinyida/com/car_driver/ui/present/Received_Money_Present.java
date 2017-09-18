package cinyida.com.car_driver.ui.present;

import java.util.List;

import cinyida.com.car_driver.net.result.Money_Record_Bean;
import cinyida.com.car_driver.ui.model.Received_Money_Biz;
import cinyida.com.car_driver.ui.view.Received_Money_View;

/**
 * Created by Zheng on 2017/4/27.
 */

public class Received_Money_Present {
    private Received_Money_Biz moneyBiz;
    private Received_Money_View moneyView;

    private List<Money_Record_Bean> moneyList;

    public Received_Money_Present(Received_Money_View moneyView) {
        this.moneyView = moneyView;
        moneyBiz=new Received_Money_Biz();
    }

    /**
     * 初始化数据
     */
    public void initData(){
        moneyList= moneyBiz.getData();
    }
    /**
     * 取得数据
     */
    public List<Money_Record_Bean> getData(){
        return moneyList;
    }
}
