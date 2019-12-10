package cn.duoduo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "duoduo-service2")
public interface IClientService2ConfigService {

    @GetMapping("nacos/config/user")
    String getNacosConfigUser();
}
