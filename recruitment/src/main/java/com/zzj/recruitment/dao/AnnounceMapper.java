package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Announce;
import com.zzj.recruitment.vo.AnnounceListVo;
import com.zzj.recruitment.vo.AnnounceSearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnnounceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Announce record);

    int insertSelective(Announce record);

    Announce selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Announce record);

    int updateByPrimaryKey(Announce record);

    List<AnnounceListVo> getAnnounceList(Integer userId);

    AnnounceListVo selectByannounceId(Integer announceId);

    List<AnnounceListVo> selectAnnounceListBySearchVo(@Param("announceSearchVo") AnnounceSearchVo announceSearchVo);

}