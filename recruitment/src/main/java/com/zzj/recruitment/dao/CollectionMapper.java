package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Collection;
import com.zzj.recruitment.vo.CompanyListVo;
import com.zzj.recruitment.vo.PosisitonListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);

    Integer removeByPositionUserId(@Param("positionId") Integer positionId, @Param("userId") Integer userId);

    Integer addByPositionUserId(@Param("positionId") Integer positionId, @Param("userId") Integer userId);

    Integer selectByPositionUserId(@Param("positionId") Integer positionId, @Param("userId") Integer userId);

    Integer selectByCompanyUserId(@Param("companyId") Integer companyId, @Param("userId")Integer userId);

    Integer addByCompanyUserId(@Param("companyId") Integer companyId, @Param("userId")Integer userId);

    Integer removeByCompanyUserId(@Param("companyId") Integer companyId, @Param("userId")Integer userId);

    List<Integer> selectPositionByUserId(Integer userId);

    List<Integer> selectCompanyByUserId(Integer userId);

    List<PosisitonListVo> getPosisitonList(Integer userId);

    List<CompanyListVo> getCompanyList(Integer userId);

    Integer selectUserCollectionStatus(@Param("type") Integer type, @Param("collectionId") Integer collectionId, @Param("userId") Integer userId);
}