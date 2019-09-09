/**
 * FileName: EsGoodclientServiceFallBack
 * Author: xivin
 * Date: 2019-09-09 20:14
 * Description:
 */
package cn.duoduo.service.fallback;

import cn.duoduo.service.EsGoodClientService;
import cn.duoduo.vo.EsGood;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EsGoodClientServiceFallBack implements FallbackFactory<EsGoodClientService> {
    @Override
    public EsGoodClientService create(Throwable throwable) {
        return new EsGoodClientService() {
            @Override
            public int saveOne(EsGood esGood) {
                return 0;
            }

            @Override
            public int saveAll(List<EsGood> list) {
                return 0;
            }

            @Override
            public Object list() {
                return null;
            }
        };
    }
}
