let proxyObj = {}
proxyObj['/'] = {
  ws:false,
  target:'http://www.grtwwh.com:8080',
  changeOrigin:true,
  pathRewrite:{
    '^/':''
  }
}

module.exports={
  devServer:{
    host: '127.0.0.1',
    port: 9527,
    proxy: proxyObj,
    disableHostCheck: true
  }
}