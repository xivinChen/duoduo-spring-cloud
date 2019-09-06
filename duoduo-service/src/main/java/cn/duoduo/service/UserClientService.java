/**
 * FileName: UserService
 * Author: xivin
 * Date: 2019-09-04 11:02
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.config.FeignClientConfig;
import cn.duoduo.service.fallback.UserClientServiceFallback;
import cn.duoduo.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "duoduo-user-producer",configuration = FeignClientConfig.class,fallbackFactory = UserClientServiceFallback.class)
public interface UserClientService {

    @GetMapping("/user/{id}")
    User get(@PathVariable("id") int id);

    @PostMapping("/user/")
    int save(@RequestBody User user);
}
