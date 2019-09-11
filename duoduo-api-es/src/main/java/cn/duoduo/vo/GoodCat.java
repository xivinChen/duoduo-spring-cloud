/**
 * FileName: GoodCat
 * Author: xivin
 * Date: 2019-09-11 11:04
 * Description:
 */
package cn.duoduo.vo;

import java.io.Serializable;

public class GoodCat implements Serializable {

    private Integer id;
    private Long number;
    private String name;

    public GoodCat() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
