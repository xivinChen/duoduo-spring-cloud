/**
 * FileName: FeignPage
 * Author: xivin
 * Date: 2019-09-09 16:47
 * Description:
 */
package cn.duoduo.vo;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageFeign<T> implements Serializable {

    private List<T> content = new ArrayList<>();
    private boolean last;
    private int totalPages;
    private int totalElements;
    private int numberOfElements;
    private int size;
    private int number;



    public PageFeign() {
    }

    public PageFeign(List<T> content, boolean last, int totalPages, int totalElements, int numberOfElements, int size, int number) {
        this.content = content;
        this.last = last;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.numberOfElements = numberOfElements;
        this.size = size;
        this.number = number;
    }

    public PageFeign(Page<T> page) {
        this.content=page.getContent();
        this.last=page.isLast();
        this.totalPages=page.getTotalPages();
        this.totalElements=page.getNumberOfElements();
        this.numberOfElements=page.getNumberOfElements();
        this.size=page.getSize();
        this.number=page.getNumber();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
