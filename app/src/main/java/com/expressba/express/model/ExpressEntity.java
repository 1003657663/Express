package com.expressba.express.model;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by violet on 2016/4/11.
 */
public class ExpressEntity implements Serializable {
    private String id;
    private int customerId;
    private Integer isPackage;
    private Float weight;
    private int sendAddressId;
    private int accAddressId;
    private String getTime;
    private String outTime;
    private Float tranFee;
    private Float insuFee;
    private String acc1;
    private String acc2;

    public ExpressEntity() {
    }

    public ExpressEntity(String id, int customerId, Integer isPackage, Float weight, int sendAddressId, int accAddressId, String getTime, String outTime, Float tranFee, Float insuFee, String acc1, String acc2) {
        this.id = id;
        this.customerId = customerId;
        this.isPackage = isPackage;
        this.weight = weight;
        this.sendAddressId = sendAddressId;
        this.accAddressId = accAddressId;
        this.getTime = getTime;
        this.outTime = outTime;
        this.tranFee = tranFee;
        this.insuFee = insuFee;
        this.acc1 = acc1;
        this.acc2 = acc2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(Integer isPackage) {
        this.isPackage = isPackage;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public int getSendAddressId() {
        return sendAddressId;
    }

    public void setSendAddressId(int sendAddressId) {
        this.sendAddressId = sendAddressId;
    }

    public int getAccAddressId() {
        return accAddressId;
    }

    public void setAccAddressId(int accAddressId) {
        this.accAddressId = accAddressId;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public Float getTranFee() {
        return tranFee;
    }

    public void setTranFee(Float tranFee) {
        this.tranFee = tranFee;
    }

    public Float getInsuFee() {
        return insuFee;
    }

    public void setInsuFee(Float insuFee) {
        this.insuFee = insuFee;
    }

    public String getAcc1() {
        return acc1;
    }

    public void setAcc1(String acc1) {
        this.acc1 = acc1;
    }

    public String getAcc2() {
        return acc2;
    }

    public void setAcc2(String acc2) {
        this.acc2 = acc2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpressEntity that = (ExpressEntity) o;

        if (customerId != that.customerId) return false;
        if (sendAddressId != that.sendAddressId) return false;
        if (accAddressId != that.accAddressId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isPackage != null ? !isPackage.equals(that.isPackage) : that.isPackage != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (getTime != null ? !getTime.equals(that.getTime) : that.getTime != null) return false;
        if (outTime != null ? !outTime.equals(that.outTime) : that.outTime != null) return false;
        if (tranFee != null ? !tranFee.equals(that.tranFee) : that.tranFee != null) return false;
        if (insuFee != null ? !insuFee.equals(that.insuFee) : that.insuFee != null) return false;
        if (acc1 != null ? !acc1.equals(that.acc1) : that.acc1 != null) return false;
        if (acc2 != null ? !acc2.equals(that.acc2) : that.acc2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + customerId;
        result = 31 * result + (isPackage != null ? isPackage.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + sendAddressId;
        result = 31 * result + accAddressId;
        result = 31 * result + (getTime != null ? getTime.hashCode() : 0);
        result = 31 * result + (outTime != null ? outTime.hashCode() : 0);
        result = 31 * result + (tranFee != null ? tranFee.hashCode() : 0);
        result = 31 * result + (insuFee != null ? insuFee.hashCode() : 0);
        result = 31 * result + (acc1 != null ? acc1.hashCode() : 0);
        result = 31 * result + (acc2 != null ? acc2.hashCode() : 0);
        return result;
    }
}
