package com.zzj.recruitment.service;

import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.vo.CompanyPositionSearchVo;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 14:24:13
 */
public interface ICommonService {

    ServerResponse<PageInfo> returnListByType(Integer type, CompanyPositionSearchVo searchVo, Integer pageNum);

    ServerResponse getDetail(Integer type, Integer id);

    ServerResponse getCompanyListByCompanyName(String companyName);

    ServerResponse getSchoolListBySchoolName(String schoolName);

    ServerResponse getAllIndustry();

    ServerResponse getAllPositionType();

    ServerResponse getSkillTagsByPositionId(Integer pid);

    ServerResponse getAllCertification();

    ServerResponse getAllCity();

}
