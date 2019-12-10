package cn.duoduo.mapper;

import cn.duoduo.vo.UserRecord;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Set;

public interface UserRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRecord record);

    int insertSelective(UserRecord record);

    UserRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRecord record);

    int updateByPrimaryKey(UserRecord record);

    List<UserRecord> listByTag(String tag);

    List<UserRecord> selectByPrimaryKeys(Set<Integer> ids);
}