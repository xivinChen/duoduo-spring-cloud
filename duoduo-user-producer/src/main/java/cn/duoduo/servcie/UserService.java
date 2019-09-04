/**
 * FileName: UserService
 * Author: xivin
 * Date: 2019-09-04 11:46
 * Description:
 */
package cn.duoduo.servcie;

import cn.duoduo.vo.User;

public interface UserService {
    int save(User user);

    int delete(int id);

    User get(int id);
}
