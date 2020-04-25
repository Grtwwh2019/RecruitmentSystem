package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Resume;
import com.zzj.recruitment.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResumeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);

    ResumeBaseInfoVo selectBaseInfoByUserId(Integer userId);

    List<ResumeWorkExpListVo> selectWorkExpByResumeId(Integer resumeId);

    List<Map<String,Object>> selectSkillTagByWid(Integer wid);

    List<ResumeEducationExpListVo> selectEducationExpByResumeId(Integer resumeId);

    List<ResumeProjectExpListVo> selectProjectExpByResumeId(Integer resumeId);

    List<ResumeCertificationListVo> selectCertificationByResumeId(Integer resumeId);

    List<Map<String, Object>> selectIndustriesByResumeId(Integer resumeId);

    Integer selectResumeIdByUserId(Integer userId);

    Integer updateResumeBaseInfo(UpdateResumeBaseInfoVo resume);

    Integer insertReumeBaseInfo(UpdateResumeBaseInfoVo resume);

    Integer deleteResumeIndustryByRid(Integer resumeId);

    Integer insertResumeIndusty(@Param("resumeId") Integer resumeId, @Param("industryId") Integer industryId);

    String selectUriByUserId(@Param("type") String type, @Param("userId") Integer userId);

    Integer updateResumeAttachmentByUserId(@Param("userId") Integer userId, @Param("type") String type);

    Integer updateResumeWorkExpById(UpdateResumeWorkExpVo updateResumeWorkExpVo);

    Integer insertResumeWorkExp(UpdateResumeWorkExpVo updateResumeWorkExpVo);

    Integer updateResumeProjectExpById(UpdateResumeProjectExpVo projectExpIdVo);

    Integer insertResumeProjectExp(UpdateResumeProjectExpVo projectExpIdVo);

    Integer updateResumeEducationExpById(UpdateResumeEducationExpVo updateEducationExpVo);

    Integer insertResumeEducationExp(UpdateResumeEducationExpVo updateEducationExpVo);

    List<ResumeBaseInfoVo> selectDeliveryResumeByUserId(@Param("searchVo") ResumeListSearchVo resumeListSearchVo, @Param("userid") Integer userid);
}