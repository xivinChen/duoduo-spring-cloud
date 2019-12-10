package cn.duoduo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nacos/config")
@RefreshScope
public class NacosConfigController {

    @Value("${user.username}")
    private String username;

    @Value("${user.sex}")
    private String sex;

    @Value("${user.age}")
    private Integer age;

    @GetMapping("/user")
    public String getNacosConfigUser() {
        return username+"--"+sex+"--"+age;
    }

}
