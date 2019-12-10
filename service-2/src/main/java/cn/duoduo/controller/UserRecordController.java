/**
 * FileName: UserController
 * Author: xivin
 * Date: 2019-09-04 11:30
 * Description:
 */
package cn.duoduo.controller;


import cn.duoduo.servcie.UserRecordService;
import cn.duoduo.vo.UserRecord;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import java.util.List;
import java.util.Set;

@CacheConfig(cacheNames = "duoduo:userRecord")
@RestController
@RequestMapping("/service2_user_record")
public class UserRecordController {

    @Resource
    private UserRecordService userRecordService;

    @PostMapping("/")
    public int save(@RequestBody UserRecord user) {

        System.out.println("xid--"+ RootContext.getXID());
        return userRecordService.save(user);

    }

    @CacheEvict(key = "#userRecord.id")
    @PutMapping("/")
    public int update(@RequestBody UserRecord userRecord) {
        return this.userRecordService.update(userRecord);
    }

    @Cacheable(key = "methodName + '-' + #id",unless = "#result == null")
    @GetMapping("/{id}")
    public UserRecord get(@PathVariable("id") int id) {
        return this.userRecordService.get(id);
    }

    @Cacheable(keyGenerator = "keyGenerator",unless = "#result == null")
    @GetMapping("/on")
    public UserRecord getOneRecord(int id) {
        return this.userRecordService.get(id);
    }

    @Cacheable(key = "methodName + '-' + #tag",unless = "#result = null")
    @GetMapping("/")
    public List listByTag(String tag) {
        return this.userRecordService.listByTag(tag);
    }

    @GetMapping("/ids")
    public List listByPrimaryKeys(@RequestParam("ids") Set<Integer> ids) {
        return this.userRecordService.selectByPrimaryKeys(ids);
    }


}
