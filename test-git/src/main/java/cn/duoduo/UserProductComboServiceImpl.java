package cn.bosenkeji.service.Impl;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.mapper.ComboRedisKeyMapper;
import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboRedisTemplate;
import cn.bosenkeji.service.*;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.UserComboTimeUtil;
import cn.bosenkeji.vo.ComboRedisKeyParameter;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.ComboRedisKey;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.product.Product;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.aliyuncs.schedulerx2.model.v20190430.CreateJobResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.map.HashedMap;
import org.dromara.hmily.annotation.Hmily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
@Service
public class UserProductComboServiceImpl implements IUserProductComboService {

    @Resource
    private UserProductComboMapper userProductComboMapper;

    @Resource
    private ProductComboMapper productComboMapper;


    @Resource
    private UserProductComboRedisTemplate userProductComboRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;


    @Resource
    private IUserClientService iUserClientService;

    @Resource
    private IProductClientService iProductClientService;

    @Autowired
    private JobService jobService;

    @Resource
    private ComboRedisKeyMapper comboRedisKeyMapper;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());
    

    @Resource
    private ITradePlatformApiBindProductComboClientService iTradePlatformApiBindProductComboClientService;

    /**
     * 涉及分布式事务，要注意各个操作的顺序
     * @param userProductCombo
     * @return
     */
    @Hmily(confirmMethod = "addConfirm",cancelMethod = "addCancel")
    //@Transactional
    @Override
    public int add(UserProductCombo userProductCombo) {

        //查询套餐时长
        int time=productComboMapper.selectTimeByPrimaryKey(userProductCombo.getProductComboId());
        //int i=1/0;
        //保存到数据库 管理端部署机器人
        int aresult=userProductComboMapper.insert(userProductCombo);
        //部署失败直接返回
        if(aresult!=1)
            return 0;

        String idStr=String.valueOf(userProductCombo.getId());
        int key=0;
        String currentKey="";

        UserComboTimeUtil.addTime(currentKey,time,key,jobService,redisTemplate,userProductCombo,userProductComboMapper);


        //int i=1/0;
        //用户端机器人
        TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo=new TradePlatformApiBindProductCombo();
        tradePlatformApiBindProductCombo.setUserProductComboId(userProductCombo.getId());
        tradePlatformApiBindProductCombo.setUserId(userProductCombo.getUserId());
        tradePlatformApiBindProductCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformApiBindProductCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        int uresult= (int) iTradePlatformApiBindProductComboClientService.addTradePlatformApiBindProductCombo(tradePlatformApiBindProductCombo).getData();

        System.out.println(uresult+"uresult");
        //int i=1/0;
        return 1;

    }

    public int addConfirm(UserProductCombo userProductCombo) {

        Log.info(userProductCombo.getId()+"机器人部署确认成功");
        return 3;
    }


    public int addCancel(UserProductCombo userProductCombo) {
        Log.error(userProductCombo.getId()+"机器人部署失败，进入cancel");

        //删除机器人
        userProductComboMapper.deleteByPrimaryKey(userProductCombo.getId());

        redisTemplate.opsForZSet().remove(userProductCombo.getRedisKey(),String.valueOf(userProductCombo.getId()));
        return -3;
    }



    @Override
    public int update(UserProductCombo userProductCombo) {
        return userProductComboMapper.updateByPrimaryKeySelective(userProductCombo);
    }

    @Override
    public PageInfo<UserProductCombo> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<UserProductCombo>(userProductComboMapper.findAll());
    }

    /**
     * 删除用户套餐功能，同时删除绑定记录，还有 redis的剩余时长
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {
        //UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);
        Result result = iTradePlatformApiBindProductComboClientService.deleteByComboId(id);
        int result1=(int) result.getData();
        int result2 = userProductComboMapper.deleteByPrimaryKey(id);
        userProductComboRedisTemplate.setExpire(id,0);
        System.out.println("result1:"+result1);
        System.out.println("result2:"+result2);
        if(result1==result2)
            return result1;
        else
            return result2;

    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        return 0;
    }

    //多表查询
    @Override
    public UserProductCombo get(int id) {

        //数据库联表查询用户套餐信息
        UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);

        //根据id获取用户套餐的有效时长
        final long time = userProductComboRedisTemplate.getExpire(id);
        if(time>0) {
            userProductCombo.setRemainTime((int)time);
        }else {
            userProductCombo.setRemainTime(0);
        }

        return userProductCombo;


    }




    /**
     * 联合查询用户套餐时长列表
     * @param pageNum
     * @param pageSize
     * @param userTel
     * @return
     */
    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserTel(int pageNum,int pageSize,String userTel) {

        //PageHelper.startPage(pageNum,pageSize);
        User user=null;

        try{
            user = iUserClientService.getOneUserByTel(userTel);
        }catch (Exception e) {
            e.printStackTrace();
            return new PageInfo<>();
        }


        //查不到用户
        if(user==null) {
            return new PageInfo<>();
        }
        //从数据库查询

        return selectUserProductComboByUserId(user.getId(),pageSize,pageNum);

    }

    /**
     * 联合查询用户套餐时长列表
     * @param pageNum
     * @param pageSize
     * @param userId 用户ID
     * @return
     */
    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserId(int pageNum,int pageSize,int userId) {

        //从数据库查询
        PageHelper.startPage(pageNum,pageSize);
        List<UserProductCombo> userProductCombos = userProductComboMapper.selectUserProductComboByUserId(userId);

        getProductByPids(userProductCombos);

        return new PageInfo<>(userProductCombos);
    }

    @Override
    public int checkExistByProductIdAndUserId(int productComboId,int userId) {
        int productId=productComboMapper.selectProductIdByPrimaryKey(productComboId);
        return userProductComboMapper.checkExistByProductIdAndUserId(productId,userId);
    }

    @Override
    public Map<Integer,UserProductCombo> selectByPrimaryKeys(List<Integer> ids) {
        Map<Integer,UserProductCombo> userProductCombosMap=userProductComboMapper.selectByPrimaryKeys(ids);
        List<UserProductCombo> userProductCombos=new ArrayList<>(userProductCombosMap.values());
        getProductByPids(userProductCombos);
        return userProductCombosMap;
    }

    public List<UserProductCombo> getProductByPids(List<UserProductCombo> userProductCombos) {
        List<Integer> pids=new ArrayList<>();
        for (UserProductCombo userProductCombo : userProductCombos) {

            //从缓存拿去剩余时间
            int id = userProductCombo.getId();
            String redisKey=userProductCombo.getRedisKey();
            Double score = redisTemplate.opsForZSet().score(redisKey, String.valueOf(id));
            int time=0;
            if(score!=null)
                time=score.intValue();
            //long time=userProductComboRedisTemplate.getExpire(id);

            userProductCombo.setRemainTime(time>0?time:0);

            //循环收集产品ID
            pids.add(userProductCombo.getProductCombo().getProductId());
        }

        if(pids!=null&&pids.size()>0) {

            //通过多个id获取产品的 map
            Map<Integer, Product> productMap=iProductClientService.listByPrimaryKeys(pids);
            //循环把产品 映射到套餐中
            for (UserProductCombo userProductCombo:userProductCombos) {
                Integer id=userProductCombo.getProductCombo().getProductId();
                if(id!=null&&id>0&productMap.containsKey(id)) {
                    userProductCombo.getProductCombo().setProduct(productMap.get(id));
                }
            }
        }
        return userProductCombos;
    }

    @Override
    public int moveComboTime() {

        int result=0;
        if(!redisTemplate.hasKey(UserComboRedisEnum.UserComboTime+"_0")) {
            System.out.println("用户时长 数据恢复中");
            List<UserProductCombo> all = userProductComboMapper.findAll();
            long currentTime = Timestamp.valueOf(LocalDateTime.now()).getTime();

            //List<UserProductCombo> hasTimeCombo = getProductByPids(all);


            if(all!=null&&all.size()>0) {
                
                //先尝试创建任调度再创建redis key
                try{

                    System.out.println("尝试创建任务调度");
                    CreateJobResponse job = jobService.createJob(UserComboRedisEnum.UserComboTime + "_0", "0 0 0 * * ?", UserComboRedisEnum.UserComboTime + "_0");
                    if(!job.getSuccess()) {
                        Log.error("schedulex create fail");
                        return -1;
                    }
                    else {
                        Log.info("schedulex create success！！！");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("初始化 任务调度失败");
                    return -1;
                }
                for (UserProductCombo userProductCombo : all) {
                    Timestamp createdAt = userProductCombo.getCreatedAt();
                    long createTime = createdAt.getTime();
                    long remain= (currentTime-createTime)/(1000*60*60*24);
                    long remainTime= userProductCombo.getProductCombo().getTime() - remain;
                    if(remainTime>0) {
                        redisTemplate.opsForZSet().add(UserComboRedisEnum.UserComboTime+"_0",String.valueOf(userProductCombo.getId()),remainTime);
                        result++;
                    }
                }

                userProductComboMapper.updateRedisKeyAll(UserComboRedisEnum.UserComboTime+"_0");


            }
            System.out.println("用户时长 数据恢复完成");

        }

        return result;
    }

    @Override
    public int moveComboRedis() {

        List<UserProductCombo> redisId0 = userProductComboMapper.findByRedisKeyId(0);
        int result=0;

        if(redisId0!=null&&redisId0.size()>0) {

            List<String> redisByGroup = userProductComboMapper.selectRedisByGroup();
            for (String key : redisByGroup) {
                if(!redisTemplate.hasKey(key)) {

                }
                ComboRedisKey comboRedisKey = comboRedisKeyMapper.selectByName(key);
                if(comboRedisKey==null) {

                    comboRedisKey = new ComboRedisKey();
                    comboRedisKey.setName(key);
                    comboRedisKey.setStatus(1);
                    int insert = comboRedisKeyMapper.insert(comboRedisKey);
                    if(insert==1)
                        Log.info("添加ComboRedisKey:"+key+" 成功");
                    else
                        Log.info("添加ComboRedisKey:"+key+" 失败了！！！");

                }

                List<Integer> comboIdList=new ArrayList();
                for(UserProductCombo userProductCombo:redisId0) {
                    if(key.equals(userProductCombo.getRedisKey()))
                        comboIdList.add(userProductCombo.getId());
                }

                if(comboIdList.size()>0) {
                    ComboRedisKeyParameter comboRedisKeyParameter = new ComboRedisKeyParameter();
                    comboRedisKeyParameter.setList(comboIdList);
                    comboRedisKeyParameter.setRedisKeyId(comboRedisKey.getId());
                    int i = userProductComboMapper.updateRedisKeyIds(comboRedisKeyParameter);
                    result += i;
                    Log.info(key + " 更新了" + i + "条 套餐数据");
                }

            }


        }

        Log.info("套餐 更新 redis_key_id 总共"+result+"条记录");
        return  result;
    }
    
}
