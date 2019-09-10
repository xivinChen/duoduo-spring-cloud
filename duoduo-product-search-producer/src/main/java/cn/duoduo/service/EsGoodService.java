/**
 * FileName: EsGoodService
 * Author: xivin
 * Date: 2019-09-09 20:00
 * Description:
 */
package cn.duoduo.service;

import cn.duoduo.vo.EsGood;
import cn.duoduo.vo.qingtaoke.QingTaoKeSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EsGoodService {

    int saveOne(EsGood esGood);
    int saveAll(List<EsGood> list);
    Object list();
    int deleteAll();

    Page<EsGood> search(QingTaoKeSearch qingTaoKeSearch, int pageNum, int pageSize);



}
