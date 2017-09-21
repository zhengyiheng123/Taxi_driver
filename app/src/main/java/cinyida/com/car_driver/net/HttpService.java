package cinyida.com.car_driver.net;








import java.util.List;
import java.util.Map;

import cinyida.com.car_driver.net.result.AboutUs;
import cinyida.com.car_driver.net.result.AddBankBean;
import cinyida.com.car_driver.net.result.Car_Records_Bean;
import cinyida.com.car_driver.net.result.CatchOrderBean;
import cinyida.com.car_driver.net.result.CertificationBean;
import cinyida.com.car_driver.net.result.ChangeBean;
import cinyida.com.car_driver.net.result.ChangeHeanBean;
import cinyida.com.car_driver.net.result.DoubanBean;
import cinyida.com.car_driver.net.result.Driver_Bean;
import cinyida.com.car_driver.net.result.ForgetPassBean;
import cinyida.com.car_driver.net.result.HelpBean;
import cinyida.com.car_driver.net.result.HelperDetails;
import cinyida.com.car_driver.net.result.HomeAddress;
import cinyida.com.car_driver.net.result.HttpResult;
import cinyida.com.car_driver.net.result.LoginResult;
import cinyida.com.car_driver.net.result.MoneyResult;
import cinyida.com.car_driver.net.result.Msg;
import cinyida.com.car_driver.net.result.MybankBean;
import cinyida.com.car_driver.net.result.NewsBean;
import cinyida.com.car_driver.net.result.RegCodeBean;
import cinyida.com.car_driver.net.result.TousuBean;
import cinyida.com.car_driver.net.result.UploadBean;
import cinyida.com.car_driver.net.result.UserCenterResult;
import cinyida.com.car_driver.net.result.ValidateBean;
import cinyida.com.car_driver.net.result.WeixinCodeBean;
import cinyida.com.car_driver.net.result.XinYongBean;
import cinyida.com.car_driver.ui.view.TargetAddBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;
import rx.annotations.Beta;

/**
 * Created by xiaotongchao on 2016/8/25.
 * Function :
 */
public interface HttpService {

    @GET("recite/groups")
    Call<ResponseBody> getTest2();

    /**
     * https 测试
     * @return
     */
    @FormUrlEncoded
    @POST("/driver/login/login")
//    @Headers("Content-type: application/x-www-form-urlencoded")
    Observable<HttpResult<LoginResult>> login(
            @Field("phonenum") String phoonenum,
            @Field("password") String password
    );
    /**
     * https 测试
     * @return
     */
//    @FormUrlEncoded
//    @POST ("v1/user/login.json")
//    @Headers("Content-type: application/x-www-form-urlencoded")
//    Call<User> getTest3(
//            @Field("mobile") String mobile,
//            @Field("password") String password,
//            @Field("os") String os,
//            @Field("imei") String imei
//    );

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("http://api.imysky.com:6080/v1/node/upload.json?uid=606115&timestamp=1472103420&sign=dc2369c18f5bf62770234aa392d21c6e&imei=862263031103196&")
    Call<ResponseBody> uploadFileWithRequestBody(
            @Body MultipartBody multipartBody);

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @return 状态信息
     */
//    @POST("http://api.imysky.com:6080/v1/node/upload.json?uid=606115&timestamp=1472103420&sign=dc2369c18f5bf62770234aa392d21c6e&imei=862263031103196&")
//    Call<ResultResponse<Result>> uploadFileWithRequestBody3(
//            @Body MultipartBody multipartBody);
//@GET("index.php/Home/index/index")
//    Call<FirstBean> getInfo(@Query("page") int page);
//    @GET("whtapi/new_index.php")
//    Call<ResponseBody> getInfo();
    @FormUrlEncoded
    @POST("v1/node/like/create.json")
    Call<HttpResponse<LoginResult>> like_create(@Field("name") String name);

    @Multipart
    @POST("index.php?c=Upload&m=doUpload")
        //  @POST("index.php/Upload/doUpload")
    Call<HttpResponse<Msg>> upload(
            @Part("file\"; filename=\"image.png\" ") RequestBody file,
            @Part("description") RequestBody description);

