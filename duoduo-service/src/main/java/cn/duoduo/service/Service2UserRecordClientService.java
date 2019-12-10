package cn.duoduo.service;

import cn.duoduo.config.FeignClientConfig;
import cn.duoduo.service.fallback.Service2UserRecordClientServiceFallback;
import cn.duoduo.vo.UserRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "duoduo-service2",configuration = FeignClientConfig.class,fallbackFactory = Service2UserRecordClientServiceFallback.class)
public interface Service2UserRecordClientService {

    @PostMapping("/service2_user_record/")
    int saveService1User(@RequestBody UserRecord userRecord);

    @GetMapping("/service2_user_record/{id}")
    UserRecord get(@PathVariable("id") int id);


}
