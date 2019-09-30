/**
 * FileName: MySchedule
 * Author: xivin
 * Date: 2019-09-20 16:48
 * Description:
 */
package cn.duoduo.job;


import cn.duoduo.vo.DateUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MySchedule {

    private String jobGroup="userComboJobGroup";
    private String triggerGroup="userComboTriggerGroup";

    private final String cronExpression="0 0/1 * * * ?";

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private Scheduler scheduler;

    //schedule job
    public void scheduleJob(String jobName,String triggerName) throws SchedulerException {

        scheduler.start();

        JobDetail jobDetail = getJobDetail(jobName,jobGroup);

        Trigger trigger=getTrigger(triggerName,triggerGroup);

        scheduler.scheduleJob(jobDetail, trigger);

    }

    //重新schedule job 重新触发
    public void rescheduleJob(String triggerName) throws SchedulerException {
        scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName,triggerGroup),getTrigger(triggerName,triggerGroup));
    }

    public JobDetail getJobDetail(String jobName,String jobGroup) {

        JobDataMap jobDataMap = new JobDataMap();
        //构造job信息
        return  JobBuilder.newJob(UserComboJob.class)
                .withIdentity(jobName,jobGroup)
                .setJobData(jobDataMap)
                .build();

    }

    public SimpleScheduleBuilder getSimpleSchedule() {

        //返回一个简单的scheduleBuilder 它每隔24小时 执行一遍 且永远重复执行
        return SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).repeatForever();

        //同样 返回一个简单的scheduleBuilder 它每隔1分钟执行一遍 且永远重复执行
        //return SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever();
    }


    public CronScheduleBuilder getCronSchedule() {

        //返回cron表达式的schedule 关于cron表达式……
        return CronScheduleBuilder.cronSchedule(cronExpression);
    }

    public Trigger getTrigger(String triggerName,String triggerGroup) {

        return TriggerBuilder.newTrigger().withIdentity(triggerName,triggerGroup)
                .withSchedule(getSimpleSchedule())
                //开始触发时间 24小时后开始 可换成其他时间
                .startAt(DateUtils.getTomorrow())
                .build();

    }


    public CronTrigger getCronTrigger(String triggerName,String triggerGroup) {
        return TriggerBuilder.newTrigger().withIdentity(triggerName,triggerGroup)
                .withSchedule(getCronSchedule())
                .build();
    }

    //删除定时任务方法
    public int deleteJob(int userComboId) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(userComboId + "", triggerGroup));
            scheduler.unscheduleJob(TriggerKey.triggerKey(userComboId+"",triggerGroup));
            scheduler.deleteJob(JobKey.jobKey(userComboId+"",jobGroup));
            //jobExecutionContext.getScheduler().pauseJob(JobKey.jobKey(userComboId + "", jobGroup));
            Log.info("定时任务："+jobGroup+userComboId+"已暂停执行！");
            return 1;
        }
        catch (SchedulerException e) {
            e.printStackTrace();
            Log.error("定时任务："+jobGroup+userComboId+"暂停时发生异常");
            return 0;
        }
    }



}
