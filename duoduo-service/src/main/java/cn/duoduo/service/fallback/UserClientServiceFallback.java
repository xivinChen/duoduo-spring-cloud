/**
 * FileName: UserServiceFallback
 * Author: xivin
 * Date: 2019-09-04 11:03
 * Description:
 */
package cn.duoduo.service.fallback;

import cn.duoduo.service.UserClientService;
import cn.duoduo.vo.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientServiceFallback implements FallbackFactory<UserClientService> {

    @Override
    public UserClientService create(Throwable throwable) {
        return new UserClientService() {

            @Override
            public User get(int id) {
                User user=new User();
                user.setId(-1);
                user.setUsername("hystrix");
                return user;
            }

            @Override
            public int save(User user) {
                return -1;
            }
        };
    }
}
