package cn.duoduo.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;
import java.util.Date;

public class Order {
    private int id;

    @JSONField(name = "order_group_id")
    private int orderGroupId;

    private int coin_pair_choice_id;

    private int trade_average_price;

    @JSONField(name = "trade_number")
    private int tradeNumber;

    private int trade_cost;

    private int trade_type;

    private int statue;

    private long created_at;

    private Timestamp updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(int orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public int getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(int tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public int getCoin_pair_choice_id() {
        return coin_pair_choice_id;
    }

    public void setCoin_pair_choice_id(int coin_pair_choice_id) {
        this.coin_pair_choice_id = coin_pair_choice_id;
    }

    public int getTrade_average_price() {
        return trade_average_price;
    }

    public void setTrade_average_price(int trade_average_price) {
        this.trade_average_price = trade_average_price;
    }


    public int getTrade_cost() {
        return trade_cost;
    }

    public void setTrade_cost(int trade_cost) {
        this.trade_cost = trade_cost;
    }

    public int getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(int trade_type) {
        this.trade_type = trade_type;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    /*public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }*/

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}