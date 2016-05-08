package extrace.model;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nvpiao on 2016/5/3 0003.
 */

public class ExpresslogisticsInfo implements Serializable {
    private Date time;
    private String Info;
    private String State;

    public ExpresslogisticsInfo() {
    }

    public ExpresslogisticsInfo(Date time, String info, String state) {
        this.time = time;
        Info = info;
        State = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    @Override
    public String toString() {
        return "ExpresslogisticsInfo{" +
                "time=" + time +
                ", Info='" + Info + '\'' +
                ", State='" + State + '\'' +
                '}';
    }
}
