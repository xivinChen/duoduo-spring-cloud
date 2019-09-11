/**
 * FileName: EsGoodclientController
 * Author: xivin
 * Date: 2019-09-09 20:17
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsGoodClientService;
import cn.duoduo.vo.EsGood;
import cn.duoduo.vo.GoodCat;
import cn.duoduo.vo.PageFeign;
import cn.duoduo.vo.qingtaoke.QingTaoKeSearch;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int saveAll(int page){

        RestTemplate restTemplate=new RestTemplate();
        Map<String,String> map=new HashMap<>();
        map.put("app_key","JJhn18Nz");
        map.put("v","1.0");
        int result=0;
        if(page>0) {
            //for(int i=0;i<page;i++) {
                String go = restTemplate.getForObject("http://openapi.qingtaoke.com/qingsoulist?app_key=JJhn18Nz&v=1.0&page="+page, String.class);

                JSONObject jsonObject= JSON.parseObject(go);
                String list = JSONObject.parseObject(jsonObject.getString("data")).getString("list");
                ArrayList<EsGood> esGoods = JSONObject.parseObject(list, new TypeReference<ArrayList<EsGood>>() {
                });
                result += esGoodClientService.saveAll(esGoods);
                System.out.println("--------------------------------");
                System.err.println("总共插入——————————："+result+"条记录！！！！");
                System.out.println("--------------------------------");
                System.out.println("go = " + esGoods.toString());
            //}

        }
        /*String go = restTemplate.getForObject("http://openapi.qingtaoke.com/qingsoulist?app_key=JJhn18Nz&v=1.0", String.class);

        JSONObject jsonObject= JSON.parseObject(go);
        String list = JSONObject.parseObject(jsonObject.getString("data")).getString("list");
        ArrayList<EsGood> esGoods = JSONObject.parseObject(list, new TypeReference<ArrayList<EsGood>>() {
        });
        int i = esGoodClientService.saveAll(esGoods);
        System.out.println("--------------------------------");
        System.err.println("总共插入——————————："+i+"条记录！！！！");
        System.out.println("--------------------------------");
        System.out.println("go = " + esGoods.toString());*/

        return result;
        //return esGoodClientService.saveAll(list);
    }

    @GetMapping("/")
    public Object list() {
        return esGoodClientService.list();
    }

    @RequestMapping("/search")
    public PageFeign<EsGood> search(QingTaoKeSearch qingTaoKeSearch
            , @RequestParam(value = "page",defaultValue = "0") int pageNum
            , @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {

        return this.esGoodClientService.search(qingTaoKeSearch,pageNum,pageSize);
    }

    @GetMapping("/cat/list")
    public List<GoodCat> findCatList() {
        return this.esGoodClientService.getCatsList();
    }

    @GetMapping("/listByCat")
    public PageFeign<EsGood> listByCat(@RequestParam("goods_cat") Integer goods_cat
            ,@RequestParam(value = "page",defaultValue = "0") int page
            ,@RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {

        return this.esGoodClientService.listByCat(goods_cat, page, pageSize);


    }
}
