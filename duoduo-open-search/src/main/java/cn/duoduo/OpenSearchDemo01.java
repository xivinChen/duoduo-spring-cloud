package cn.duoduo;

import cn.duoduo.vo.Field;
import cn.duoduo.vo.TestDemo;
import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Maps;
import com.aliyun.opensearch.sdk.dependencies.com.google.gson.JsonArray;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONArray;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONObject;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.document.Command;
import com.aliyun.opensearch.sdk.generated.document.DocumentConstants;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.aliyun.opensearch.util.JsonUtil;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OpenSearchDemo01 {

    private static String appName = "bs_ccr_test";
    private static String accesskey = "LTAIEBWhTbhsWByO";
    private static String secret = "FXqHcqjJMPftFuMjH1HOZeGpAGZ7Ko";
    private static String host = "http://opensearch-cn-qingdao.aliyuncs.com";

    private static String tableName="demo";

    public static void main(String[] args) {

       //testCommit();
        testPush(7);
        //testQuery();
        searchDemo();

    }

    public static void Menu() {
        testQuery();
        testPush(1);
    }

    public static void searchDemo() {
        //查看文件和默认编码格式
        System.out.println(String.format("file.encoding: %s", System.getProperty("file.encoding")));
        System.out.println(String.format("defaultCharset: %s", Charset.defaultCharset().name()));

        //创建openSearch
        OpenSearch openSearch = new OpenSearch(accesskey, secret, host);

        //创建openSearchClient 对象
        OpenSearchClient openSearchClient = new OpenSearchClient(openSearch);

        //创建 searcherClient
        SearcherClient searcherClient = new SearcherClient(openSearchClient);

        Config config = new Config(Lists.newArrayList(appName));

        //设置分页
        config.setStart(0);
        config.setHits(15);

        //返回格式
        config.setSearchFormat(SearchFormat.FULLJSON);

        //设置字段
        config.setFetchFields(Lists.newArrayList("id","name","phone","int_arr","literal_arr","float_arr","cate_id"));

        SearchParams searchParams = new SearchParams(config);

        searchParams.setQuery("name:'kevin'");

      /*  //聚合打散句子
        Distinct distinct = new Distinct();
        distinct.setKey("cate_id");
        distinct.setDistCount(1);
        distinct.setDistTimes(1);
        *//*distinct.setReserved(false);
        distinct.setUpdateTotalHit(false);
        distinct.setDistFilter("cate_id<=3");
        distinct.setGrade("1.2");*//*

        searchParams.addToDistincts(distinct);*/

        //统计句子

       /* Aggregate aggregate = new Aggregate();
        aggregate.setGroupKey("cate_id");
        aggregate.setAggFun("count()");
        aggregate.setAggFilter("cate_id=1");
        aggregate.setRange("0-10");
        aggregate.setAggSamplerThresHold("5"); //设置采样阀值
        aggregate.setAggSamplerStep("5");  //设置采样步长
        aggregate.setMaxGroup("5");  //设置最大返回组数

        searchParams.addToAggregates(aggregate);*/

        //sort  条件 排序
        Sort sort = new Sort();
        sort.addToSortFields(new SortField("cate_id",Order.INCREASE));
        sort.addToSortFields(new SortField("RANK",Order.INCREASE));

        searchParams.setSort(sort);

        /*//设置粗排表达式
        Rank rank = new Rank();
        rank.setFirstRankName("default");
        rank.setFirstRankName("default");
        rank.setReRankSize(5);

        searchParams.setRank(rank);*/


        SearchParamsBuilder searchParamsBuilder = SearchParamsBuilder.create(searchParams);
        searchParamsBuilder.addSummary("name",50,"em","...",1);

        //searchParamsBuilder.addFilter("id>=0","AND");
        searchParamsBuilder.addFilter("id>3 && cate_id>1","AND");


        //执行查询
        try {

            SearchResult execute = searcherClient.execute(searchParamsBuilder);
            String result = execute.getResult();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("jsonObject = " + jsonObject);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void testQuery() {
        OpenSearchClient openSearchClient = getOpenSearchClient();

        //创建 searcherClient
        SearcherClient searcherClient = new SearcherClient(openSearchClient);

        Config config = new Config(Lists.newArrayList(appName));

        //设置分页
        config.setStart(0);
        config.setHits(5);

        //返回格式
        config.setSearchFormat(SearchFormat.FULLJSON);

        //设置字段
        config.setFetchFields(Lists.newArrayList("id","name","phone","int_arr","literal_arr","float_arr","cate_id"));

        SearchParams searchParams = new SearchParams(config);

        searchParams.setQuery("name:'kevin'");

        SearchParamsBuilder searchParamsBuilder = SearchParamsBuilder.create(searchParams);

        searchParamsBuilder.addSummary("name",50,"em","...",1);

        //searchParamsBuilder.addFilter("cate_id>0","AND");

        //执行查询
        try {

            SearchResult execute = searcherClient.execute(searchParamsBuilder);
            String result = execute.getResult();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("jsonObject = " + jsonObject);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * get openSearchClient
     * @return
     */
    public static OpenSearchClient getOpenSearchClient() {
        //创建openSearch
        OpenSearch openSearch = new OpenSearch(accesskey, secret, host);

        //创建openSearchClient 对象
        OpenSearchClient openSearchClient = new OpenSearchClient(openSearch);
        return openSearchClient;
    }

    public static void testCommit() {

        //查看文件和默认编码格式
        /*System.out.println(String.format("file.encoding: %s", System.getProperty("file.encoding")));
        System.out.println(String.format("defaultCharset: %s", Charset.defaultCharset().name()));*/
        //生成随机数，作为主键值
        Random rand = new Random();
        int value = rand.nextInt(Integer.MAX_VALUE);

        Map<String,Object> doc1 = Maps.newLinkedHashMap();
        doc1.put("id",value);
        String name = "xivin";
        byte[] name_byte;
        try {
            name_byte = name.getBytes("utf-8");
            String utf_string=new String(name_byte);
            doc1.put("name",utf_string);

        }catch (Exception e) {
            e.printStackTrace();
        }

        int[] int_arr={11,22};
        float[] float_arr={1.1f,2.2f};
        String[] literal_arr = {"hello","openSearch"};
        doc1.put("int_arr",int_arr);
        doc1.put("float_arr",float_arr);
        doc1.put("literal_arr",literal_arr);
        doc1.put("phone","13556559840");
        doc1.put("cate_id",1);

        OpenSearch openSearch=new OpenSearch(accesskey,secret,host);
        OpenSearchClient openSearchClient = new OpenSearchClient(openSearch);
        DocumentClient documentClient = new DocumentClient(openSearchClient);

        //把doc1 加入缓存，并设置为新增文档
        documentClient.add(doc1);

        System.out.println(doc1.toString());

        try {

            //提交新增操作
            OpenSearchResult commit = documentClient.commit(appName, tableName);

            if(commit.getResult().equalsIgnoreCase("true")) {

                System.out.println("用户推送成功，以下为getTraceInfo推送请求id:"+commit.getTraceInfo().getRequestId());

            }else {

                System.out.println("用户推送失败！"+commit.getTraceInfo());

            }


        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void testPush(int id) {

        OpenSearchClient openSearchClient = getOpenSearchClient();

        TestDemo testDemo=new TestDemo();
        int[] ints={1,2};
        testDemo.setId(id);
        testDemo.setName("kevin");
        testDemo.setPhone("15814516363");
        testDemo.setCate_id(1);
        testDemo.setInt_arr(ints);
        Field field = new Field();
        field.setFields(testDemo);
        field.setCmd("ADD");

        List<Field> list=new ArrayList<>();
        list.add(field);

        JSONObject json=new JSONObject();
        json.put(DocumentConstants.DOC_KEY_CMD, Command.ADD.toString());
        json.put(DocumentConstants.DOC_KEY_FIELDS,testDemo);

        String s = JsonUtil.toJson(list);
        System.out.println("s = " + s);


        JSONArray jsonElements = new JSONArray();
        jsonElements.put(json);

        System.out.println("jsonElements = " + jsonElements.toString());
        DocumentClient documentClient = new DocumentClient(openSearchClient);
        try {
            OpenSearchResult push = documentClient.push(s, appName, tableName);
            if(push.getResult().equalsIgnoreCase("true")) {
                System.out.println("用户推送成功! getTraceInfo 推送请求id为"+push.getTraceInfo().getRequestId());
            }else {
                System.out.println("用户推送失败！"+push.getTraceInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


    }


}
