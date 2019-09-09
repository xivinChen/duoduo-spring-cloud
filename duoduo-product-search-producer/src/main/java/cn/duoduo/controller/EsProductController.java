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
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
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

    @GetMapping("/search")
    public Page<EsProduct> search(EsProductSearch esProductSearch,
                                  @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="10") int pageSize) {

        return this.esProductService.search(esProductSearch,pageNum,pageSize);
    }

    @GetMapping("/simple_search")
    public Object simpleSearch(@RequestParam String keyWord,
                               @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value="pageSize",defaultValue="10") int pageSize) {
        return this.esProductService.simpleSearch(keyWord,pageNum,pageSize);
    }



}
