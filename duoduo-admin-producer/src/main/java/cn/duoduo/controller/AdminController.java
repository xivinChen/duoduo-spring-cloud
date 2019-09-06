/**
 * FileName: AdminController
 * Author: xivin
 * Date: 2019-09-05 11:04
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.service.AdminService;
import cn.duoduo.vo.Admin;
import cn.duoduo.vo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*@Compensable(interfaceClass = AdminService.class,
        confirmableKey = "adminConfirmServiceImpl",
        cancellableKey = "adminCancelServiceImpl")*/
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminServiceImpl;



    //@Transactional
    @PostMapping("/")
    public int add() {

        User user=new User();
        Admin admin=new Admin();
        user.setUsername("kevin");
        user.setPassword("123456");
        user.setTel("123456");
        user.setStatus(1);
        admin.setPassword("123456");
        admin.setAccount("admin222");
        admin.setStatus(1);
        return adminServiceImpl.saveAdminAndUser(user,admin);
    }
}
