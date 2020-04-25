package com.zzj.recruitment.service.Backend;

import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.vo.*;

import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-17 21:40:22
 */
public interface IResumeService {

    ServerResponse<ResumeInfoVo> returnResumeInfo(User user);

    ServerResponse<ResumeBaseInfoVo> updateResumeBaseInfo(UpdateResumeBaseInfoVo resume, User user);

    ServerResponse deleteAttachment(String type, User user);

    ServerResponse<ResumeWorkExpListVo> updateResumeWorkExpInfo(Integer workExpId, UpdateResumeWorkExpVo workExpVo, User user);

    ServerResponse<ResumeProjectExpListVo> updateResumeProjectExpInfo(Integer projectExpId, UpdateResumeProjectExpVo projectExpIdVo, User user);

    ServerResponse<ResumeEducationExpListVo> updateResumeEducationExpInfo(Integer educationExpId, UpdateResumeEducationExpVo educationExpVo, User user);

    ServerResponse<List<ResumeCertificationListVo>> updateResumeCertificationInfo(List<Integer> certificationIdList, User user);

    ServerResponse deleteResumeWorkExpInfo(Integer workExpId, User user);

    ServerResponse deleteResumeProjecExpInfo(Integer projectExpId, User user);
}
