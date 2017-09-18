package cinyida.com.car_driver.ui.view;

import cinyida.com.car_driver.net.result.CatchOrderBean;

/**
 * Created by Zheng on 2017/6/2.
 */

public interface UsercenterView extends BaseView {
    public void initData();
    public void showWarnDialog();

    void processStop();

    void processListen();

    void getOrderSuccess(CatchOrderBean catchorderBean);

    void catchFailed(String message);

    void resetState();
}
