/**
 * FileName: QingTaoKeData
 * Author: xivin
 * Date: 2019-09-09 19:34
 * Description:
 */
package cn.duoduo.vo.qingtaoke;

import cn.duoduo.vo.EsGood;

import java.io.Serializable;
import java.util.List;

public class QingTaoKeData implements Serializable {

    private long total;
    private List<EsGood> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
