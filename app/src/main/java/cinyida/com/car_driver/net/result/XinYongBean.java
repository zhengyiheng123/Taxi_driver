package cinyida.com.car_driver.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/6/20.
 */

public class XinYongBean {

    private List<CreditBean> credit;

    public List<CreditBean> getCredit() {
        return credit;
    }

    public void setCredit(List<CreditBean> credit) {
        this.credit = credit;
    }

    public static class CreditBean {
        /**
         * c_id : 2
         * d_details : 在1497949374时您因取消订单而扣除20积分
         * d_id : 1
         * d_number : 20
         * d_time : 1497949374
         * d_type : 0
         */

        private int c_id;
        private String d_details;
        private int d_id;
        private String d_number;
        private int d_time;
        private int d_type;

        public int getC_id() {
            return c_id;
        }

        public void setC_id(int c_id) {
            this.c_id = c_id;
        }

        public String getD_details() {
            return d_details;
        }

        public void setD_details(String d_details) {
            this.d_details = d_details;
        }

        public int getD_id() {
            return d_id;
        }

        public void setD_id(int d_id) {
            this.d_id = d_id;
        }

        public String getD_number() {
            return d_number;
        }

        public void setD_number(String d_number) {
            this.d_number = d_number;
        }

        public int getD_time() {
            return d_time;
        }

        public void setD_time(int d_time) {
            this.d_time = d_time;
        }

        public int getD_type() {
            return d_type;
        }

        public void setD_type(int d_type) {
            this.d_type = d_type;
        }
    }
}
