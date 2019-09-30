package cn.duoduo.job;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.schedulerx2.model.v20190430.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JopApi {

    @Value("${regionId}")
    private String regionId;

    @Value("${accessKeyId}")
    private  String accessKeyId;

    @Value("${accessKeySecret}")
    private  String accessKeySecret;

    @Value("${productName}")
    private  String productName;

    @Value("${domain}")
    private  String domain;

    @Value("${namespace}")
    private  String namespace;

    @Value("${groupId}")
    private  String groupId;

    private String jobType="java";
    private String className="cn.duoduo.controller.SimpleTask";
    //单机模式 广播模式 等等
    private String executeMode="standalone";
    private int timeType=4;
    private String timeExpression="300";


    public void getJob() throws ClientException {
        Long jobId=14937l;
        // Open API 的接入点，具体参见支持地域列表和购买实例的地域
        String regionId = "cn-hangzhou";
        //鉴权使用的 Access Key ID
        String accessKeyId = "LTAIEBWhTbhsWByO";
        //鉴权使用的 Access Key Secret
        String accessKeySecret = "FXqHcqjJMPftFuMjH1HOZeGpAGZ7Ko";
        //产品名称
        String productName ="schedulerx2";
        //参见支持地域列表，选择 Domain
        String domain ="schedulerx.cn-hangzhou.aliyuncs.com";
        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(defaultProfile);
        //构建接口请求(根据接口中参数类型和是否必填进行填充)
        //EnableJobRequest request = new EnableJobRequest();
        GetJobInfoRequest request=new GetJobInfoRequest();
        request.setJobId(jobId);
        request.setNamespace("0055acd5-2ffd-4196-ae2a-b3f52f8e604c");
        request.setGroupId("alibaba-springboot-demo-group");
        //发送请求
        GetJobInfoResponse response = defaultAcsClient.getAcsResponse(request);
        if (response.getSuccess()) {
            System.out.println(response.getMessage());
            System.out.println("EnableJob: "+response.getRequestId());
        }
    }

    public void createJob(String jobName) throws ClientException {

        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(defaultProfile);

        //创建 请求
        CreateJobRequest createJobRequest=new CreateJobRequest();
        createJobRequest.setNamespace(namespace);
        createJobRequest.setGroupId(groupId);
        createJobRequest.setJobType(jobType);
        createJobRequest.setClassName(className);
        //createJobRequest.setJarUrl(jarUrl);
        createJobRequest.setName(jobName);
        createJobRequest.setDescription(jobName);
        createJobRequest.setExecuteMode(executeMode);
        createJobRequest.setTimeType(timeType);
        createJobRequest.setTimeExpression(timeExpression);
        createJobRequest.setParameters(jobName);



        CreateJobResponse createJobResponse=defaultAcsClient.getAcsResponse(createJobRequest);

        if(createJobResponse.getSuccess()) {

            System.out.println("成功了");
            System.out.println(createJobResponse);

        }

    }

    public void enableJob(long jobId) throws ClientException {

        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        EnableJobRequest enableJobRequest=new EnableJobRequest();
        enableJobRequest.setGroupId(groupId);
        enableJobRequest.setNamespace(namespace);
        enableJobRequest.setJobId(jobId);
        /*enableJobRequest.setNamespace("0055acd5-2ffd-4196-ae2a-b3f52f8e604c");
        enableJobRequest.setGroupId("alibaba-springboot-demo-group");*/

        EnableJobResponse response=defaultAcsClient.getAcsResponse(enableJobRequest);
        if(response.getSuccess())
            System.out.println(response);

    }

    public void disableJob(long jobId) throws ClientException {
        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        DisableJobRequest request=new DisableJobRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);

        DisableJobResponse response=defaultAcsClient.getAcsResponse(request);
        if(response.getSuccess())
            System.out.println(response);
        else {
            System.out.println("操作失败");
            System.out.println(response);
        }
    }

    public void updateJob(long jobId) throws ClientException {
        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        UpdateJobRequest request=new UpdateJobRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);
        UpdateJobResponse response=defaultAcsClient.getAcsResponse(request);
        if(response.getSuccess())
            System.out.println(response);
        else
            System.out.println(response);
    }

    public DeleteJobResponse deleteJob(long jobId) throws ClientException {

        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        DeleteJobRequest request=new DeleteJobRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);
        DeleteJobResponse response=defaultAcsClient.getAcsResponse(request);
        return response;

    }

    public ExecuteJobResponse executeJob(long jobId) throws ClientException {
        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        ExecuteJobRequest request=new ExecuteJobRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);
        ExecuteJobResponse response=defaultAcsClient.getAcsResponse(request);
        return response;

    }

    public GetJobInfoResponse getJobInfo(long jobId) throws ClientException {
        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        GetJobInfoRequest request=new GetJobInfoRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);
        GetJobInfoResponse response=defaultAcsClient.getAcsResponse(request);
        return response;

    }


    public DefaultAcsClient getDefaultAcsClient() {

        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(defaultProfile);
        return defaultAcsClient;
    }





}
