/**
 * FileName: UserController
 * Author: xivin
 * Date: 2019-09-04 11:30
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.servcie.TccActionOne;
import cn.duoduo.servcie.UserService;
import cn.duoduo.vo.User;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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

    @Resource
    private TccActionOne tccActionOne;

    //@GlobalTransactional
    @PostMapping("/tcc_one")
    public boolean saveOne() {
        return this.tccActionOne.prepare(null,1);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") int id) {

        return userServiceImpl.get(id);
    }

    //@Transactional
    @CacheEvict(value = "duoduo:user",key = "#user.recordId")
    @PostMapping("/")
    public int save(@RequestBody User user) {
        user.setId(0);
        return userServiceImpl.save(user);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userServiceImpl.delete(id);
    }

    @Cacheable(value = "duoduo:user",key = "#recordId")
    @GetMapping("/listByRecordId")
    public List listByRecordId(int recordId) {
        return this.userServiceImpl.listByRecordId(recordId);
    }
}
