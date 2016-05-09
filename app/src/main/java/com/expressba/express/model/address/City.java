package com.expressba.express.model.address;

/**
 * Created by songchao on 16/4/25.
 * 用户地址城市类
 */
public class City {
    private Integer cid;
    private Integer pid;
    private String cname;
    private String code;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
