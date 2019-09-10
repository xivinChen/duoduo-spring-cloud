/**
 * FileName: EsGood
 * Author: xivin
 * Date: 2019-09-09 19:02
 * Description:
 */
package cn.duoduo.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;

@Document(indexName = "goods",type = "goods",shards = 1,replicas = 0)
public class EsGood implements Serializable {

    @Id
    private String goods_id;
    private String goods_pic;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String goods_title;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String goods_short_title;
    private int goods_cat;
    private float goods_price;
    private long goods_sales;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String goods_introduce;
    private int is_tmall;
    private float commission;
    private int commission_type;
    private int commission_link;
    private int coupon_is_check;
    private int coupon_type;
    private String seller_id;
    private String coupon_id;
    private float coupon_price;
    private long coupon_number;
    private int coupon_limit;
    private int coupon_over;
    private float coupon_condition;
    private Timestamp coupon_start_time;
    private Timestamp coupon_end_time;
    private int is_ju;
    private int is_tqg;
    private int is_ali;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_short_title() {
        return goods_short_title;
    }

    public void setGoods_short_title(String goods_short_title) {
        this.goods_short_title = goods_short_title;
    }

    public int getGoods_cat() {
        return goods_cat;
    }

    public void setGoods_cat(int goods_cat) {
        this.goods_cat = goods_cat;
    }

    public float getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public long getGoods_sales() {
        return goods_sales;
    }

    public void setGoods_sales(long goods_sales) {
        this.goods_sales = goods_sales;
    }

    public String getGoods_introduce() {
        return goods_introduce;
    }

    public void setGoods_introduce(String goods_introduce) {
        this.goods_introduce = goods_introduce;
    }

    public int getIs_tmall() {
        return is_tmall;
    }

    public void setIs_tmall(int is_tmall) {
        this.is_tmall = is_tmall;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public int getCommission_type() {
        return commission_type;
    }

    public void setCommission_type(int commission_type) {
        this.commission_type = commission_type;
    }

    public int getCommission_link() {
        return commission_link;
    }

    public void setCommission_link(int commission_link) {
        this.commission_link = commission_link;
    }

    public int getCoupon_is_check() {
        return coupon_is_check;
    }

    public void setCoupon_is_check(int coupon_is_check) {
        this.coupon_is_check = coupon_is_check;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public float getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(float coupon_price) {
        this.coupon_price = coupon_price;
    }

    public long getCoupon_number() {
        return coupon_number;
    }

    public void setCoupon_number(long coupon_number) {
        this.coupon_number = coupon_number;
    }

    public int getCoupon_limit() {
        return coupon_limit;
    }

    public void setCoupon_limit(int coupon_limit) {
        this.coupon_limit = coupon_limit;
    }

    public int getCoupon_over() {
        return coupon_over;
    }

    public void setCoupon_over(int coupon_over) {
        this.coupon_over = coupon_over;
    }

    public float getCoupon_condition() {
        return coupon_condition;
    }

    public void setCoupon_condition(float coupon_condition) {
        this.coupon_condition = coupon_condition;
    }

    public Timestamp getCoupon_start_time() {
        return coupon_start_time;
    }

    public void setCoupon_start_time(Timestamp coupon_start_time) {
        this.coupon_start_time = coupon_start_time;
    }

    public Timestamp getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(Timestamp coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }

    public int getIs_ju() {
        return is_ju;
    }

    public void setIs_ju(int is_ju) {
        this.is_ju = is_ju;
    }

    public int getIs_tqg() {
        return is_tqg;
    }

    public void setIs_tqg(int is_tqg) {
        this.is_tqg = is_tqg;
    }

    public int getIs_ali() {
        return is_ali;
    }

    public void setIs_ali(int is_ali) {
        this.is_ali = is_ali;
    }
}
