/**
 * FileName: EsProductClientServiceFallBack
 * Author: xivin
 * Date: 2019-09-06 17:10
 * Description:
 */
package cn.duoduo.service.fallback;

import cn.duoduo.service.EsProductClientService;
import cn.duoduo.vo.EsProduct;
import cn.duoduo.vo.EsProductSearch;
import cn.duoduo.vo.PageFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EsProductClientServiceFallBack implements FallbackFactory<EsProductClientService> {

    @Override
    public EsProductClientService create(Throwable throwable) {
        return new EsProductClientService() {

            @Override
            public int save(EsProduct esProduct) {
                return -1;
            }

            @Override
            public Object list() {
                return null;
            }

            @Override
            public Page simpleSearch(String keyWord, int pageNum, int pageSize) {
                return null;
            }

            @Override
            public PageFeign<EsProduct> search(EsProductSearch esProductSearch, int pageNum, int pageSize) {
                List<EsProduct> list=new ArrayList<EsProduct>();
                EsProduct esProduct=new EsProduct();
                esProduct.setBrandId(-1);
                esProduct.setName("hystrix");
                list.add(esProduct);
                System.out.println("熔断了 ！！！");
                return null;
            }

            @Override
            public PageFeign<EsProduct> testSearch() {
                System.out.println("熔断了 @！");
                return null;
            }
        };
    }
}
