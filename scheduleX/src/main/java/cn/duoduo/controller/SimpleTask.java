package cn.duoduo.controller;


import cn.duoduo.vo.DateUtils;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SimpleTask extends JavaProcessor {

    private final Logger Log = LoggerFactory.getLogger(this.getClass());



    @Override
    public void preProcess(JobContext context) {
        Log.info("执行前的操作");
        super.preProcess(context);
    }

    /**
     * 定时任务执行后的操作
     * @param context
     * @return
     */
    @Override
    public ProcessResult postProcess(JobContext context) {

        Log.info("执行后的操作");

        return super.postProcess(context);
    }

    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {

        Log.info("schedulex2 running ---"+jobContext.getJobId()+ DateUtils.dateToString(new Date()));
        //Log.info(DateUtils.dateToString(new Date())+"定时任务："+userComboId+"已执行时长减1 工作！！！");

        return new ProcessResult(true);
    }
}
