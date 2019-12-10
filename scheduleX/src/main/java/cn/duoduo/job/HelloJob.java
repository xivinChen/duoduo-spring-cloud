/**
 * FileName: HelloJob
 * Author: xivin
 * Date: 2019-09-17 14:48
 * Description:
 */
package cn.duoduo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

public class HelloJob extends QuartzJobBean {

    @Value("${jobGroup}")
    private String jobGroup;

    @Value("${triggerGroup}")
    private String triggerGroup;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        Integer userId=Integer.valueOf(jobExecutionContext.getJobDetail().getKey().getName());

        //剩余时长低于十天 需要发邮箱提醒
        if(redisTemplate.opsForZSet().score("userTime",userId)<10) {
            Log.info("用户时长还剩余"+redisTemplate.opsForZSet().score("userTime",userId)+"天，请续费！！！");
        }

        //时长减少 1天
        redisTemplate.opsForZSet().incrementScore("userTime",userId,-1);
        Log.info("定时任务："+jobGroup+userId+"已执行时长减1 工作！！！");

        //如果剩余时长为0则结束定时任务
        if(redisTemplate.opsForZSet().score("userTime",userId)<=0) {
            try {
                jobExecutionContext.getScheduler().pauseTrigger(TriggerKey.triggerKey(userId + "", triggerGroup));
                jobExecutionContext.getScheduler().unscheduleJob(TriggerKey.triggerKey(userId+"",triggerGroup));
                jobExecutionContext.getScheduler().deleteJob(JobKey.jobKey(userId+"",jobGroup));
                //jobExecutionContext.getScheduler().pauseJob(JobKey.jobKey(userId + "", jobGroup));
                Log.info("定时任务："+jobGroup+userId+"已停止执行！");
            }
            catch (SchedulerException e) {
                Log.error("定时任务："+jobGroup+userId+"删除发生异常");
            }
        }

        //System.out.println("hello job--"+jobExecutionContext.getJobDetail().getKey().getName());
    }


}
