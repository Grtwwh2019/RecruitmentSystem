package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Right;
import com.zzj.recruitment.vo.RightRolesVo;

import java.util.List;

public interface RightMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Right record);

    int insertSelective(Right record);

    Right selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Right record);

    int updateByPrimaryKey(Right record);

    List<Right> getAllRights();

}