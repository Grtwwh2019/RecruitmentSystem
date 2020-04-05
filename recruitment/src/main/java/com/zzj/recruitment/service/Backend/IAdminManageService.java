package com.zzj.recruitment.service.Backend;

import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.Announce;
import com.zzj.recruitment.pojo.Company;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.vo.*;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-22 15:31:34
 */
public interface IAdminManageService {
    ServerResponse<String> deleteCompanyInfo(Integer companyId);

    ServerResponse<CompanyDetailVo>updateCompanyInfo(Company company);

    ServerResponse<PageInfo> getApplyEntAccountList(Integer pageNum, Integer auth);

    ServerResponse<User> getEntAccountAuthInfo(Integer userid);

    ServerResponse<String> approveEnterpriseAccount(Integer entUserId, Integer approve);

    ServerResponse<PageInfo> getAllUserList(UserSearchVo userSearchVo, Integer pageNum);

    ServerResponse<String> mangeAccount(Integer userId, Integer status);

    ServerResponse<PageInfo> getAllAnnounceList(AnnounceSearchVo announceSearchVo, Integer pageNum);

    ServerResponse<AnnounceListVo> addOrUpdateAnnounce(Announce announce, Integer announceId, User user);

    ServerResponse<String> deleteAnnounce(Integer announceId);

    ServerResponse<PageInfo> getPositionList(CompanyPositionSearchVo employmentSearchVo, Integer pageNum);

    ServerResponse<String> forbiddenPosition(Integer positionId, Integer status);

}
