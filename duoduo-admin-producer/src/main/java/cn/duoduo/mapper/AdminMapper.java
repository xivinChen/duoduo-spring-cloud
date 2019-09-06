package cn.duoduo.mapper;

import cn.duoduo.vo.Admin;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    Admin selectByAccount(String account);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> findAll();

    @MapKey("id")
    Map<Integer,Admin> selectByPrimaryKeys(List<Integer> ids);
}