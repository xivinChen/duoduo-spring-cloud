package cn.duoduo.vo;

import java.io.Serializable;
import java.util.List;

public class OpenSearchPage<T> implements Serializable {

    private Long total;
    private Integer pageNum;
    private Integer pageSize;

    private Integer startRow;
    private List<T> list;

    public void countStartRow() {
        this.startRow=(pageNum-1)*pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
