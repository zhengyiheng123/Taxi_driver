package cinyida.com.car_driver.exception;

/**
 * Created by Zheng on 2017/3/16.
 */
public class ApiException extends RuntimeException {
    public ApiException(String  msg) {
        super(msg);
    }
}
