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
import cn.duoduo.vo.PageFeign;
import cn.duoduo.vo.qingtaoke.QingTaoKeSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "duoduo-product-search-producer",configuration = FeignClientConfig.class,fallbackFactory = EsGoodClientServiceFallBack.class)
public interface EsGoodClientService {

    @PostMapping("/es_good/")
    public int saveOne(@RequestBody EsGood esGood);

    @PostMapping("/es_good/all")
    public int saveAll(@RequestBody List<EsGood> list);

    @GetMapping("/es_good/")
    public Object list();

    @PostMapping("/es_good/search/")
    PageFeign<EsGood> search(@RequestBody QingTaoKeSearch qingTaoKeSearch,
                             @RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize);

    @GetMapping("/es_good/cat/list")
    List getCatsList();

    @GetMapping("/es_good/listByCat")
    PageFeign<EsGood> listByCat(@RequestParam("goods_cat") Integer goods_cat
            ,@RequestParam(value = "page",defaultValue = "0") int page
            ,@RequestParam(value = "pageSize",defaultValue = "20") int pageSize);
}
