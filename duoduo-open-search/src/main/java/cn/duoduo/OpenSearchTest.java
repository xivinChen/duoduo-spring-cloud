package cn.duoduo;

import cn.duoduo.vo.Order;
import cn.duoduo.vo.*;
import com.alibaba.fastjson.JSON;
import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONObject;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchParamsBuilder;
import com.aliyun.opensearch.util.JsonUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OpenSearchTest {

    private static String appName = "bs_ccr_gao";
    private static String accesskey = "LTAIEBWhTbhsWByO";
    private static String secret = "FXqHcqjJMPftFuMjH1HOZeGpAGZ7Ko";
    private static String host = "http://opensearch-cn-qingdao.aliyuncs.com";

    private static String orderTable="order";
    private static String orderGroupTable="order_group";


    public static void main(String[] args) {

        testPushOrder(100);
        testSearchQuery();
        //testPushOrderGroup();
        /*for(int i=31;i<40;i++) {
            testPushOrder(i);
        }*/

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

    public static void testPushOrder(int id) {
        Field<Order> field=new Field<>();
        Order order=new Order();
        order.setId(id);
        order.setCoin_pair_choice_id(2);
        order.setOrderGroupId(4);
        order.setTrade_type(1);
        order.setTradeNumber(10);
        order.setTrade_average_price(5);
        order.setTrade_cost(30);
        order.setCreated_at(Timestamp.valueOf(LocalDateTime.now()).getTime());
        field.setFields(order);
        testPush(field,orderTable);
    }

    public static void testSearchQuery() {
        OpenSearchClient openSearchClient = getOpenSearchClient();

        OpenSearchPage page=new OpenSearchPage();
        page.setPageNum(1);
        page.setPageSize(5);
        page.countStartRow();

        //创建 searcherClient
        SearcherClient searcherClient = new SearcherClient(openSearchClient);

        Config config = new Config(Lists.newArrayList(appName));

        //设置分页
        config.setStart(page.getStartRow());
        config.setHits(page.getPageSize());

        //返回格式
        config.setSearchFormat(SearchFormat.FULLJSON);

        Lists.newArrayList("id", "order_group_id", "coin_pair_choice_id", "trade_profit_price", "trade_number", "trade_cost", "trade_type", "status", "created_at", "updated_at");
        //设置字段
        config.setFetchFields(Lists.newArrayList("id","order_group_id","coin_pair_choice_id","trade_profit_price","trade_number","trade_cost","trade_type","status","created_at","updated_at"));

        SearchParams searchParams = new SearchParams(config);

        searchParams.setQuery("coin_pair_choice_id:'1' OR coin_pair_choice_id:'2' ");

        //排序
        Sort sort=new Sort();
        //sort.addToSortFields(new SortField("trade_average", com.aliyun.opensearch.sdk.generated.search.Order.INCREASE));
        sort.addToSortFields(new SortField("order_group_id", com.aliyun.opensearch.sdk.generated.search.Order.DECREASE));

        Aggregate aggregate=new Aggregate();
        aggregate.setGroupKey("order_group_id");
        aggregate.setAggFun("count()");
        //aggregate.setAggFun("sum(t)");
        searchParams.addToAggregates(aggregate);

        //Aggregate aggregate1=new Aggregate();
        //aggregate1.setGroupKey("")


        SearchParamsBuilder searchParamsBuilder = SearchParamsBuilder.create(searchParams);

        //searchParamsBuilder.addSummary("name",50,"em","...",1);

        searchParamsBuilder.addFilter("id=100","AND");
        //searchParamsBuilder.addFilter("trade_type=2","AND");

        //执行查询
        try {

            SearchResult execute = searcherClient.execute(searchParamsBuilder);
            String result = execute.getResult();
            JSONObject jsonObject = new JSONObject(result);
            Object openResult = jsonObject.get("result");
            Result result1 = com.alibaba.fastjson.JSONObject.parseObject(result, Result.class);
            System.out.println("result1 = " + result1);
            System.out.println("total"+result1.getResult().getTotal()+"items"+result1.getResult().getItems());
            /*String s = JSON.toJSONString(openResult);
            String s1 = com.alibaba.fastjson.JSONObject.toJSONString(openResult);

            System.out.println("s = " + s1);
            OpenSearchResult openSearchResult = JSON.parseObject(s1, OpenSearchResult.class);
            System.out.println("openSearchResult = " + openSearchResult);
            System.out.println("result1 = " + openResult);
            System.out.println("jsonObject = " + jsonObject);
            JSONObject resultJson = new JSONObject(openResult);*/
            //Object total = resultJson.get("total");
           /* resultJson.get("errors");
            resultJson.get("status");
            Object items = resultJson.get("items");*/
            //System.out.println("total"+total+";items"+items);


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testPushOrderGroup() {
        Field<OrderGroup> field=new Field<>();
        OrderGroup group=new OrderGroup();
        group.setId(3);
        group.setName("20161031");
        group.setEnd_profit_ratio(1);
        group.setEnd_type(1);
        group.setIs_end(1);
        group.setCreate_at(Timestamp.valueOf(LocalDateTime.now()));

        field.setFields(group);
        testPush(field,orderGroupTable);
    }

    public static void testPush(Field field,String tableName) {

        OpenSearchClient openSearchClient = getOpenSearchClient();

        field.setCmd("ADD");

        List<Field> list=new ArrayList<>();
        list.add(field);

        String json = JsonUtil.toJson(list);
        System.out.println("s = " + json);


        DocumentClient documentClient = new DocumentClient(openSearchClient);
        try {
            OpenSearchResult push = documentClient.push(json, appName, tableName);
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
