package cinyida.com.car_driver.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/5/2.
 */

public class NewsBean {

    private List<MessageBean> message;

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * b_amount : 11
         * b_bank : 12345698765432
         * b_details : 收到车费11元，来自手机尾号121的乘客支付，你可在我的余额中查看
         * b_id : 1
         * b_people : 2
         * b_time : 2017-05-11 11:17:14
         * b_type : 0
         * d_id : 1
         */

        private String b_amount;
        private String b_bank;
        private String b_details;
        private int b_id;
        private String b_people;
        private String b_time;
        private int b_type;
        private int d_id;

        public String getB_amount() {
            return b_amount;
        }

        public void setB_amount(String b_amount) {
            this.b_amount = b_amount;
        }

        public String getB_bank() {
            return b_bank;
        }

        public void setB_bank(String b_bank) {
            this.b_bank = b_bank;
        }

        public String getB_details() {
            return b_details;
        }

        public void setB_details(String b_details) {
            this.b_details = b_details;
        }

        public int getB_id() {
            return b_id;
        }

        public void setB_id(int b_id) {
            this.b_id = b_id;
        }

        public String getB_people() {
            return b_people;
        }

        public void setB_people(String b_people) {
            this.b_people = b_people;
        }

        public String getB_time() {
            return b_time;
        }

        public void setB_time(String b_time) {
            this.b_time = b_time;
        }

        public int getB_type() {
            return b_type;
        }

        public void setB_type(int b_type) {
            this.b_type = b_type;
        }

        public int getD_id() {
            return d_id;
        }

        public void setD_id(int d_id) {
            this.d_id = d_id;
        }
    }
}
