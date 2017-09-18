package cinyida.com.car_driver.ui.view;

/**
 * Created by Zheng on 2017/6/13.
 */

public interface CancelReasonView extends BaseView {
    void cancelSuccess(String message);

    void cancelFailed(String message);
}
