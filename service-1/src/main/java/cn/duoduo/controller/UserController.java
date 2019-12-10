/**
 * FileName: UserController
 * Author: xivin
 * Date: 2019-09-04 11:30
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.servcie.UserService;
import cn.duoduo.vo.User;
import cn.duoduo.vo.UserRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*@Compensable(interfaceClass = UserService.class,
        confirmableKey = "userConfirmServiceImpl",
        cancellableKey = "userCancelServiceImpl")*/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userServiceImpl;

    //@Cacheable(value = "duoduo:user:id",key = "#id",unless = "#result == null")
    @GetMapping("/{id}")
    public User get(@PathVariable("id") int id) {

        return userServiceImpl.get(id);
    }

    //@Transactional
    //@CacheEvict(value = "duoduo:user",key = "#user.recordId")
    @PostMapping("/")
    public int save(@RequestBody User user) {
        user.setId(0);
        System.out.println(user.getUserRecord()+"userRecord------");
        return userServiceImpl.save(user);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userServiceImpl.delete(id);
    }

    //@Cacheable(value = "duoduo:user",key = "#recordId")
    @GetMapping("/listByRecordId")
    public List listByRecordId(int recordId) {
        return this.userServiceImpl.listByRecordId(recordId);
    }
}
