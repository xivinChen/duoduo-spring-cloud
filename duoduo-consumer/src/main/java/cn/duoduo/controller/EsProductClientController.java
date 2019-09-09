/**
 * FileName: EsProductClientController
 * Author: xivin
 * Date: 2019-09-06 17:08
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsProductClientService;
import cn.duoduo.vo.EsProduct;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
