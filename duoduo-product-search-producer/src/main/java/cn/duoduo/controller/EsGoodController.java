/**
 * FileName: EsGoodController
 * Author: xivin
 * Date: 2019-09-09 20:09
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsGoodService;
import cn.duoduo.vo.EsGood;
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

}
