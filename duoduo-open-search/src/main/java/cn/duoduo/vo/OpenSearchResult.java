package cn.duoduo.vo;

import java.util.List;

public class OpenSearchResult {

    private Long total;
    private List<Order> items;
    private String error;
    private String status;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OpenSearchResult{" +
                "total=" + total +
                ", items=" + items +
                ", error='" + error + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
