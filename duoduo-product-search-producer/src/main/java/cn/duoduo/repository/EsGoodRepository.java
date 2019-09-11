/**
 * FileName: EsGoodRepository
 * Author: xivin
 * Date: 2019-09-09 19:59
 * Description:
 */
package cn.duoduo.repository;

import cn.duoduo.vo.EsGood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EsGoodRepository extends ElasticsearchRepository<EsGood,String> {

    //Page<EsGood> findByGoods_cat(Integer goods_cat, Pageable page);

}
