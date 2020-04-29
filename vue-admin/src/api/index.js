import {get} from '@/utils/api'

// 得到所有行业类型
export const getAllIndustry = () => {
    return get('/common/getAllIndustry.do')
}
  
  
// 得到所有职位类型
export const getAllPositionType = () => {
    return get('/common/getAllPositionType.do')
}

// 得到所有城市
export const getAllCity = () => {
    return get('/common/getAllCity.do')
}