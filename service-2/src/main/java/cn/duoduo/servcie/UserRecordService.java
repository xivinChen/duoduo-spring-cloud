package cn.duoduo.servcie;

import cn.duoduo.vo.UserRecord;

import java.util.List;
import java.util.Set;

public interface UserRecordService {

    int save(UserRecord userRecord);

    int update(UserRecord userRecord);

    UserRecord get(int id);


    List listByTag(String tag);

    List<UserRecord> selectByPrimaryKeys(Set<Integer> ids);
}
