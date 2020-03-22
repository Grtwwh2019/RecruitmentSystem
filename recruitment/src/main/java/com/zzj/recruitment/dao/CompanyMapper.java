package com.zzj.recruitment.dao;

import com.zzj.recruitment.pojo.Company;
import com.zzj.recruitment.vo.CompanyDetailVo;
import com.zzj.recruitment.vo.CompanyListVo;
import com.zzj.recruitment.vo.CompanyPositionSearchVo;

import java.util.List;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    List<CompanyListVo> selectCompanyBySearchVo(CompanyPositionSearchVo searchVo);

    CompanyDetailVo selectDetailByCompanyId(Integer id);
}