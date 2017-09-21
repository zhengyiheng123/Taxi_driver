package cinyida.com.car_driver.ui.view;

import cinyida.com.car_driver.net.result.HomeAddress;

/**
 * Created by Zheng on 2017/6/8.
 */

public interface Target_Address_View extends BaseView {
    String getTargetAdd();

    void close();
    void initAddress(HomeAddress address);
}