    /**
     * 获取微信支付参数
     * @return
     */
    @GET("index.php/Home/index/index")
    Call<ResponseBody> getWebChat(@Query("type") String type, @Query("page") int page);


    @GET("v1/node/comment/list.json")
//    @Headers("Content-type: application/x-www-form-urlencoded")
    Call<ResponseBody> comment_list(
            @Query("page") String page,
            @Query("page_rows") String page_rows,
            @Query("nid") String nid
    );

    @GET("top250")
    Observable<DoubanBean> getDouBanInfo(@Query("start") String start, @Query("count") String string);
    /**
     * 注册页面获取验证码
     */
    @FormUrlEncoded
    @POST("/driver/register/code")
    Observable<HttpResult<RegCodeBean>> getRegcodeRegister(@Field("phonenum") String phonenum);

    /**
     * 注册
     * @param phonenum
     * @param code
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/driver/register/register")
    Observable<HttpResult<ValidateBean>> validateCode(@Field("phonenum") String phonenum
            ,@Field("code") String code
            ,@Field("password")String password);

    /**
     * 我的余额
     * @return
     */
    @POST("http://didi.jiangliping.com/driver/blance/withdrawals")
    Observable<HttpResult<MoneyResult>> getBananceData();

    /**
     * 我的
     */
    @POST("/driver/driver/drivermy")
    Observable<HttpResult<UserCenterResult>> getUserCenter();
    /**
     * 司机主页
     */
    @POST("/driver/driver/driverindex")
        Observable<HttpResult<Driver_Bean>> getDriverBean();
    /**
     * 车单记录
     */
    @POST("/driver/single/single")
    Observable<HttpResult<Car_Records_Bean>> getCarRecords();
    /**
     * 上传认证信息
     */
    @POST("/driver/Authentication/driver_info")
    Observable<HttpResult<UploadBean>> uploadInfo(@Body RequestBody body);
    /**
     * 修改头像
     */
    @POST("/driver/authentication/authentication")
    Observable<HttpResult<ChangeHeanBean>> change(@Body RequestBody body);
    /**
     * 修改密码获取验证码
     */
    @FormUrlEncoded
    @POST("/driver/Forgetpass/check")
    Observable<HttpResult<ForgetPassBean>> getCodePass(@Field("phonenum")String phonenum);
    /**
     * 修改密码（"未登录"）
     */
    @FormUrlEncoded
    @POST("/driver/Forgetpass/change")
    Observable<HttpResult<ChangeBean>> changePassword(@Field("phonenum")String phonenum,@Field("codenum")String codenum,@Field("password")String password);

    /**
     * 消息记录
     */
    @POST("/driver/message/message")
    Observable<HttpResult<NewsBean>> getNews();

    /**
     * 修改密码  登陆后
     * @param old_pwd
     * @param new_pwd
     * @return
     */
    @FormUrlEncoded
    @POST("/driver/authentication/modify")
    Observable<HttpResult<RegCodeBean>> resetPass(@Field("old_pwd")String old_pwd, @Field("new_pwd")String new_pwd);

    /**
     * 添加银行卡
     */
    @FormUrlEncoded
    @POST("/driver/authentication/bank")
    Observable<HttpResult<AddBankBean>> addBank(@Field("name")String name,@Field("bank") String banknum);

    /**
     * 我的银行卡
     */
    @POST("/driver/authentication/showbank")
    Observable<HttpResult<MybankBean>> myBank();
    /**
     * 设置收车地点
     */
    @FormUrlEncoded
    @POST("/driver/mode/closecar")
    Observable<HttpResult<TargetAddBean>> addTarget(@Field("closecar")String closecar,@Field("longitude")String longitude,@Field("latitude")String latitude);

    //获取收车地点
    @POST("/driver/driver/check_close")
    Observable<HttpResult<HomeAddress>> getHomeAddress();
    @FormUrlEncoded
    @POST("/driver/authentication/realtime")
    Observable<HttpResult> sendPosition(@Field("longitude")String longitude,@Field("latitude")String latitude);

