package cinyida.com.car_driver.ui.model;

import java.util.Observer;

import cinyida.com.car_driver.net.ServiceApi;
import cinyida.com.car_driver.net.result.DoubanBean;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.LoginResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by Zheng on 2017/4/25.
 */

public class LoginBiz  {

    /**
     * 登录
     */
    public Observable<HttpResult<LoginResult>> login(String username, String code){
       return ServiceApi.getInstance().getServiceContract().login(username,code);
    }
    /**
     * 测试
     */
    public Observable<DoubanBean> getMovie(String start,String count){
        return ServiceApi.getInstance().getServiceContract().getDouBanInfo(start,count);
    }
}
