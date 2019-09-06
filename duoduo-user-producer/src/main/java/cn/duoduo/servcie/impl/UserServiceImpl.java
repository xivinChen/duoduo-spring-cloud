/**
 * FileName: UserServiceImpl
 * Author: xivin
 * Date: 2019-09-04 11:47
 * Description:
 */
package cn.duoduo.servcie.impl;

import cn.duoduo.mapper.UserMapper;
import cn.duoduo.servcie.UserService;
import cn.duoduo.vo.User;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    //@LcnTransaction(propagation = DTXPropagation.SUPPORTS)
    @TccTransaction(confirmMethod = "saveConfirm",cancelMethod = "saveCancel",propagation = DTXPropagation.SUPPORTS)
    @Override
    public int save(User user) {
        System.out.println("user try");
        return userMapper.insert(user);
    }

    public void saveConfirm(User user) {
        System.out.println("user save successful,not operation confirm");
    }

    public void saveCancel(User user) {
        userMapper.deleteByPrimaryKey(user.getId());
        System.out.println("user add fail,rollback user");
    }

    @Override
    public int delete(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
