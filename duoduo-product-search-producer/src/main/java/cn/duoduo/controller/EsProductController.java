/**
 * FileName: ProductERController
 * Author: xivin
 * Date: 2019-09-04 14:45
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsProductService;
import cn.duoduo.service.UserClientService;
import cn.duoduo.vo.EsProduct;
import cn.duoduo.vo.EsProductSearch;
import cn.duoduo.vo.PageFeign;
import cn.duoduo.vo.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/es_product")
@ApiOperation(value = "商品搜索相关接口",tags = "product 商品搜索相关接口")
public class EsProductController {

    @Resource
    private UserClientService userClientService;
    @Resource
    private EsProductService esProductService;


    @GetMapping("/")
    public Object list() {
        return esProductService.list();
    }

    @PostMapping("/")
    public int save(@RequestBody EsProduct esProduct) {
        return this.esProductService.save(esProduct);
    }

    @PostMapping("/search")
    public Page<EsProduct> search(@RequestBody EsProductSearch esProductSearch,
                                  @RequestParam(value="pageNum",defaultValue="0") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="10") int pageSize) {

        Page<EsProduct> search = this.esProductService.search(esProductSearch, pageNum, pageSize);
        return search;
    }

    @PostMapping("/test_search")
    public PageFeign<EsProduct> testSearch() {
        EsProductSearch esProductSearch=new EsProductSearch();
        Page<EsProduct> search = this.esProductService.search(esProductSearch, 1, 10);
        PageFeign<EsProduct> pageFeign=new PageFeign<>();
        pageFeign.setContent(search.getContent());
        pageFeign.setLast(search.isLast());
        pageFeign.setSize(search.getSize());
        pageFeign.setTotalElements(search.getNumberOfElements());
        pageFeign.setTotalPages(search.getTotalPages());
        pageFeign.setNumber(search.getNumber());
        return pageFeign;
    }

    @GetMapping("/simple_search")
    public Object simpleSearch(@RequestParam String keyWord,
                               @RequestParam(value="pageNum",defaultValue="0") int pageNum,
                               @RequestParam(value="pageSize",defaultValue="10") int pageSize) {
        return this.esProductService.simpleSearch(keyWord,pageNum,pageSize);
    }



}
