package cinyida.com.car_driver.ui.view;

/**
 * Created by Zheng on 2017/4/25.
 */

public interface LoginView extends BaseView{
    String getPhonenum();
    String getPassword();
    void nextActivity();
    /**
     * 显示数据
     */
    void initText();
}
