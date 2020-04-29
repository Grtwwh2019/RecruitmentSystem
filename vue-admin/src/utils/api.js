import axios from 'axios'
import router from '../router'
import { Message } from 'element-ui'

axios.defaults.withCredentials = true
// axios.interceptors.request.use(config => {
//   if (config.url == '/common/upload.do') {
//     // 此处设置文件上传，配置单独请求头
//     config.headers = {
//       'Content-Type': 'multipart/form-data'
//     }
//   }
//   return config
// },err => {
//   return Promise.reject(err)
// })

axios.interceptors.response.use(success => {
  if (success.status && success.status == 200 && success.data.status == 1) {
    let msgstr = String(success.data.msg)
    if (msgstr.indexOf("登录") >= 1) {
      console.log(msgstr)
      Message.error({message: success.data.msg})
      router.push("/")
    }
    Message.error({message: success.data.msg})
  } else if (success.data.status == 403) {
    Message.error({message: success.data.msg})
  } 
  else if (success.data.msg) {
    Message.success({message: success.data.msg})
  }
  return success.data
}, error => {
  if (error.response.status == 504 || error.response.status == 404) {
    Message.error({message:'服务器出现异常！'})
  } else if (error.response.status == 403) {
    Message.error({message:'权限不足，请联系管理员！'})
  } else if (error.response.status == 404) {
    Message.error({message:'页面不存在，请联系管理员！'})
  } else {
    if (error.response.data.msg) {
      Message.error({message:error.response.msg})
    } else {
      Message.error({message:'未知错误'})
    }
  }
  return
})


let base = '';
// key-value
export const post = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function(data) {
      let ret = '';
      for(let i in data) {
        ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type' : 'application/x-www-form-urlencoded'
    },
    xhrFields: {withCredentials: true}, 
    crossDomain: true
  })
}

// 传递Json的post
export const postJson = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
  })
}

export const putJson = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params
  })
}

export const deleteJson = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    data: params
  })
}


export const get = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    data: params
  })
}