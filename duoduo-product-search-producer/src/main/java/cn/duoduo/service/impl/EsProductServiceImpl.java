/**
 * FileName: EsProductERServiceImpl
 * Author: xivin
 * Date: 2019-09-04 14:56
 * Description:
 */
package cn.duoduo.service.impl;

import cn.duoduo.repository.EsProductRepository;
import cn.duoduo.service.EsProductService;
import cn.duoduo.vo.EsProduct;
import cn.duoduo.vo.EsProductSearch;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EsProductServiceImpl implements EsProductService {

    /*@Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private EsProductRepository esProductRepository;*/
    @Override
    public int save(EsProduct EsProduct) {
        //esProductRepository.save(EsProduct);
        return 1;
    }

    @Override
    public int delete(int id) {
        return 1;
    }

    @Override
    public int update(EsProduct EsProduct) {
        return 0;
    }

    @Override
    public Object list() {
        //Iterable<EsProduct> all = esProductRepository.findAll();
        return null;
    }

    @Override
    public Page<EsProduct> simpleSearch(String keyWord, int pageNum, int pageSize) {
       /* PageRequest page = PageRequest.of(pageNum, pageSize);
        //return esProductRepository.findByTitleOrDescribe(keyWord,keyWord,keyWord,page);
        return esProductRepository.findEsProductsByNameOrTitle(keyWord,keyWord,keyWord,page);*/
       return null;
    }

    @Override
    public Page<EsProduct> search(EsProductSearch esProductSearch, int pageNum, int pageSize) {
        Pageable pageable= PageRequest.of(pageNum,pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withPageable(pageable);

        //性别过滤
        if(esProductSearch.getCategoryId()!=null&&esProductSearch.getCategoryId()>0) {
            BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
            boolQueryBuilder.must(QueryBuilders.termQuery("categoryId",esProductSearch.getCategoryId()));
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }

        String keyWord=esProductSearch.getKeyWord();
        //没有关键字检索
        if(StringUtils.isEmpty(keyWord)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        }else {
            //有关键字检索
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders=new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("name",keyWord)
                    , ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("title",keyWord)
                    ,ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("describe",keyWord)
                    ,ScoreFunctionBuilders.weightFactorFunction(2)));
            //范围查询
            Integer sortId=esProductSearch.getSortId();
            if(sortId!=null&&sortId>0) {
                if(sortId==1)
                    filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            QueryBuilders.rangeQuery("price").from(1).to(20),ScoreFunctionBuilders.weightFactorFunction(5)));
                else if(sortId==2) {
                    filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            QueryBuilders.rangeQuery("price").from(20,false).to(40),
                            ScoreFunctionBuilders.weightFactorFunction(2)));
                }
                else if(sortId==3)
                    filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            QueryBuilders.rangeQuery("price").from(40).to(59),
                            ScoreFunctionBuilders.weightFactorFunction(5)));
                else if(sortId==4)
                    filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            QueryBuilders.rangeQuery("price").from(60).to(99),
                            ScoreFunctionBuilders.weightFactorFunction(5)));
                else
                    filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            QueryBuilders.rangeQuery("price").gt(99),
                            ScoreFunctionBuilders.weightFactorFunction(5)));
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
        if(esProductSearch.getPriceId()!=null&&esProductSearch.getPriceId()>0)
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        else
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));


        //Page<EsProduct> search = esProductRepository.search(build);

        return  null;
    }

    /*public void getCatList() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.addAggregation()
    }
*/

}
