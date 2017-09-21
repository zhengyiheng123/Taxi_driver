package cinyida.com.car_driver.net.result;

/**
 * Created by Zheng on 2017/9/21.
 */

public class HomeAddress {

    /**
     * id :
     * d_id :
     * d_close :
     * longitude :
     * latitude :
     */

    private String id;
    private String d_id;
    private String d_close;
    private String longitude;
    private String latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getD_close() {
        return d_close;
    }

    public void setD_close(String d_close) {
        this.d_close = d_close;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
