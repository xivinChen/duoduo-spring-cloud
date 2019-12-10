/**
 * FileName: IndexController
 * Author: xivin
 * Date: 2019-09-17 14:38
 * Description:
 */
package cn.duoduo.controller;

import cn.duoduo.job.HelloJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

@RestController
public class IndexController {

    @Autowired
    Scheduler scheduler;

    @Resource
    private RedisTemplate redisTemplate;

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    //private final String cronExpression="0/20 * * * * ? ";
    //private final String jobGroup="userTimeJobGroup";
    //private final String triggerGroup="userTimeTriggerGroup";

    @Value("${jobGroup}")
    private String jobGroup;

    @Value("${triggerGroup}")
    private String triggerGroup;

    @Value("${cron}")
    private String cronExpression;

    @Scheduled(cron = "1/5 * * * * ?")
    @RequestMapping("/test1")
    public Object test1() {

        System.out.println("scheduled run"+ LocalDate.now());
        return LocalDate.now();

    }

    @RequestMapping("quartz")
    public Object testQuartz(Integer userid,Integer time) {
        try {

            redisTemplate.opsForZSet().add("userTime",userid,time);
        scheduler.start();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", "A1");
        jobDataMap.put("count", "20");
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity(userid+"", jobGroup)
                .setJobData(jobDataMap)
                .build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(userid+"", triggerGroup)
                .withSchedule(scheduleBuilder).build();


            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            Log.error("创建定时任务失败", e);
            return "创建定时任务失败"+e.getMessage();
        }
        return "ok";
    }

    @RequestMapping("time")
    public Object getTime() {

        return redisTemplate.opsForZSet().rangeByScoreWithScores("userTime",1,-1);

        /*RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gt("1");
        return redisTemplate.opsForZSet().rangeByLex("userTime",range);*/
    }

}
