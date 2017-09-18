package cinyida.com.car_driver.ui.view;

import cinyida.com.car_driver.net.result.ChangeHeanBean;
import okhttp3.RequestBody;

/**
 * Created by Zheng on 2017/6/5.
 */

public interface Driver_View extends BaseView {
    public void initData();
    RequestBody getRequestBody();
    void changeHead(ChangeHeanBean bean);
}
