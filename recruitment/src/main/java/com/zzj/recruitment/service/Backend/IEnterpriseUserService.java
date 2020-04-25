package com.zzj.recruitment.service.Backend;

import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.vo.*;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 18:45:40
 */
public interface IEnterpriseUserService {
    ServerResponse<PageInfo> getEmploymentListByUserId(Integer pageNum, CompanyPositionSearchVo employmentSearchVo, User user);

    ServerResponse<PositionDetailVo> addOrUpdateEmployment(AddUpdateEmploymentVo addUpdateEmploymentVo, Integer eid, User user);

    ServerResponse<String> deleteEmploymentInfo(Integer eid);

    ServerResponse<PageInfo> getUserResumeList(Integer pageNum, ResumeListSearchVo resumeListSearchVo, User user);

    ServerResponse<User> getResumeDetailInfo(Integer resumeId, Integer employmentId, User user);

}
