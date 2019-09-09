/**
 * FileName: QingTaoKeResult
 * Author: xivin
 * Date: 2019-09-09 19:10
 * Description:
 */
package cn.duoduo.vo;

import java.io.Serializable;
import java.util.List;

public class QingTaoKeResult implements Serializable {

    private int er_code;
    private String er_msg;
    private QingTaoKeData data;

    public int getEr_code() {
        return er_code;
    }

    public void setEr_code(int er_code) {
        this.er_code = er_code;
    }

    public String getEr_msg() {
        return er_msg;
    }

    public void setEr_msg(String er_msg) {
        this.er_msg = er_msg;
    }

    public QingTaoKeData getData() {
        return data;
    }

    public void setData(QingTaoKeData data) {
        this.data = data;
    }
}
