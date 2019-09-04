/**
 * FileName: ProductERServiceImpl
 * Author: xivin
 * Date: 2019-09-04 14:56
 * Description:
 */
package cn.duoduo.service.impl;

import cn.duoduo.repository.ProductERRepository;
import cn.duoduo.service.ProductERService;
import cn.duoduo.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductERServiceImpl implements ProductERService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ProductERRepository productERRepository;
    @Override
    public int save(Product product) {
        productERRepository.save(product);
        return 1;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public Object list() {
        Iterable<Product> all = productERRepository.findAll();
        return all;
    }

    @Override
    public int simpleSearch(String keyWord, int pageNum, int pageSize) {
        return 0;
    }

    @Override
    public int search(String keyWord, int categoryId, int sortId, int pageNum, int pageSize) {
        return 0;
    }
}
