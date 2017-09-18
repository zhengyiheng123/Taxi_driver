package cinyida.com.car_driver.ui.view;

import cinyida.com.car_driver.net.result.CertificationBean;
import okhttp3.RequestBody;

/**
 * Created by Zheng on 2017/6/13.
 */

public interface CertificationView extends BaseView{
    void initData(CertificationBean certificationBean);
    RequestBody getImages();
}
