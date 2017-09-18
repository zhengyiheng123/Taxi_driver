package cinyida.com.car_driver.ui.view;

/**
 * Created by Zheng on 2017/6/7.
 */

public interface ForgetPassword_View extends BaseView {
    void initData();
    String getPhonenum();
    String getPassword();
    String getCode();

    void finishi(String message);

    void failed(String message);
}
