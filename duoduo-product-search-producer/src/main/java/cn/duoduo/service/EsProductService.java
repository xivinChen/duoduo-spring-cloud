/**
 * FileName: EsProductERService
 * Author: xivin
 * Date: 2019-09-04 14:48
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.vo.EsProduct;
import cn.duoduo.vo.EsProductSearch;
import org.springframework.data.domain.Page;

public interface EsProductService {


    int save(EsProduct esProduct);
    int delete(int id);
    int update(EsProduct esProduct);

    Page<EsProduct> simpleSearch(String keyWord, int pageNum, int pageSize);
    Page<EsProduct> search(EsProductSearch esProductSearch, int pageNum, int pageSize);

    Object list();

}
