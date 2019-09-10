/**
 * FileName: ProductERRepository
 * Author: xivin
 * Date: 2019-09-04 14:57
 * Description:
 */
package cn.duoduo.repository;

import cn.duoduo.vo.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

//@Component
public interface EsProductRepository {

    /*Page<EsProduct> findEsProductsByNameOrTitle(String name,String title,String keyWords,Pageable pageable);

    Page<EsProduct> findByTitleOrDescribe(String title, String describe, String keyWords, Pageable page);*/
}
