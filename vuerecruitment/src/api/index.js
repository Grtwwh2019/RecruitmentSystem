import {post, get} from '@/utils/api'
import { postJson } from '../utils/api'

//上传文件
export const upload = (file, type) => {
  return post('/common/upload.do/'+ type, file)
}

export const deleteFile = (params) => {
  return post('/common/deleteFile.do/', params)
}


// 注册
export const register = (params, role) => {
  return post('/user/register.do/' + role, params)
}

// 登陆
export const userLogin = (params) => {
  return post('/user/login.do', params)
}

// 登出
export const userLogout = () => {
  return post('/user/logout.do')
}

// 获取验证码
export const getCaptcha = (params) => {
  return get('/captcha', params)
}

// 忘记密码（三个）
export const forgetGetPsd = (params) => {
  // return get('/user/forgetGetPsd.do', params)
  return get('/user/forgetGetPsd.do?username='+params.username+'&type='+params.type)
}

export const forgetCheckCode = (params) => {
  return post('/user/forgetCheckCode.do', params)
}

export const forgetResetPassword = (params) => {
  return post('/user/forgetResetPassword.do', params)
}

// 获取用户信息
export const getUserInfo = () => {
  return get('/user/getUserInfo.do')
}

//获取角色信息
export const getHighestRole = () => {
  return get('/user/getHighestRole.do')
}

// 更新用户信息
export const updateUserInfo = (params) => {
  return post('/user/updateUserInfo.do', params)
}

// 修改密码
export const resetPassword = (params) => {
  return post('/user/resetPassword.do', params)
}

// 获得公司列表(id, cname)
export const getCompanyListByCname = (cname) => {
  return get('/common/getCompanyListByCname.do/' + cname)
}


// 提交企业用户认证申请
export const applyEnterpriseUser = (params) => {
  return postJson('/backend/user/applyEnterpriseUser.do', params)
}

// 获取简历信息
export const getResumeInfo = () => {
  return get('/resume/getResumeInfo.do')
}

// 更新简历基本信息
export const updateResumeBaseInfo = (params) => {
  return postJson('resume/updateResumeBaseInfo.do', params)
}


// 获得学校列表(id, cname)
export const getSchoolListBySname = (sname) => {
  return get('/common/getSchoolListBySname.do/' + sname)
}


// 更新简历教育经历
export const updateResumeEducationExpInfo = (params, eduId) => {
  return postJson('/resume/updateResumeEducationExpInfo.do/' + eduId, params)
}

// 得到所有行业类型
export const getAllIndustry = () => {
  return get('/common/getAllIndustry.do')
}


// 得到所有职位类型
export const getAllPositionType = () => {
  return get('/common/getAllPositionType.do')
}

// 根据PositionId获得技能标签
export const getSkillTagsByParentId = (parentId) => {
  return get('/common/getSkillTagsByParentId.do/' + parentId)
}


// 更新简历工作经历
export const updateResumeWorkExpInfo = (params, workId) => {
  return postJson('/resume/updateResumeWorkExpInfo.do/' + workId, params)
}

// 删除简历工作经历
export const deleteResumeWorkExpInfo = (workId) => {
  return postJson('/resume/deleteResumeWorkExpInfo.do/' + workId)
}


// 更新简历项目经历
export const updateResumeProjectExpInfo = (params, projectId) => {
  return postJson('/resume/updateResumeProjectExpInfo.do/' + projectId, params)
}

// 删除简历项目经历
export const deleteResumeProjecExpInfo = (projectId) => {
  return postJson('/resume/deleteResumeProjecExpInfo.do/' + projectId)
}

// 得到所有职位类型
export const getAllCertification = () => {
  return get('/common/getAllCertification.do')
}


// 更新简历项目经历
export const updateResumeCertificationListInfo = (params) => {
  return postJson('/resume/updateResumeCertificationListInfo.do', params)
}

// 得到所有城市
export const getAllCity = () => {
  return get('/common/getAllCity.do')
}

// 删除简历附件
export const deleteAttachment = (type) => {
  return post('/resume/deleteAttachment.do/', type)
}

// 根据type查询数据列表，0——搜索职位；1——搜索公司
export const searchList = (params) => {
  return postJson('/common/searchList.do/' + params.type + "/" + params.pageNum, params.searchVo)
}

// 根据type查询详细数据，0——职位；1——公司
export const getDetail = (params) => {
  return get('/common/getDetail.do/' + params.type + "/" + params.id)
}

// 收藏职位
export const collectPosition = (params) => {
  return post('/backend/user/collectPosition.do', params)
}

// 收藏公司
export const collectCompany = (params) => {
  return post('/backend/user/collectCompany.do', params)
}

// 获取用户收藏的状态
export const getUserCollectionStatus = (params) => {
  return get('/user/getUserCollectionStatus.do/' + params.type + "/" + params.id)
}

// 简历投递
export const deliver = (employmentId) => {
  return post('/user/deliver.do/'+ employmentId)
}

// 得到用户的公告列表
export const getAnnounceList = (pageNum, readed) => {
  return get('/backend/user/getAnnounceList.do/'+ (pageNum == null ? '' : pageNum) + '/' + readed)
}

// 得到用户的公告详情
export const getAnnounceDetail = (announceId) => {
  return get('/backend/user/getAnnounceDetail.do/'+ announceId)
}

// 得到用户的收藏夹列表
export const getCollectionList = (type, pageNum ) => {
  return get('/backend/user/getCollectionList.do/'+ type + '/' + pageNum)
}

// 得到用户的投递箱列表
export const getResumeDeliverList = (pageNum ) => {
  return get('/backend/user/getResumeDeliverList.do/'+ pageNum)
}

// 获取招聘职位列表
export const getEmploymentList = (params) => {
  return postJson('/entUser/getEmploymentList.do/' + params.pageNum, params.searchVo)
}

// 增加/更新招聘信息
export const addUpdateEmploymentInfo = (params, id) => {
  return postJson('/entUser/addUpdateEmploymentInfo.do/' + id, params)
}

// 删除招聘信息
export const deleteEmploymentInfo = (id) => {
  return postJson('/entUser/deleteEmploymentInfo.do/' + id)
}

// 获取求职者简历列表
export const getUserResumeList = (params) => {
  return postJson('/entUser/getUserResumeList.do/' + params.pageNum, params.searchVo)
}

// 查询求职者简历详细数据
export const getResumeDetailInfo = (resumeId, employmentId) => {
  return get('/entUser/getResumeDetailInfo.do/' + resumeId + '/' + employmentId)
}

// 获取申请企业用户的用户列表
export const getApplyEntUserList = (pageNum) => {
  return get('/entAccount/getApplyEntUserList.do/' + pageNum)
}

// 查看企业用户认证详细信息
export const getEntUserAuthInfo = (entUserId) => {
  return get('/entAccount/getEntUserAuthInfo.do/' + entUserId)
}

// 审批企业用户
// @param approve ： 1---通过认证； 2---拒绝认证
export const approveEntUser = (entUserId, approve) => {
  return postJson('/entAccount/approveEntUser.do/' + entUserId + '/' + approve)
}