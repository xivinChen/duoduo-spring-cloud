/**
 * FileName: EsProductClientService
 * Author: xivin
 * Date: 2019-09-06 17:09
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.config.FeignClientConfig;
import cn.duoduo.service.fallback.EsProductClientServiceFallBack;
import cn.duoduo.vo.EsProduct;
import cn.duoduo.vo.EsProductSearch;
import cn.duoduo.vo.PageFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "duoduo-product-search-producer",configuration = FeignClientConfig.class,fallbackFactory = EsProductClientServiceFallBack.class)
public interface EsProductClientService {

    @PostMapping("/es_product/")
    int save(EsProduct esProduct);

    @GetMapping("/es_product/")
    Object list();

    @GetMapping("/es_product/simple_search/")
    Page simpleSearch(@RequestParam("keyWord") String keyWord,@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize);

    @PostMapping("/es_product/search/")
    PageFeign<EsProduct> search(@RequestBody EsProductSearch esProductSearch,
                                  @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="10") int pageSize);

    @PostMapping("/es_product/test_search/")
    PageFeign<EsProduct> testSearch();

}
