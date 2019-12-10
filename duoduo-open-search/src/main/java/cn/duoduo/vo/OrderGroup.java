package cn.duoduo.vo;

import java.sql.Timestamp;
import java.util.Date;

public class OrderGroup {
    private int id;

    private String name;

    private int end_profit_ratio;

    private int is_end;

    private int end_type;

    private int state;

    private Timestamp create_at;

    private Timestamp updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnd_profit_ratio() {
        return end_profit_ratio;
    }

    public void setEnd_profit_ratio(int end_profit_ratio) {
        this.end_profit_ratio = end_profit_ratio;
    }

    public int getIs_end() {
        return is_end;
    }

    public void setIs_end(int is_end) {
        this.is_end = is_end;
    }

    public int getEnd_type() {
        return end_type;
    }

    public void setEnd_type(int end_type) {
        this.end_type = end_type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}