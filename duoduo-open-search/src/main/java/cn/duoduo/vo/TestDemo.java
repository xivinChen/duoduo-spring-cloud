package cn.duoduo.vo;

import java.io.Serializable;

public class TestDemo implements Serializable {

    private int id;
    private String name;
    private String phone;
    private int[] int_arr;
    private float[] float_arr;
    private String[] literal_arr;
    private int cate_id;
    //private String cmd;


    /*public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }*/

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int[] getInt_arr() {
        return int_arr;
    }

    public void setInt_arr(int[] int_arr) {
        this.int_arr = int_arr;
    }

    public float[] getFloat_arr() {
        return float_arr;
    }

    public void setFloat_arr(float[] float_arr) {
        this.float_arr = float_arr;
    }

    public String[] getLiteral_arr() {
        return literal_arr;
    }

    public void setLiteral_arr(String[] literal_arr) {
        this.literal_arr = literal_arr;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }
}
