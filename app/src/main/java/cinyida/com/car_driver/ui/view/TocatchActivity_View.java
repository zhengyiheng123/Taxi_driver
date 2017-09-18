package cinyida.com.car_driver.ui.view;

/**
 * Created by Zheng on 2017/6/9.
 */

public interface TocatchActivity_View extends BaseView {
    void finishNow();

    void getCustomSuccess();

    void getCustomFailed();

    void arriveCustomFailed();

    void arriveCustomSuccess();

    void finishFailed();

    void finishSuccess();

    void cancelFinish();
}
