/**
 * FileName: EsGoodServiceImpl
 * Author: xivin
 * Date: 2019-09-09 20:02
 * Description:
 */
package cn.duoduo.service.impl;

import cn.duoduo.repository.EsGoodRepository;
import cn.duoduo.service.EsGoodService;
import cn.duoduo.vo.EsGood;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
