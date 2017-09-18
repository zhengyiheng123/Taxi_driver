package cinyida.com.car_driver.ui.present;

/**
 * Created by Zheng on 2017/6/1.
 */

public class BasePresent<T>  {
    public String errorMessage;
    public String getErrorMessage(){
        return errorMessage;
    }
    T t;
    public T getData(){

        return t;
    }
}
