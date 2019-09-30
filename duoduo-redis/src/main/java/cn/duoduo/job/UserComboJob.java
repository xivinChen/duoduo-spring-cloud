/**
 * FileName: UserComboJob
 * Author: xivin
 * Date: 2019-09-18 14:43
 * Description:
 */
package cn.duoduo.job;

import cn.duoduo.vo.UserComboRedisEnum;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class UserComboJob extends QuartzJobBean {

    //@Value("${jobGroup}")
    private String jobGroup="userComboJobGroup";

    //@Value("${triggerGroup}")
    private String triggerGroup="userComboTriggerGroup";
    
    //private final String userComboTime="userComboTime";

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Integer userComboId=Integer.valueOf(jobExecutionContext.getJobDetail().getKey().getName());
        String userComboIdStr=String.valueOf(userComboId);
        //redisTemplate.opsForZSet().add("user","1",1);
        //redisTemplate.opsForZSet().score(UserComboRedisEnum.UserComboTime,1+"");
        Double score = redisTemplate.opsForZSet().score(UserComboRedisEnum.UserComboTime, userComboId + "");
        if(score==null) {
            pauseJob(jobExecutionContext,userComboId);
            Log.warn("userComboId:"+userComboId+"记录 在redis中不存在，定时任务 "+jobGroup+userComboId+"暂停执行！");
        }


        //时长减少 1天
        redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,userComboId+"",-1);
        Log.info("定时任务："+jobGroup+userComboId+"已执行时长减1 工作！！！");

        //剩余时长低于十天 需要发邮箱提醒
        //if(redisTemplate.opsForZSet().score(UserComboRedisEnum.UserComboTime,userComboId+"")<10) {
        if(score<=11) {
            Log.info("温馨提示：用户时长"+userComboId+"还剩余"+score+"天，请续费！！！");
        }

        //如果剩余时长为0则暂停定时任务
        if(score<=1) {
            pauseJob(jobExecutionContext,userComboId);
        }

        Log.info("----------------------------------------------------");

    }

    //暂停 定时任务方法
    void pauseJob(JobExecutionContext jobExecutionContext,int userComboId) {
        try {

            jobExecutionContext.getScheduler().pauseJob(JobKey.jobKey(userComboId+"",jobGroup));
            Log.info("定时任务："+jobGroup+userComboId+"已暂停执行！");
        }
        catch (SchedulerException e) {
            Log.error("定时任务："+jobGroup+userComboId+"暂停时发生异常");
        }

    }

    //删除定时任务方法
    void deleteJob(JobExecutionContext jobExecutionContext,int userComboId) {
        try {
            jobExecutionContext.getScheduler().pauseTrigger(TriggerKey.triggerKey(userComboId + "", triggerGroup));
            jobExecutionContext.getScheduler().unscheduleJob(TriggerKey.triggerKey(userComboId+"",triggerGroup));
            jobExecutionContext.getScheduler().deleteJob(JobKey.jobKey(userComboId+"",jobGroup));
            //jobExecutionContext.getScheduler().pauseJob(JobKey.jobKey(userComboId + "", jobGroup));
            Log.info("定时任务："+jobGroup+userComboId+"已暂停执行！");
        }
        catch (SchedulerException e) {
            Log.error("定时任务："+jobGroup+userComboId+"暂停时发生异常");
        }
    }
}
