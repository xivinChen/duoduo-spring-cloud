/**
 * FileName: AdminService
 * Author: xivin
 * Date: 2019-09-05 12:00
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.vo.Admin;
import cn.duoduo.vo.User;

public interface AdminService {

    int save(User user);

    int saveAdminAndUser(User user, Admin admin);
}
