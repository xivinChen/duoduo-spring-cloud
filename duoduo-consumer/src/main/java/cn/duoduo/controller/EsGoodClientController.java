/**
 * FileName: EsGoodclientController
 * Author: xivin
 * Date: 2019-09-09 20:17
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsGoodClientService;
import cn.duoduo.vo.EsGood;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/es_good")
public class EsGoodClientController {

    @Resource
    private EsGoodClientService esGoodClientService;

    @PostMapping("/")
    public int saveOne(@RequestBody EsGood esGood) {
        return esGoodClientService.saveOne(esGood);
    }

    @PostMapping("/all")
    public int saveAll(@RequestBody List<EsGood> list){

        return esGoodClientService.saveAll(list);
    }

    @GetMapping("/")
    public Object list() {
        return esGoodClientService.list();
    }
}
