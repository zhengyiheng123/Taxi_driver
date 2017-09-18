package cinyida.com.car_driver.net.result;

import java.io.Serializable;

/**
 * Created by Zheng on 2017/6/9.
 */

public class OrderBean implements Serializable{

    @Override
    public String toString() {
        return "OrderBean{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * code : 1
     * data : {"addtime":1496995193,"appointment_time":"0","city":"北京市","ordernum":"149699519321567152","price":"48.7","start_add":"北京市丰台区新村街道南四环西路160号诺德中心1期","start_latitude":"39.829616272766145","start_longitude":"116.29915595054628","stop_add":"前门东大街9号","stop_latitude":"39.901537","stop_longitude":"116.404733","u_id":21,"u_type":"0"}
     * message : 成功
     */

    private int code;
    private int state;
    private DataBean data;
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * addtime : 1496995193
         * appointment_time : 0
         * city : 北京市
         * ordernum : 149699519321567152
         * price : 48.7
         * start_add : 北京市丰台区新村街道南四环西路160号诺德中心1期
         * start_latitude : 39.829616272766145
         * start_longitude : 116.29915595054628
         * stop_add : 前门东大街9号
         * stop_latitude : 39.901537
         * stop_longitude : 116.404733
         * u_id : 21
         * u_type : 0
         */
        private String distance;
        private int addtime;
        private String appointment_time;
        private String city;
        private String ordernum;
        private String price;
        private String start_add;
        private String start_latitude;
        private String start_longitude;
        private String stop_add;
        private String stop_latitude;
        private String stop_longitude;
        private int u_id;
        private String u_type;
        private int u_reason;
        private String reason;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setU_reason(int u_reason) {
            this.u_reason = u_reason;
        }

        public int getU_reason() {
            return u_reason;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDistance() {
            return distance;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getAppointment_time() {
            return appointment_time;
        }

        public void setAppointment_time(String appointment_time) {
            this.appointment_time = appointment_time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStart_add() {
            return start_add;
        }

        public void setStart_add(String start_add) {
            this.start_add = start_add;
        }

        public String getStart_latitude() {
            return start_latitude;
        }

        public void setStart_latitude(String start_latitude) {
            this.start_latitude = start_latitude;
        }

        public String getStart_longitude() {
            return start_longitude;
        }

        public void setStart_longitude(String start_longitude) {
            this.start_longitude = start_longitude;
        }

        public String getStop_add() {
            return stop_add;
        }

        public void setStop_add(String stop_add) {
            this.stop_add = stop_add;
        }

        public String getStop_latitude() {
            return stop_latitude;
        }

        public void setStop_latitude(String stop_latitude) {
            this.stop_latitude = stop_latitude;
        }

        public String getStop_longitude() {
            return stop_longitude;
        }

        public void setStop_longitude(String stop_longitude) {
            this.stop_longitude = stop_longitude;
        }

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
        }

        public String getU_type() {
            return u_type;
        }

        public void setU_type(String u_type) {
            this.u_type = u_type;
        }
    }
}
