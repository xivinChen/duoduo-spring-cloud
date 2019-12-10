package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.UserProductComboDayMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.IAdminClientService;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.omg.CORBA.INTERNAL;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
@Service
public class UserProductComboDayServiceImpl implements IUserProductComboDayService {

    @Resource
    private UserProductComboDayMapper userProductComboDayMapper;

    @Resource
    private UserProductComboMapper userProductComboMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IUserClientService iUserClientService;

    @Resource
    private IAdminClientService iAdminClientService;


    @Override
    public PageInfo<UserProductComboDay> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserProductComboDay> all = userProductComboDayMapper.findAll();

        List<Integer> userIds=new ArrayList<>();
        List<Integer> adminIds=new ArrayList<>();

        for (UserProductComboDay userProductComboDay : all) {
            if(userProductComboDay.getUserId()>0)
                userIds.add(userProductComboDay.getUserId());
            if(null != userProductComboDay.getUserProductComboDayByAdmin() && userProductComboDay.getUserProductComboDayByAdmin().getAdminId()>0)
                adminIds.add(userProductComboDay.getUserProductComboDayByAdmin().getAdminId());
        }

        getUserByIds(all,userIds);
        getAdminByIds(all,adminIds);

        return new PageInfo<>(all);
    }

    @Override
    public UserProductComboDay get(int id) {
        return userProductComboDayMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<UserProductComboDay> selectByUserTel(String tel, int pageNum, int pageSize) {
        User user=null;
        try {
            user = iUserClientService.getOneUserByTel(tel);
        }catch (Exception e) {
            System.out.println("获取用户发生异常"+e.getMessage());
        }
        List<Integer> adminIds=new ArrayList<>();
        if(user!=null) {
            PageHelper.startPage(pageNum,pageSize);
            List<UserProductComboDay> userProductComboDays = userProductComboDayMapper.selectByUserId(user.getId());

            if(null == userProductComboDays || userProductComboDays.size() < 1)
                return new PageInfo<>();


            for (int i=0;i<userProductComboDays.size();i++) {
                if(null != userProductComboDays.get(i)) {
                    userProductComboDays.get(i).setUser(user);
                    if (null != userProductComboDays.get(i).getUserProductComboDayByAdmin()) {

                        int adminId = userProductComboDays.get(i).getUserProductComboDayByAdmin().getAdminId();
                        if (adminId > 0) {
                            adminIds.add(adminId);
                        }
                    }
                }
            }

            if(adminIds.size()>0) {
                this.getAdminByIds(userProductComboDays,adminIds);
            }

            return new PageInfo<>(userProductComboDays);
        }


        return new PageInfo<>();
    }

    @Override
    public PageInfo<UserProductComboDay> selectByUserProductComboId(int userProductComboId, int pageNum, int pageSize) {
        //User user = iUserClientService.getOneUser(tel);

        PageHelper.startPage(pageNum,pageSize);
        List<UserProductComboDay> userProductComboDays = userProductComboDayMapper.selectByUserProductComboId(userProductComboId);

        //Integer userId=userProductComboMapper.selectByPrimaryKey(userProductComboId).getUserId();
        UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(userProductComboId);
        if(userProductCombo==null)
            return new PageInfo<>();
        Integer userId = userProductCombo.getUserId();
        User user=null;
        if(userId!=null&&userId>0) {
            user = iUserClientService.getOneUser(userId);
        }

        List<Integer> adminIds=new ArrayList<>();
        for (UserProductComboDay userProductComboDay : userProductComboDays) {

            if(user!=null) {
                userProductComboDay.setUser(user);
            }
            if(userProductComboDay.getUserProductComboDayByAdmin()!=null) {
                Integer adminId = userProductComboDay.getUserProductComboDayByAdmin().getAdminId();
                if (adminId != null && userId > 0) {
                    adminIds.add(adminId);
                }
            }


        }

        if(adminIds.size()>0) {
            Map<Integer,Admin> adminMap=iAdminClientService.listByIds(adminIds);
            for (UserProductComboDay upcd : userProductComboDays) {
                if(upcd.getUserProductComboDayByAdmin()!=null) {
                    int aId = upcd.getUserProductComboDayByAdmin().getAdminId();
                    if (aId > 0 && adminMap.containsKey(aId)) {
                        upcd.getUserProductComboDayByAdmin().setAdmin(adminMap.get(aId));
                    }
                }
            }
        }
        return new PageInfo<>(userProductComboDays);
    }

    public List<UserProductComboDay> getAdminByIds(List<UserProductComboDay> userProductComboDays,List<Integer> adminIds) {

        if(adminIds.size()>0) {
            Map<Integer,Admin> adminMap=iAdminClientService.listByIds(adminIds);
            for (UserProductComboDay upcd : userProductComboDays) {
                if(upcd.getUserProductComboDayByAdmin()!=null) {
                    int aId = upcd.getUserProductComboDayByAdmin().getAdminId();
                    if (aId > 0 && adminMap.containsKey(aId)) {
                        upcd.getUserProductComboDayByAdmin().setAdmin(adminMap.get(aId));
                    }
                }
            }
        }
        return userProductComboDays;
    }

    public List<UserProductComboDay> getUserByIds(List<UserProductComboDay> userProductComboDays,List<Integer> userIds) {
        if(userIds.size()>0) {
            Map<Integer,User> userMap=iUserClientService.listByIds(userIds);
            for (UserProductComboDay upcd : userProductComboDays) {
                int uId=upcd.getUserId();
                if(uId>0&&userMap.containsKey(uId)) {
                    upcd.setUser(userMap.get(uId));
                }
            }
        }
        return userProductComboDays;
    }

}
