/**
 * FileName: UserServiceImpl
 * Author: xivin
 * Date: 2019-09-04 11:47
 * Description:
 */
package cn.duoduo.servcie.impl;

import cn.duoduo.mapper.UserMapper;
import cn.duoduo.servcie.TccActionOne;
import cn.duoduo.servcie.UserService;
import cn.duoduo.vo.User;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TccActionOne tccActionOne;

    //@LcnTransaction(propagation = DTXPropagation.SUPPORTS)
    //@TccTransaction(confirmMethod = "saveConfirm",cancelMethod = "saveCancel",propagation = DTXPropagation.SUPPORTS)
    //@Transactional

    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int save(User user) {
        //tccActionOne.prepare(null,1);
        System.out.println("user try");
        userMapper.insert(user);
        //int i=1/0;
        return 1;
    }

    //@GlobalTransactional
    public int save2(User user) {
        int insert = userMapper.insert(user);
        return insert;
    }

    @Override
    public int saveConfirm(User user) {
        System.out.println("user save successful,not operation confirm");
        return 1;
    }

    @Override
    public int saveCancel(User user) {
        userMapper.deleteByPrimaryKey(user.getId());
        System.out.println("user add fail,rollback user");
        return 0;
    }

    @Override
    public int delete(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> listByRecordId(int recordId) {
        return userMapper.selectByRecordId(recordId);
    }
}
