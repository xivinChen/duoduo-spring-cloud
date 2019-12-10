package cn.duoduo.servcie.impl;

import cn.duoduo.vo.Product;
import cn.duoduo.servcie.JetCacheService;
import com.alicp.jetcache.anno.Cached;
import org.springframework.stereotype.Service;

@Service
public class JetCacheServiceImpl implements JetCacheService {


    @Override
    public Product getById(long id) {
        System.out.println("select by mysql");
        Product product = new Product();
        product.setId(id);
        product.setName("product"+id);
        product.setType(1);
        return product;
    }

    @Override
    public void updateProduct(Product product) {

        System.out.println("hello product update");
    }

    @Override
    public void deleteProduct(long id) {

        System.out.println("hello product delete : id = "+id);
    }
}
