/**
 * FileName: ProductERController
 * Author: xivin
 * Date: 2019-09-04 14:45
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.ProductERService;
import cn.duoduo.service.UserClientService;
import cn.duoduo.vo.Product;
import cn.duoduo.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
@ApiOperation(value = "商品搜索相关接口",tags = "product 商品搜索相关接口")
public class ProductERController {

    @Resource
    private UserClientService userClientService;
    @Resource
    private ProductERService productERService;

    @GetMapping("/test_user")
    public Object testUser(@RequestBody User user, @RequestBody Product product) {

        userClientService.save(user);
        int i=1/0;
        return productERService.save(product);

    }

    @GetMapping("/")
    public Object list() {
        return productERService.list();
    }



}