    /**
     * 听单
     */
    @FormUrlEncoded
    @POST("/driver/mode/hear")
    Observable<HttpResult> listenOrder(@Field("type")String type);

    /**
     * 抢单
     */
    @FormUrlEncoded
    @POST("/driver/graborder/grab")
    Observable<HttpResult<CatchOrderBean>> catchOrder(@Field("ordernum")String ordernum,@Field("u_id")String u_id);

    /**
     * 订单偏好设置
     */
    @FormUrlEncoded
    @POST("/driver/mode/preference")
    Observable<HttpResult> preference(@Field("preference")int preference);

    /**
     * 只听手车单
     */
    @FormUrlEncoded
    @POST("/driver/mode/receiving")

    Observable<HttpResult> onlyHome(@Field("type")int type);
    /**
     * 只听收车预约单
     */
    @FormUrlEncoded
    @POST("/driver/mode/appointment")
    Observable<HttpResult> onlyOrder(@Field("type")int type,@Field("longitude")String longitude,@Field("latitude")String latitude);

    /**
     * 接到乘客
     * @param ordernum
     * @return
     */
    @FormUrlEncoded
    @POST("/driver/graborder/receive")
    Observable<HttpResult> getCustom(@Field("ordernum")String ordernum);

    /**
     * 到达上车地点
     */
    @FormUrlEncoded
    @POST("/driver/graborder/arrive")
    Observable<HttpResult> toArriveAddress(@Field("ordernum")String ordernum);

    /**
     * 到达目的地
     */
    @FormUrlEncoded
    @POST("/driver/graborder/destination")
    Observable<HttpResult> toFinal(@Field("location")String location,@Field("ordernum")String ordern);

    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST("/driver/graborder/cancel")
    Observable<HttpResult> cancelOrder(@Field("ordernum")String ordernum,@Field("d_reason")int u_reason);

    /**
     * 获取验证信息
     */
    @POST("/driver/authentication/message")
    Observable<HttpResult<CertificationBean>> getCertification();

    /**
     * 现金收款  完成订单
     */
    @FormUrlEncoded
    @POST("/driver/graborder/cash")
    Observable<HttpResult> finishOrder(@Field("ordernum")String ordernum);

    /**
     * 查询订单信息
     */
    @FormUrlEncoded
    @POST("/driver/graborder/ordermessage")
    Observable<HttpResult<CatchOrderBean>> getOrderInfo(@Field("ordernum")String ordernum);
    /**
     * 提现帮助
     */
    @POST("/driver/Question/question")
    Observable<HttpResult<HelpBean>> getHelpInfo();

    /**
     * 退出登录
     */
    @POST("/driver/login/signout")
    Observable<HttpResult> loginOut();
    /**
     * 问题详情
     */
    @FormUrlEncoded
    @POST("/driver/Question/answer")
    Observable<HelperDetails> problemDetails(@Field("id")String id);

    /**
     * 替换二维码
     */
    @POST("/driver/authentication/twocode")
    Observable<HttpResult<WeixinCodeBean>> changeWxCode(@Body RequestBody body);
    /**
     * 显示二维码
     */
    @POST("/driver/authentication/ercode")
    Observable<HttpResult<WeixinCodeBean>> loadWeixin();

    /**
     * 投诉
     */
    @FormUrlEncoded
    @POST("/driver/services/feedback")
    Observable<HttpResult<TousuBean>> tousu(@Field("c_id")int c_id,@Field("details")String details,@Field("ordernum")String ordernum);
    @POST("/driver/single/credit")
    Observable<HttpResult<XinYongBean>> xinYongList();

    /**
     *设置听单范围
     */
    @POST("/driver/mode/distance")
    Observable<HttpResult> distance(@Query("distance")int distance);

    /**
     * 意见反馈
     */
    @POST("/driver/Question/feedback")
    Observable<HttpResult> feedback(@Query("content")String content);
    /**
     * 关于我们
     */
    @POST("/Index/Our/aboutour")
    Observable<HttpResult<AboutUs>> aboutUs();
}
