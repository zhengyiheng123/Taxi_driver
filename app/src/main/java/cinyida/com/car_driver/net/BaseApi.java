package cinyida.com.car_driver.net;

/**
 * Created by xiaotongchao on 2016/8/25.
 * Function :
 */
public class BaseApi {
    public static boolean isDebug = true;
//    public static String baseTestUrl="http://toefl21.xiaomajj.com/service/";
//    public static String baseTestUrl="https://123.56.67.225:6443/";
//    public static String baseTestUrl="http://192.168.1.30:6080/";
    public static String baseTestUrl="http://didi.jiangliping.com";
    public static String baseOnlineUrl="http://toefl21.xiaomajj.com/service/";

    /**
     * 返回baseurl
     * @return
     */
    public static String getBaseUrl(){
     return isDebug? baseTestUrl:baseOnlineUrl;
    }
}
