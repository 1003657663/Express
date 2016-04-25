package extrace.model.address;

/**
 * Created by songchao on 16/4/25.
 * 用户地址城市类
 */
public class City {
    private Integer cid;
    private Integer pid;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
