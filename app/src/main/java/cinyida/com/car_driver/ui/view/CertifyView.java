package cinyida.com.car_driver.ui.view;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Zheng on 2017/6/5.
 */

public interface CertifyView extends BaseView {
    RequestBody getImages();
    void initData();
}
