package cinyida.com.car_driver.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/6/5.
 */

public class Driver_Bean {

    /**
     * biaoqian : ["司机友好","自带导航","车辆整洁"]
     * driver : {"allorder":160,"company":"星艺达出租车公司","image":"uploads/20170620/3cd65d4b4c24a23f09792de636308a3e.jpg","name":"赵小磊","paiming":2,"phonenum":"15538869641","star":5}
     */

    private DriverBean driver;
    private List<String> biaoqian;

    public DriverBean getDriver() {
        return driver;
    }

    public void setDriver(DriverBean driver) {
        this.driver = driver;
    }

    public List<String> getBiaoqian() {
        return biaoqian;
    }

    public void setBiaoqian(List<String> biaoqian) {
        this.biaoqian = biaoqian;
    }

    public static class DriverBean {
        /**
         * allorder : 160
         * company : 星艺达出租车公司
         * image : uploads/20170620/3cd65d4b4c24a23f09792de636308a3e.jpg
         * name : 赵小磊
         * paiming : 2
         * phonenum : 15538869641
         * star : 5
         */

        private int allorder;
        private String company;
        private String image;
        private String name;
        private int paiming;
        private String phonenum;
        private int star;

        public int getAllorder() {
            return allorder;
        }

        public void setAllorder(int allorder) {
            this.allorder = allorder;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
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

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }
    }
}
