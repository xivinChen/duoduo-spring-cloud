/**
 * FileName: EsProductClientServiceFallBack
 * Author: xivin
 * Date: 2019-09-06 17:10
 * Description:
 */
package cn.duoduo.service.fallback;

import cn.duoduo.service.EsProductClientService;
import cn.duoduo.vo.EsProduct;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class EsProductClientServiceFallBack implements FallbackFactory<EsProductClientService> {

    @Override
    public EsProductClientService create(Throwable throwable) {
        return new EsProductClientService() {

            @Override
            public int save(EsProduct esProduct) {
                return -1;
            }
        };
    }
}
