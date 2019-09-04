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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserClientConsumer {

    @Autowired
    private UserClientService userClientService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) {
        return this.userClientService.get(id);
    }

}
