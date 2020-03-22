package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Employment;
import com.zzj.recruitment.vo.AddUpdateEmploymentVo;
import com.zzj.recruitment.vo.CompanyPositionSearchVo;
import com.zzj.recruitment.vo.PosisitonListVo;
import com.zzj.recruitment.vo.PositionDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmploymentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employment record);

    int insertSelective(Employment record);

    Employment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employment record);

    int updateByPrimaryKey(Employment record);

    List<PosisitonListVo> selectPositionBySearchVo(CompanyPositionSearchVo searchVo);

    PositionDetailVo selectDetailByPositionId(Integer id);

    List<PosisitonListVo> selectPositionBySearchVoUserId(@Param("searchVo") CompanyPositionSearchVo employmentSearchVo, @Param("userid") Integer userid);

    Integer insertByEmploymentVo(AddUpdateEmploymentVo addUpdateEmploymentVo);

    Integer updateByEmploymentVo(AddUpdateEmploymentVo addUpdateEmploymentVo);

    Integer selectPublisherByResumeId(Integer resumeId);
}