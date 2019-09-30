package cn.duoduo.controller;


import cn.duoduo.job.JopApi;
import cn.duoduo.vo.DateUtils;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class TaskController {

    //@Resource
    //private TestService testService;


    @Resource
    JopApi jopApi;

    @RequestMapping("/")
    public String index() {
        return "hello welcome!!";
    }

    @GetMapping("/add")
    public void createJob(String jobName) {
        try {
            jopApi.createJob(jobName);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/enable")
    public String enableJob(Long jobId) throws ClientException {
        jopApi.enableJob(jobId);
        return "ok";
    }

    @GetMapping("/disable")
    public String disable(Long jobId) throws ClientException {
        jopApi.disableJob(jobId);
        return "ok";
    }

    @GetMapping("/info")
    public Object getJobInfo(Long jobId) throws ClientException {
        return jopApi.getJobInfo(jobId);
    }

    @GetMapping("/delete")
    public Object delete(Long jobId) throws ClientException {
        return jopApi.deleteJob(jobId);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello index";
    }

}
