package extrace.model;

import java.io.Serializable;

/**
 * Created by 黎明 on 2016/5/7.
 */
public class ExpressInfo implements Serializable{

        public String ID;//快递单号
        public String sname;//发件人姓名
        public String stel;//tel
        public String sadd;//省市区
        public String saddinfo;//街道
        public String rname;//收件人姓名
        public String rtel;
        public String radd;
        public String raddinfo;
        public String GetTime;
        public String OutTime;
        public double weight;
        public double TranFee;
        public double InsuFee;
        public String Acc1;
        public String Acc2;

        public ExpressInfo(){}

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getAcc1() {
            return Acc1;
        }

        public void setAcc1(String acc1) {
            Acc1 = acc1;
        }

        public String getGetTime() {
            return GetTime;
        }

        public void setGetTime(String getTime) {
            GetTime = getTime;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getAcc2() {
            return Acc2;
        }

        public void setAcc2(String acc2) {
            Acc2 = acc2;
        }

        public double getInsuFee() {
            return InsuFee;
        }

        public void setInsuFee(double insuFee) {
            InsuFee = insuFee;
        }

        public String getRadd() {
            return radd;
        }

        public void setRadd(String radd) {
            this.radd = radd;
        }

        public String getOutTime() {
            return OutTime;
        }

        public void setOutTime(String outTime) {
            OutTime = outTime;
        }

        public String getRaddinfo() {
            return raddinfo;
        }

        public void setRaddinfo(String raddinfo) {
            this.raddinfo = raddinfo;
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

        public double getTranFee() {
            return TranFee;
        }

        public void setTranFee(double tranFee) {
            TranFee = tranFee;
        }

    }
