/**
 * FileName: EsGoodController
 * Author: xivin
 * Date: 2019-09-09 20:09
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsGoodService;
import cn.duoduo.vo.EsGood;
import cn.duoduo.vo.GoodCat;
import cn.duoduo.vo.PageFeign;
import cn.duoduo.vo.qingtaoke.QingTaoKeSearch;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/es_good")
public class EsGoodController {

    @Resource
    private EsGoodService esGoodService;

    @PostMapping("/")
    public int saveOne(@RequestBody EsGood esGood) {
        return esGoodService.saveOne(esGood);
    }

    @PostMapping("/all")
    public int saveAll(@RequestBody List<EsGood> list){

        return esGoodService.saveAll(list);
    }

    @GetMapping("/")
    public Object list() {
        return esGoodService.list();
    }

    @DeleteMapping("/all")
    public int deleteAll() {
        return this.esGoodService.deleteAll();
    }

    @PostMapping("/search")
    public PageFeign<EsGood> search(@RequestBody QingTaoKeSearch qingTaoKeSearch
            ,@RequestParam(value = "pageNum",defaultValue = "0") int pageNum
            ,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {

        Page<EsGood> search = this.esGoodService.search(qingTaoKeSearch,pageNum,pageSize);
        return new PageFeign<EsGood>(search);
    }

    @GetMapping("/cat/list")
    public List<GoodCat> findCatList() {
        return this.esGoodService.findCatList();
    }

    @GetMapping("/listByCat")
    public PageFeign<EsGood> listByCat(@RequestParam("goods_cat") Integer goods_cat
            ,@RequestParam(value = "page",defaultValue = "0") int page
            ,@RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {

        Page<EsGood> byCat = this.esGoodService.findByCat(goods_cat, page, pageSize);
        return new PageFeign<EsGood>(byCat);

    }

}
