/**
 * FileName: UserServiceImpl
 * Author: xivin
 * Date: 2019-09-04 11:47
 * Description:
 */
package cn.duoduo.servcie.impl;

import cn.duoduo.mapper.UserMapper;
import cn.duoduo.servcie.UserService;
import cn.duoduo.service.Service2UserRecordClientService;
import cn.duoduo.vo.User;
import cn.duoduo.vo.UserRecord;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private Service2UserRecordClientService service1UserClientService;


    @GlobalTransactional
    @Override
    public int save(User user) {
        UserRecord userRecord = user.getUserRecord();
        System.out.println("userRecord = " + userRecord);
        int result = userMapper.insertSelective(user);
        result = 1/0;
        int i = service1UserClientService.saveService1User(userRecord);
        i=1/0;
        return 1;
    }

    //@GlobalTransactional
    public int save2(User user) {
        int insert = userMapper.insert(user);
        UserRecord userRecord = user.getUserRecord();

        return insert;
    }


    @Override
    public int saveConfirm(User user) {
        return 0;
    }

    @Override
    public int saveCancel(User user) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }


    @Override
    public User get(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null)
            return null;
        UserRecord userRecord1 = service1UserClientService.get(user.getRecordId());
        if(userRecord1 != null)
            user.setUserRecord(userRecord1);
        return user;
    }

    @Override
    public List<User> listByRecordId(int recordId) {
        return userMapper.selectByRecordId(recordId);
    }
}
