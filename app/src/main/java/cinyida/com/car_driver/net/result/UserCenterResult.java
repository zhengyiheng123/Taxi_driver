package cinyida.com.car_driver.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/6/2.
 */

public class UserCenterResult {


    private DriverBean driver;
    private List<AppointmentBean> appointment;
    private List<OrderBean> order;

    public DriverBean getDriver() {
        return driver;
    }

    public void setDriver(DriverBean driver) {
        this.driver = driver;
    }

    public List<AppointmentBean> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<AppointmentBean> appointment) {
        this.appointment = appointment;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public static class DriverBean {
        /**
         * allmoney : 0.00
         * allorder : 117
         * d_reputation : 43
         * goodrate : 2%
         * image : uploads/20170615/e77a27b41c322c070fca94b164e51434.jpeg
         * name : zhangda
         * paiming : 1
         * state : 2
         */

        private String allmoney;
        private int allorder;
        private int d_reputation;
        private String goodrate;
        private String image;
        private String name;
        private int paiming;
        private int state;

        public String getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(String allmoney) {
            this.allmoney = allmoney;
        }

        public int getAllorder() {
            return allorder;
        }

        public void setAllorder(int allorder) {
            this.allorder = allorder;
        }

        public int getD_reputation() {
            return d_reputation;
        }

        public void setD_reputation(int d_reputation) {
            this.d_reputation = d_reputation;
        }

        public String getGoodrate() {
            return goodrate;
        }

        public void setGoodrate(String goodrate) {
            this.goodrate = goodrate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPaiming() {
            return paiming;
        }

        public void setPaiming(int paiming) {
            this.paiming = paiming;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    public static class AppointmentBean implements Serializable {
        /**
         * addtime : 1497429162
         * nickname : u
         * ordernum : 149742916232980076
         * start_add : 北京市丰台区南四环西路靠近诺德中心1期
         * state : 2
         * stop_add : 八达岭长城
         * u_type : 1
         */
        private String phonenum;
        private String appointment_time;
        private int addtime;
        private String nickname;
        private String ordernum;
        private String start_add;
        private int state;
        private String stop_add;
        private int u_type;

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public String getPhonenum() {
            return phonenum;
        }

        public void setAppointment_time(String appointment_time) {
            this.appointment_time = appointment_time;
        }

        public String getAppointment_time() {
            return appointment_time;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStop_add() {
            return stop_add;
        }

        public void setStop_add(String stop_add) {
            this.stop_add = stop_add;
        }

        public int getU_type() {
            return u_type;
        }

        public void setU_type(int u_type) {
            this.u_type = u_type;
        }
    }

    public static class OrderBean {
        /**
         * addtime : 1497518685
         * nickname : u
         * ordernum : 149751868532873420
         * start_add : 北京市丰台区南四环西路靠近诺德中心1期
         * state : 6
         * stop_add : 天坛公园
         * u_type : 0
         */

        private int addtime;
        private String nickname;
        private String ordernum;
        private String start_add;
        private int state;
        private String stop_add;
        private int u_type;

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStop_add() {
            return stop_add;
        }

        public void setStop_add(String stop_add) {
            this.stop_add = stop_add;
        }

        public int getU_type() {
            return u_type;
        }

        public void setU_type(int u_type) {
            this.u_type = u_type;
        }
    }
}
