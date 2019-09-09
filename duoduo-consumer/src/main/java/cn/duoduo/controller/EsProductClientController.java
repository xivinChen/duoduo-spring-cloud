/**
 * FileName: EsProductClientController
 * Author: xivin
 * Date: 2019-09-06 17:08
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsProductClientService;
import cn.duoduo.vo.EsProduct;
import cn.duoduo.vo.EsProductSearch;
import cn.duoduo.vo.PageFeign;
import cn.duoduo.vo.result.Result;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("es_product")
public class EsProductClientController {

    @Resource
    private EsProductClientService esProductClientService;

    @PostMapping("/")
    public int save(EsProduct esProduct) {
        return this.esProductClientService.save(esProduct);
    }

    @GetMapping("/")
    public Object list() {
        return this.esProductClientService.list();
    }

    @GetMapping("/simple_search")
    public Page simpleSearch(@RequestParam String keyWord,
                             @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                             @RequestParam(value="pageSize",defaultValue="10") int pageSize) {
        return this.esProductClientService.simpleSearch(keyWord,pageNum,pageSize);
    }

    @PostMapping("/search")
    public PageFeign<EsProduct> search(EsProductSearch esProductSearch,
                                  @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="10") int pageSize) {

        PageFeign<EsProduct> search = this.esProductClientService.search(esProductSearch, pageNum, pageSize);
        return search;
    }


    @PostMapping("/test_search")
    public PageFeign<EsProduct> testSearch() {
        return this.esProductClientService.testSearch();
    }
}
