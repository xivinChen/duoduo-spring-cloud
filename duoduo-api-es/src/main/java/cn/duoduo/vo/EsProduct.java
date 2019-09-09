/**
 * FileName: Product
 * Author: xivin
 * Date: 2019-09-04 14:49
 * Description:
 */
package cn.duoduo.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;

@Document(indexName = "product",type = "product",shards = 1,replicas = 0)
public class EsProduct implements Serializable {

    @Id
    private int id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String title;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String describe;
    private int categoryId;
    private int brandId;
    private int status;
    private String image;
    private double price;
    @ApiModelProperty(hidden = true)
    private Timestamp createdTime;
    @ApiModelProperty(hidden = true)
    private Timestamp updatedTime;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }
}
