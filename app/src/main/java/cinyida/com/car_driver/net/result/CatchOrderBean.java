package cinyida.com.car_driver.net.result;

import java.io.Serializable;

/**
 * Created by Zheng on 2017/6/9.
 */

public class CatchOrderBean implements Serializable{

    private String phonenum;
    private int id;
    private int u_id;
    private int d_id;
    private String price;
    private String pay_price;
    private String ordernum;
    private String start_add;
    private double start_longitude;
    private double start_latitude;
    private String stop_add;
    private double stop_longitude;
    private double stop_latitude;
    private int addtime;
    private int state;
    private String paytype;
    private int u_type;
    private int appointment_time;
    private String city;
    private int u_arrive;
    private Object d_arrive;
    private int u_reason;
    private int d_reason;
    private String passengername;

    public String getPassengername() {
        return passengername;
    }

    public void setPassengername(String passengername) {
        this.passengername = passengername;
    }

    public void setD_reason(int d_reason) {
        this.d_reason = d_reason;
    }

    public int getD_reason() {
        return d_reason;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setStart_longitude(double start_longitude) {
        this.start_longitude = start_longitude;
    }

    public void setStart_latitude(double start_latitude) {
        this.start_latitude = start_latitude;
    }

    public void setStop_longitude(double stop_longitude) {
        this.stop_longitude = stop_longitude;
    }

    public void setStop_latitude(double stop_latitude) {
        this.stop_latitude = stop_latitude;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPay_price() {
        return pay_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getStart_add() {
        return start_add;
    }

    public void setStart_add(String start_add) {
        this.start_add = start_add;
    }

    public double getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(int start_longitude) {
        this.start_longitude = start_longitude;
    }

    public double getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(int start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getStop_add() {
        return stop_add;
    }

    public void setStop_add(String stop_add) {
        this.stop_add = stop_add;
    }

    public double getStop_longitude() {
        return stop_longitude;
    }

    public void setStop_longitude(int stop_longitude) {
        this.stop_longitude = stop_longitude;
    }

    public double getStop_latitude() {
        return stop_latitude;
    }

    public void setStop_latitude(int stop_latitude) {
        this.stop_latitude = stop_latitude;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public int getU_type() {
        return u_type;
    }

    public void setU_type(int u_type) {
        this.u_type = u_type;
    }

    public int getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(int appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getU_arrive() {
        return u_arrive;
    }

    public void setU_arrive(int u_arrive) {
        this.u_arrive = u_arrive;
    }

    public Object getD_arrive() {
        return d_arrive;
    }

    public void setD_arrive(Object d_arrive) {
        this.d_arrive = d_arrive;
    }

    public int getU_reason() {
        return u_reason;
    }

    public void setU_reason(int u_reason) {
        this.u_reason = u_reason;
    }
}
