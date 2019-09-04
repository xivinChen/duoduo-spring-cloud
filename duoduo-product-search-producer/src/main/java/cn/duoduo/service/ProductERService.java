/**
 * FileName: ProductERService
 * Author: xivin
 * Date: 2019-09-04 14:48
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.vo.Product;

public interface ProductERService {

    int save(Product product);
    int delete(int id);
    int update(Product product);

    int simpleSearch(String keyWord,int pageNum,int pageSize);
    int search(String keyWord,int categoryId,int sortId,int pageNum,int pageSize);

    Object list();
}
