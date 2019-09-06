/**
 * FileName: UserController
 * Author: xivin
 * Date: 2019-09-04 11:30
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.servcie.UserService;
import cn.duoduo.vo.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/*@Compensable(interfaceClass = UserService.class,
        confirmableKey = "userConfirmServiceImpl",
        cancellableKey = "userCancelServiceImpl")*/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userServiceImpl;

    @GetMapping("/{id}")
    public User get(@PathVariable("id") int id) {

        return userServiceImpl.get(id);
    }

    //@Transactional
    @PostMapping("/")
    public int save(@RequestBody User user) {
        user.setId(0);
        return userServiceImpl.save(user);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userServiceImpl.delete(id);
    }
}
