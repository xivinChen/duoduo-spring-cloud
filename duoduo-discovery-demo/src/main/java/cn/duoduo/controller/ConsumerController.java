package cn.duoduo.controller;

import cn.duoduo.service.IClientService2ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private IClientService2ConfigService iClientService2ConfigService;

    @GetMapping("/user")
    public Object getService2User() {

        return restTemplate.getForObject("http://duoduo-service2/nacos/config/user",String.class);

    }

    @GetMapping("/client/user")
    public Object getIClientService2User() {
        return this.iClientService2ConfigService.getNacosConfigUser();
    }

}
