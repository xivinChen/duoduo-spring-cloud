/**
 * FileName: EsProductSearch
 * Author: xivin
 * Date: 2019-09-06 16:46
 * Description:
 */
package cn.duoduo.vo;

public class EsProductSearch {

    private String keyWord;
    private Integer categoryId;
    private Integer sortId;
    private Integer priceId;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }
}
