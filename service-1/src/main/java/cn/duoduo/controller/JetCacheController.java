package cn.duoduo.controller;

import cn.duoduo.vo.Product;
import cn.duoduo.servcie.JetCacheService;
import cn.duoduo.vo.User;
import com.alicp.jetcache.Cache;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/jet")
public class JetCacheController {

    public Cache<Integer, User> userCache;

    @Resource
    private JetCacheService jetCacheService;

    @GetMapping("/{id}")
    public Product get(@PathVariable("id") long id) {
        return jetCacheService.getById(id);
    }

    @PutMapping("/")
    public void update(@RequestBody Product product) {
        jetCacheService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        jetCacheService.deleteProduct(id);
    }

}
