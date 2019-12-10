package cn.duoduo.service.fallback;

import cn.duoduo.service.Service2UserRecordClientService;
import cn.duoduo.vo.UserRecord;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class Service2UserRecordClientServiceFallback implements FallbackFactory<Service2UserRecordClientService> {

    @Override
    public Service2UserRecordClientService create(Throwable cause) {
        return new Service2UserRecordClientService() {
            @Override
            public int saveService1User(UserRecord userRecord) {
                return 0;
            }

            @Override
            public UserRecord get(int id) {
                return null;
            }
        };


    }
}
