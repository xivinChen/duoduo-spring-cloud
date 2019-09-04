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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int save(User user) {
        return userMapper.insert(user);
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
