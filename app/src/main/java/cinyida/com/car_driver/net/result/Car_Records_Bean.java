package cinyida.com.car_driver.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/6/5.
 */

public class Car_Records_Bean {

    /**
     * driver : {"allorder":107,"d_reputation":80}
     * Record : [{"ordernum":"114611494920133","start_add":"碘化物","stop_add":"而为过渡期·","addtime":2017,"state":7},{"ordernum":"708011494930609","start_add":"热情","stop_add":"ewqere","addtime":0,"state":5},{"ordernum":"149562300221843811","start_add":"诺德中心1期","stop_add":"国瑞购物中心","addtime":1495623002,"state":6}]
     */

    private DriverBean driver;
    private List<RecordBean> Record;

    public DriverBean getDriver() {
        return driver;
    }

    public void setDriver(DriverBean driver) {
        this.driver = driver;
    }

    public List<RecordBean> getRecord() {
        return Record;
    }

    public void setRecord(List<RecordBean> Record) {
        this.Record = Record;
    }

    public static class DriverBean {
        /**
         * allorder : 107
         * d_reputation : 80
         */

        private int allorder;
        private int d_reputation;

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
    }

    public static class RecordBean {
        /**
         * ordernum : 114611494920133
         * start_add : 碘化物
         * stop_add : 而为过渡期·
         * addtime : 2017
         * state : 7
         */

        private String ordernum;
        private String start_add;
        private String stop_add;
        private int addtime;
        private int state;
        private int u_type;

        public int getU_type() {
            return u_type;
        }

        public void setU_type(int u_type) {
            this.u_type = u_type;
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

        public String getStop_add() {
            return stop_add;
        }

        public void setStop_add(String stop_add) {
            this.stop_add = stop_add;
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
    }
}
