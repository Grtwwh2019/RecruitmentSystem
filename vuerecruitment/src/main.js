import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import router from './router'
import store from './store'
import {initConst} from "./utils/initConst";
import { getUserInfo, getHighestRole } from "@/api/index";
import './assets/css/font.css'   //字体图标
import './assets/css/animate.css'  //css动画
import './assets/css/loading.css'  //加载动画
import { Message } from 'element-ui';


Vue.config.productionTip = false
Vue.use(ElementUI);


// router.afterEach((to,from,nex)=>{
//   window.scroll(0,0)
// })


router.beforeEach(async (to,from,next)=>{
  if (to.path == '/login') {
    next()
  } else {
    if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
      let user = {}
      let resp = await getUserInfo()
      if (resp && resp.status == 0) {  // 通过vuex state获取当前的token是否存在
        user = resp.data
        let result = await getHighestRole()
        if (result && result.status == 0) {
          user.role = result.data
        }
        window.sessionStorage.setItem("user", JSON.stringify(user))
        next();
      } else {
        Message({
          message: '您没有权限，请先登录！',
          type: 'warning'
        });
        next({
          path: '/login',
          query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
        })
      }
    }
    window.sessionStorage.setItem("user", window.localStorage.getItem("user"))
    initConst(router, store)
    next()
  }
})
// 跳转后返回顶部
router.afterEach((to,from,next) => {
  window.scrollTo(0,0);
})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
