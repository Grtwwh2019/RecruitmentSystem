package com.zzj.recruitment.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 在线简历所有信息的整合
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 15:00:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeInfoVo {

    ResumeBaseInfoVo resumeBaseInfoVo;

    List<ResumeWorkExpListVo> workExpListVos;

    List<ResumeProjectExpListVo> projectExpListVos;

    List<ResumeEducationExpListVo> educationExpListVos;

    List<ResumeCertificationListVo> certificationListVos;

}
