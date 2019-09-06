/**
 * FileName: UserClientConsumer
 * Author: xivin
 * Date: 2019-09-04 12:45
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.UserClientService;
import cn.duoduo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserClientConsumer {

    @Autowired
    private UserClientService userClientService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) {
        return this.userClientService.get(id);
    }

    @PostMapping("/")
    public int save() {

        User user=new User();
        user.setId((int) Math.random()*100);
        user.setUsername("xivin");
        user.setPassword("12355");
        user.setStatus(1);
        user.setTel("1463636476");
        return userClientService.save(user);
    }

}
