/**
 * FileName: EsGoodServiceImpl
 * Author: xivin
 * Date: 2019-09-09 20:02
 * Description:
 */
package cn.duoduo.service.impl;

import cn.duoduo.enums.good.GoodSortEnum;
import cn.duoduo.repository.EsGoodRepository;
import cn.duoduo.service.EsGoodService;
import cn.duoduo.vo.EsGood;
import cn.duoduo.vo.EsProduct;
import cn.duoduo.vo.qingtaoke.QingTaoKeSearch;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class EsGoodServiceImpl implements EsGoodService {

    @Resource
    private EsGoodRepository esGoodRepository;

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
