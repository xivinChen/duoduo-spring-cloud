package cn.duoduo.mapper;

import cn.duoduo.vo.UserRecord;

public interface UserRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRecord record);

    int insertSelective(UserRecord record);

    UserRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRecord record);

    int updateByPrimaryKey(UserRecord record);
}