package extrace.model;


import java.io.Serializable;

/**
 * Created by 黎明 on 2016/4/20.
 */
public class ExpressInfo implements Serializable {
    private String ID;//快递单号
    private String sname;//发件人姓名
    private String stel;//tel
    private String sadd;//省市区
    private String saddinfo;//街道
    private String rname;//收件人姓名
    private String rtel;
    private String radd;
    private String raddinfo;
    private String GetTime;
    private String OutTime;
    private double weight;
    private double TranFee;
    private double InsuFee;
    private String Acc1;
    private String Acc2;

    public ExpressInfo(){}

    public ExpressInfo(String ID, String sname, String stel, String sadd, String saddinfo, String rname, String rtel, String radd, String raddinfo, String getTime, String outTime, double weight, double tranFee, double insuFee, String acc1, String acc2) {
        this.ID = ID;
        this.sname = sname;
        this.stel = stel;
        this.sadd = sadd;
        this.saddinfo = saddinfo;
        this.rname = rname;
        this.rtel = rtel;
        this.radd = radd;
        this.raddinfo = raddinfo;
        GetTime = getTime;
        OutTime = outTime;
        this.weight = weight;
        TranFee = tranFee;
        InsuFee = insuFee;
        Acc1 = acc1;
        Acc2 = acc2;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStel() {
        return stel;
    }

    public void setStel(String stel) {
        this.stel = stel;
    }

    public String getSadd() {
        return sadd;
    }

    public void setSadd(String sadd) {
        this.sadd = sadd;
    }

    public String getSaddinfo() {
        return saddinfo;
    }

    public void setSaddinfo(String saddinfo) {
        this.saddinfo = saddinfo;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRtel() {
        return rtel;
    }

    public void setRtel(String rtel) {
        this.rtel = rtel;
    }

    public String getRadd() {
        return radd;
    }

    public void setRadd(String radd) {
        this.radd = radd;
    }

    public String getRaddinfo() {
        return raddinfo;
    }

    public void setRaddinfo(String raddinfo) {
        this.raddinfo = raddinfo;
    }

    public String getGetTime() {
        return GetTime;
    }

    public void setGetTime(String getTime) {
        GetTime = getTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTranFee() {
        return TranFee;
    }

    public void setTranFee(double tranFee) {
        TranFee = tranFee;
    }

    public double getInsuFee() {
        return InsuFee;
    }

    public void setInsuFee(double insuFee) {
        InsuFee = insuFee;
    }

    public String getAcc1() {
        return Acc1;
    }

    public void setAcc1(String acc1) {
        Acc1 = acc1;
    }

    public String getAcc2() {
        return Acc2;
    }

    public void setAcc2(String acc2) {
        Acc2 = acc2;
    }

    @Override
    public String toString() {
        return "ExpressInfo{" +
                "ID='" + ID + '\'' +
                ", sname='" + sname + '\'' +
                ", stel='" + stel + '\'' +
                ", sadd='" + sadd + '\'' +
                ", saddinfo='" + saddinfo + '\'' +
                ", rname='" + rname + '\'' +
                ", rtel='" + rtel + '\'' +
                ", radd='" + radd + '\'' +
                ", raddinfo='" + raddinfo + '\'' +
                ", GetTime='" + GetTime + '\'' +
                ", OutTime='" + OutTime + '\'' +
                ", weight=" + weight +
                ", TranFee=" + TranFee +
                ", InsuFee=" + InsuFee +
                ", Acc1='" + Acc1 + '\'' +
                ", Acc2='" + Acc2 + '\'' +
                '}';
    }
}
