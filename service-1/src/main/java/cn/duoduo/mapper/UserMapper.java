/**
 * FileName: UserMapper
 * Author: xivin
 * Date: 2019-09-04 11:48
 * Description:
 */
package cn.duoduo.mapper;

import cn.duoduo.vo.User;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUsername(String username);
    User selectByTel(String tel);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkExistByUsername(String username);
    int checkExistByTel(String tel);


    int updatePasswordByTel(String tel, String password);

    List<User> selectByRecordId(Integer recordId);
}
