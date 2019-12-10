package cn.bosenkeji.mapper;


import cn.bosenkeji.vo.combo.ComboRedisKey;

import java.util.List;

public interface ComboRedisKeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComboRedisKey record);

    int insertSelective(ComboRedisKey record);

    ComboRedisKey selectByPrimaryKey(Integer id);
    ComboRedisKey selectByName(String name);
    List<ComboRedisKey> list();


    int updateByPrimaryKeySelective(ComboRedisKey record);

    int updateByPrimaryKey(ComboRedisKey record);
}