package cinyida.com.car_driver.net.result;

/**
 * 这一类的entity 的定义就直接的放在一个包里面就行了
 *
 * Created by anylife.zlb@gmail.com on 2016/7/11.
 */
public class LoginResult {


    private int id;
    private String drivername;
    private String password;
    private String name;
    private String sex;
    private String bank;
    private String image;
    private int state;
    private String addtime;
    private String carnum;
    private String phonenum;
    private String company;
    private String managerphone;
    private String allmoney;
    private int allorder;
    private int goodrate;
    private int good;
    private int carstate;
    private String comment;
    private String api_key;
    private String last_login;
    private int d_reputation;
    private int credit;
    private String balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getManagerphone() {
        return managerphone;
    }

    public void setManagerphone(String managerphone) {
        this.managerphone = managerphone;
    }

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

    public int getGoodrate() {
        return goodrate;
    }

    public void setGoodrate(int goodrate) {
        this.goodrate = goodrate;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getCarstate() {
        return carstate;
    }

    public void setCarstate(int carstate) {
        this.carstate = carstate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public int getD_reputation() {
        return d_reputation;
    }

    public void setD_reputation(int d_reputation) {
        this.d_reputation = d_reputation;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "id=" + id +
                ", drivername='" + drivername + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", bank='" + bank + '\'' +
                ", image='" + image + '\'' +
                ", state=" + state +
                ", addtime='" + addtime + '\'' +
                ", carnum='" + carnum + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", company='" + company + '\'' +
                ", managerphone='" + managerphone + '\'' +
                ", allmoney='" + allmoney + '\'' +
                ", allorder=" + allorder +
                ", goodrate=" + goodrate +
                ", good=" + good +
                ", carstate=" + carstate +
                ", comment='" + comment + '\'' +
                ", api_key='" + api_key + '\'' +
                ", last_login='" + last_login + '\'' +
                ", d_reputation=" + d_reputation +
                ", credit=" + credit +
                ", balance='" + balance + '\'' +
                '}';
    }
}
