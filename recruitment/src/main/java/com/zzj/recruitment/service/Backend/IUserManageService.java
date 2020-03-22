package com.zzj.recruitment.service.Backend;

import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.vo.AnnounceListVo;
import com.zzj.recruitment.vo.EnterpriseUserInfoVo;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 19:12:06
 */
public interface IUserManageService {

    ServerResponse collectPosition(Integer positionId, Integer userId, Integer type);

    ServerResponse collectCompany(Integer companyId, Integer userId, Integer type);

    ServerResponse<PageInfo> returnCollectionList(Integer type, User user, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> returnAnnounceList(User user, Integer pageNum);

    ServerResponse<AnnounceListVo> returnAnnounceDetail(Integer announceId, User user);

    ServerResponse applyEnterpriseUser(EnterpriseUserInfoVo enterpriseUserInfoVo, User user);
}
