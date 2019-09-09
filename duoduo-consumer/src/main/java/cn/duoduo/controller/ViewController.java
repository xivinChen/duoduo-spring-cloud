/**
 * FileName: ViewController
 * Author: xivin
 * Date: 2019-09-09 11:21
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.EsGoodClientService;
import cn.duoduo.service.EsProductClientService;
import cn.duoduo.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.client.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {


    @Resource
    private EsProductClientService esProductClientService;

    @Resource
    EsGoodClientService esGoodClientService;

    @RequestMapping("/hello")
    public ModelAndView hello() {
        ModelAndView modelAndView=new ModelAndView("hello");
        modelAndView.addAllObjects(new HashMap<String,String>() {
            {
                this.put("name","xivin");
                this.put("xivin","this is xivin");
            }
        });
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView=new ModelAndView("product");
        //EsProductSearch esProductSearch=new EsProductSearch();
        //esProductSearch.setKeyWord("荣耀");
        PageFeign<EsProduct> list = this.esProductClientService.testSearch();
        modelAndView.addObject("products",list);
        return modelAndView;
    }

    @RequestMapping("/mvc_search")
    public ModelAndView search(EsProductSearch esProductSearch,
                                       @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                       @RequestParam(value="pageSize",defaultValue="10") int pageSize) {

        PageFeign<EsProduct> search = this.esProductClientService.search(esProductSearch, pageNum, pageSize);
        ModelAndView modelAndView=new ModelAndView("product");
        modelAndView.addObject("products",search);
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/do_get")
    public List<EsGood> doGet() {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,String> map=new HashMap<>();
        map.put("app_key","JJhn18Nz");
        map.put("v","1.0");
        String go = restTemplate.getForObject("http://openapi.qingtaoke.com/qingsoulist?app_key=JJhn18Nz&v=1.0", String.class);

        JSONObject jsonObject=JSON.parseObject(go);
        String list = JSONObject.parseObject(jsonObject.getString("data")).getString("list");
        ArrayList<EsGood> esGoods = JSONObject.parseObject(list, new TypeReference<ArrayList<EsGood>>() {
        });
        int i = esGoodClientService.saveAll(esGoods);
        System.out.println("--------------------------------");
        System.err.println("总共插入——————————："+i+"条记录！！！！");
        System.out.println("--------------------------------");
        System.out.println("go = " + esGoods.toString());

        return esGoods;
    }

    @RequestMapping("/qingtaoke")
    public ModelAndView qingtaoke() {
        return new ModelAndView("qingtaoke");
    }
}
