/**
 * FileName: EsProductClientService
 * Author: xivin
 * Date: 2019-09-06 17:09
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.config.FeignClientConfig;
import cn.duoduo.service.fallback.EsProductClientServiceFallBack;
import cn.duoduo.service.fallback.UserClientServiceFallback;
import cn.duoduo.vo.EsProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "duoduo-product-search-producer",configuration = FeignClientConfig.class,fallbackFactory = EsProductClientServiceFallBack.class)
public interface EsProductClientService {

    @PostMapping("/es_product")
    int save(EsProduct esProduct);
}
