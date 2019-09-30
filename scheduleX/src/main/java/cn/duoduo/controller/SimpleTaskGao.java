package cn.duoduo.controller;

/*import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.service.JobService;
import cn.bosenkeji.util.DateUtils;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@Component
public class SimpleTaskGao extends JavaProcessor {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JobService jobService;

    @Override
    public void preProcess(JobContext context) {
        Log.info("执行前的操作");
        super.preProcess(context);
    }

    *//**
     * 定时任务执行后的操作
     * @param context
     * @return
     *//*
    @Override
    public ProcessResult postProcess(JobContext context) {

        //Log.info("执行后的操作");
        String userComboId=context.getJobParameters();
        Double score = redisTemplate.opsForZSet().score(UserComboRedisEnum.UserComboTime, userComboId);
        long jobId=context.getJobId();
        if(score!=null&&score<10) {
            Log.info(DateUtils.dateToString(new Date())+"温馨提示--用户套餐："+userComboId+"时长告急，还剩余"+score+"天！！！");
        }else if(score<=0) {
            //关闭任务
            try {
                Log.info("定时任务"+jobId+"已关闭！！！");
                jobService.disableJob(jobId);
            } catch (ClientException e) {
                Log.error("关闭定时任务"+jobId+"失败");
                e.printStackTrace();
            }

        }
        return super.postProcess(context);
    }

    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {

        Set  gt2Set= redisTemplate.opsForZSet().rangeByScore(UserComboRedisEnum.UserComboTime, 2, 1000);
        Set eq1Set = redisTemplate.opsForZSet().rangeByScore(UserComboRedisEnum.UserComboTime, 1, 1);
        //时长减少 1天
        //redisTemplate.opsForZSet().
        //redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,userComboId,-1);
        //Log.info(DateUtils.dateToString(new Date())+"定时任务："+userComboId+"已执行时长减1 工作！！！");
        //处理剩余时长大于2的用户套餐
        Iterator gt2Iterator = gt2Set.iterator();
        while (gt2Iterator.hasNext()) {
            Object next = gt2Iterator.next();
            redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,next,-1);
            System.out.println(next);
        }

        //处理剩余时长为1的套餐，即即将到期的套餐
        Iterator eq1Iterator = eq1Set.iterator();
        while (eq1Iterator.hasNext()) {
            Object next = eq1Iterator.next();
            redisTemplate.opsForZSet().incrementScore(UserComboRedisEnum.UserComboTime,next,-1);
            System.out.println(next);
        }



        return new ProcessResult(true);
    }
}*/
