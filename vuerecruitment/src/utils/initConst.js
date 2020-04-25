import {  getAllIndustry, getAllPositionType, getAllCertification, getAllCity, getUserInfo } from "@/api/index";

export const initConst = (router, stroe) => {
  if (stroe.state.positionTypeSet.length > 0 ||
      stroe.state.citySet.length > 0 ||
      stroe.state.industrySet.length > 0) {
    return
  }
  getAllPositionType().then(resp => {
    if (resp && resp.status == 0) {
      // router.addRoutes(resp.data)
      // console.log('initConst.js---getAllPositionType')
      stroe.commit('initPositionTypeSet', resp.data)
      // console.log(resp)
    }
  })
  getAllCity().then(resp => {
    if (resp && resp.status == 0) {
      stroe.commit('initCitySet', resp.data)
    } else {
      Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
    }
  })
  getAllIndustry().then(resp => {
    if (resp && resp.status == 0) {
      // this.industrySet = resp.data
      stroe.commit('initIndustrySet', resp.data)
    } else {
      Message.error({message: resp.msg ? resp.msg : "未知错误，请联系管理员"})
    }
  })
}