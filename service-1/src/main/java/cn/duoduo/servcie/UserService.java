/**
 * FileName: UserService
 * Author: xivin
 * Date: 2019-09-04 11:46
 * Description:
 */
package cn.duoduo.servcie;

import cn.duoduo.vo.User;

import java.util.List;

public interface UserService {
    //@TwoPhaseBusinessAction(name = "save",commitMethod = "saveConfirm",rollbackMethod = "saveCancel")
    int save(User user);

    int saveConfirm(User user);
    int saveCancel(User user);

    int delete(int id);

    User get(int id);

    List<User> listByRecordId(int recordId);
}
