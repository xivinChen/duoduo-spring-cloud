/**
 * FileName: AdminServiceImpl
 * Author: xivin
 * Date: 2019-09-05 12:02
 * Description:
 */
package cn.duoduo.service.impl;

import cn.duoduo.mapper.AdminMapper;
import cn.duoduo.service.AdminService;
import cn.duoduo.service.UserClientService;
import cn.duoduo.vo.Admin;
import cn.duoduo.vo.User;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("adminServiceImpl")
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserClientService userClientService;

    //@Transactional
    //@LcnTransaction
    //@TccTransaction(confirmMethod = "saveConfirm",cancelMethod = "saveCancel")
    @Override
    public int save(User user) {

        System.out.println("admin尝试阶段");

        int result= userClientService.save(user);
        int i=1/0;
        return result;

    }

    @TccTransaction(confirmMethod = "saveConfirm",cancelMethod = "saveCancel")
    @Override
    public int saveAdminAndUser(User user,Admin admin) {

        System.out.println("admin尝试阶段");

        adminMapper.insert(admin);
        int result= userClientService.save(user);
        int i=1/0;
        return result;

    }
    //@Override
    public void saveConfirm(User user,Admin admin) {
        System.out.println("添加成功，无操作 确认！");
    }

    public void saveCancel(User user,Admin admin) {
        adminMapper.deleteByPrimaryKey(admin.getId());
        System.out.println("添加失败，此模块回滚 admin！！！");
    }
}
