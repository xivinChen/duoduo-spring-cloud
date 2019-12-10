package cn.duoduo.servcie.impl;

import cn.duoduo.mapper.UserRecordMapper;
import cn.duoduo.servcie.UserRecordService;
import cn.duoduo.vo.UserRecord;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class UserRecordServiceImpl implements UserRecordService {

    @Resource
    private UserRecordMapper userRecordMapper;

    @GlobalTransactional
    @Override
    public int save(UserRecord userRecord) {
        System.out.println("XID is --"+ RootContext.getXID());

        int i = userRecordMapper.insertSelective(userRecord);
        userRecord.setId(2);
        userRecordMapper.updateByPrimaryKey(userRecord);
        int a= 9/0;
        return i;
    }

    @GlobalTransactional
    @Override
    public int update(UserRecord userRecord) {
        userRecordMapper.updateByPrimaryKeySelective(userRecord);
        int i=1/0;
        return 1;
    }

    @Override
    public UserRecord get(int id) {
        return userRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List listByTag(String tag) {
        return this.userRecordMapper.listByTag(tag);
    }

    @Cacheable(value = "duoduo:userRecordIds:ids",key = "#ids",unless = "#result == null ")
    @Override
    public List<UserRecord> selectByPrimaryKeys(Set<Integer> ids) {
        return this.userRecordMapper.selectByPrimaryKeys(ids);
    }
}
