package com.expressba.express.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by songchao on 16/4/24.
 * 用户地址model
 */
public class UserAddress implements Parcelable{
    public UserAddress(){

    }

    protected UserAddress(Parcel in) {
        rank = in.readInt();
        aid = in.readInt();
        customerid = in.readInt();
        provinceid = in.readInt();
        cityid = in.readInt();
        regionid = in.readInt();

        province = in.readString();
        city = in.readString();
        region = in.readString();
        address = in.readString();
        name = in.readString();
        telephone = in.readString();
    }

    public static final Creator<UserAddress> CREATOR = new Creator<UserAddress>() {
        @Override
        public UserAddress createFromParcel(Parcel in) {
            return new UserAddress(in);
        }

        @Override
        public UserAddress[] newArray(int size) {
            return new UserAddress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeInt(aid);
        dest.writeInt(customerid);
        dest.writeInt(provinceid);
        dest.writeInt(cityid);
        dest.writeInt(regionid);

        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(region);
        dest.writeString(address);
        dest.writeString(name);
        dest.writeString(telephone);
    }

    private Integer rank;
    private Integer aid;
    private Integer customerid;

    private Integer provinceid;
    private Integer cityid;
    private Integer regionid;

    private String province;
    private String city;
    private String region;
    private String address;
    private String name;
    private String telephone;


    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }
}
