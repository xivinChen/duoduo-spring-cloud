/**
 * FileName: EsGoodclientService
 * Author: xivin
 * Date: 2019-09-09 20:14
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.config.FeignClientConfig;
import cn.duoduo.service.fallback.EsGoodClientServiceFallBack;
import cn.duoduo.vo.EsGood;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "duoduo-product-search-producer",configuration = FeignClientConfig.class,fallbackFactory = EsGoodClientServiceFallBack.class)
public interface EsGoodClientService {

    @PostMapping("/es_good/")
    public int saveOne(@RequestBody EsGood esGood);

    @PostMapping("/es_good/all")
    public int saveAll(@RequestBody List<EsGood> list);

    @GetMapping("/es_good/")
    public Object list();
}
