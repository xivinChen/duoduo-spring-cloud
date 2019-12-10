package cn.duoduo.servcie;

import cn.duoduo.vo.Product;
import com.alicp.jetcache.anno.*;

public interface JetCacheService {

    @Cached(name = "productCache.",key = "#id",expire = 36000000,cacheType = CacheType.REMOTE)
    Product getById(long id);

    @CacheUpdate(name = "productCache.",key = "#product.id",value = "#product")
    void updateProduct(Product product);

    @CacheInvalidate(name = "productCache.",key = "#id")
    void deleteProduct(long id);
}
