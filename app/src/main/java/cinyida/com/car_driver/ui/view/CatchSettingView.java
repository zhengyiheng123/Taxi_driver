package cinyida.com.car_driver.ui.view;

/**
 * Created by Zheng on 2017/6/12.
 */

public interface CatchSettingView extends BaseView{
    void success(int preference);

    void failed(int id);

    void onlyOrderSuccess(int type);

    void onlyHomeSuccess(int type);
    //设置听单范围成功
    void distanceSuccess(int distance);
}
