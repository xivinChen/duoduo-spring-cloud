/**
 * FileName: UserController
 * Author: xivin
 * Date: 2019-09-04 11:30
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.servcie.UserService;
import cn.duoduo.vo.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public User get(@PathVariable("id") int id) {

        return userService.get(id);
    }

    @PostMapping("/")
    public int save(User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userService.delete(id);
    }
}
