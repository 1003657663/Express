package com.expressba.express.model.address;

/**
 * Created by songchao on 16/4/25.
 * 用户地址区域类
 */
public class Region {
    private String area;
    private Integer cityId;
    private Integer id;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
