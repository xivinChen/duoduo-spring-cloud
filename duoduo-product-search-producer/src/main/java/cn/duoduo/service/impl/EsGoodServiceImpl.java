/**
 * FileName: EsGoodServiceImpl
 * Author: xivin
 * Date: 2019-09-09 20:02
 * Description:
 */
package cn.duoduo.service.impl;

import cn.duoduo.enums.good.GoodCatEnum;
import cn.duoduo.enums.good.GoodSortEnum;
import cn.duoduo.repository.EsGoodRepository;
import cn.duoduo.service.EsGoodService;
import cn.duoduo.vo.EsGood;
import cn.duoduo.vo.GoodCat;
import cn.duoduo.vo.qingtaoke.QingTaoKeSearch;
import io.swagger.models.auth.In;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class EsGoodServiceImpl implements EsGoodService {

    @Resource
    private EsGoodRepository esGoodRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public int saveOne(EsGood esGood) {
        EsGood save = esGoodRepository.save(esGood);
        return 1;
    }

    @Override
    public int deleteAll() {
        this.esGoodRepository.deleteAll();
        return 1;
    }

    @Override
    public Page<EsGood> findByCat(Integer goods_cat,int page,int pageSize) {
        Pageable pageable=PageRequest.of(page,pageSize);
        SearchQuery query=new NativeSearchQueryBuilder().withPageable(pageable).withQuery(QueryBuilders.termQuery("goods_cat",goods_cat)).build();
        //NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("goods_cat", goods_cat))).build();
        return esGoodRepository.search(query);
    }

    @Override
    public int saveAll(List<EsGood> list) {

        Iterable<EsGood> esGoods = esGoodRepository.saveAll(list);
        Iterator<EsGood> iterator = esGoods.iterator();
        int result=0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public List<GoodCat> findCatList() {

        SearchQuery build = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .withIndices("goods").withTypes("goods")
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .addAggregation(AggregationBuilders.terms("goods_cat").field("goods_cat")).build();

        Aggregations aggregations = elasticsearchTemplate.query(build, searchResponse -> searchResponse.getAggregations());
        Map<String, Aggregation> map=aggregations.asMap();
        LongTerms terms=(LongTerms) map.get("goods_cat");
        /*Map<String,Long> bucket=terms.getBuckets().stream().collect(Collectors.toMap(
                StringTerms.Bucket::getKeyAsString,
                InternalTerms.Bucket::getDocCount,
                (x,y)->x)
        );*/

        List<GoodCat> result=new ArrayList<>();
        List<LongTerms.Bucket> buckets = terms.getBuckets();
        Iterator<LongTerms.Bucket> iterator=buckets.iterator();
        int num=0;
        while (iterator.hasNext()) {
            num++;
            LongTerms.Bucket next = iterator.next();
            GoodCat goodCat=new GoodCat();
            Integer id=next.getKeyAsNumber().intValue();
            goodCat.setId(id);
            goodCat.setNumber(next.getDocCount());
            String name="";
            switch (id) {
                case 0:
                    name= GoodCatEnum._0ALL;
                    break;
                case 2:
                    name=GoodCatEnum._2MU_YING;
                    break;
                case 3:
                    name=GoodCatEnum._3MEI_ZHUANG;
                    break;
                case 4:
                    name=GoodCatEnum._4JIA_JU;
                    break;
                case 5:
                    name=GoodCatEnum._5BAO_XIE;
                    break;
                case 6:
                    name=GoodCatEnum._6MEI_SHI;
                    break;
                case 7:
                    name=GoodCatEnum._7WEN_TI;
                    break;
                case 8:
                    name=GoodCatEnum._8DIAN_SHU_MA;
                    break;
                case 9:
                    name=GoodCatEnum._9OTHER;
                    break;
                case 10:
                    name=GoodCatEnum._10NV_ZHUANG;
                    break;
                case 11:
                    name=GoodCatEnum._11NEI_YI;
                    break;
                case 12:
                    name=GoodCatEnum._12NAN_ZHUANG;
                    break;
            }
            goodCat.setName(name);
            result.add(goodCat);
        }
        System.out.println("num:"+num);

        return  result;
    }

    @Override
    public Object list() {
        return esGoodRepository.findAll();
    }

    @Override
    public Page<EsGood> search(QingTaoKeSearch qingTaoKeSearch, int pageNum, int pageSize) {
        Pageable pageable= PageRequest.of(pageNum,pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withPageable(pageable);

        //类别过滤
        Integer cat=qingTaoKeSearch.getCat();
        if(cat!=null&&cat>0) {
            BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
            boolQueryBuilder.must(QueryBuilders.termQuery("goods_cat",cat));
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }

        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders=new ArrayList<>();

        String key_word=qingTaoKeSearch.getKey_word();
        //没有关键字检索
        if(StringUtils.isEmpty(key_word)) {
            Integer min_price=qingTaoKeSearch.getMin_price();
            Integer max_price=qingTaoKeSearch.getMax_price();
            if(min_price!=null&&max_price!=null&&min_price<max_price) {
                filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        QueryBuilders.rangeQuery("goods_price").from(min_price).to(max_price),ScoreFunctionBuilders.weightFactorFunction(5)));
                FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders=new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];

                filterFunctionBuilders.toArray(builders);
                FunctionScoreQueryBuilder functionScoreQueryBuilder=QueryBuilders.functionScoreQuery(builders)
                        .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                        .setMinScore(2);
                nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
            }
            /*filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchAllQuery(),
                    ScoreFunctionBuilders.weightFactorFunction(10)));*/
            else
                nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());

        }else {
            //有关键字检索
            //List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders=new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("goods_title",key_word)
                    , ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("goods_short_title",key_word)
                    ,ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("goods_introduce",key_word)
                    ,ScoreFunctionBuilders.weightFactorFunction(2)));
            //范围查询
            Integer min_price=qingTaoKeSearch.getMin_price();
            Integer max_price=qingTaoKeSearch.getMax_price();
            if(min_price!=null&&max_price!=null&&min_price<max_price) {
                filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        QueryBuilders.rangeQuery("goods_price").from(min_price).to(max_price),ScoreFunctionBuilders.weightFactorFunction(5)));
            }
            //filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.))

            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders=new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];

            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder=QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);

        }

        NativeSearchQuery build = nativeSearchQueryBuilder.build();


        //排序
        Integer sort=qingTaoKeSearch.getSort();
        if(sort!=null) {
            if(sort==GoodSortEnum.PERSON_NUMBER) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("goods_sales").order(SortOrder.DESC));
            }
            else if (sort==GoodSortEnum.MOST_NEW) {
                //nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("goods_sales").order(SortOrder.ASC));
            }
            else if (sort==GoodSortEnum.MOST_SALE) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("goods_sales").order(SortOrder.DESC));
            }
            else if (sort==GoodSortEnum.MOST_PRICE) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("goods_price").order(SortOrder.DESC));
            }
            else if (sort==GoodSortEnum.MIN_PRICE) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("goods_price").order(SortOrder.ASC));
            }
        }
        /*if(qingTaoKeSearch.getPriceId()!=null&&qingTaoKeSearch.getPriceId()>0)
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        else
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
*/

        Page<EsGood> search = esGoodRepository.search(build);
        return  search;
    }
}
