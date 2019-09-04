/**
 * FileName: ProductERRepository
 * Author: xivin
 * Date: 2019-09-04 14:57
 * Description:
 */
package cn.duoduo.repository;

import cn.duoduo.vo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductERRepository extends ElasticsearchRepository<Product,Integer> {

    //Page<Product> findByNameOrTitleOrDescribe(String name, String title, String describe, Pageable page);
}
