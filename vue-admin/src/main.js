import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'



import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import {postJson} from "./utils/api";
import {post} from "./utils/api";
import {putJson} from "./utils/api";
import {deleteJson} from "./utils/api";
import {get} from "./utils/api";
import 'font-awesome/css/font-awesome.min.css'
import {initConst} from "./utils/initConst";
Vue.prototype.postJson = postJson;
Vue.prototype.post = post;
Vue.prototype.putJson = putJson;
Vue.prototype.deleteJson = deleteJson;
Vue.prototype.get = get;

Vue.use(ElementUI);
Vue.config.productionTip = false


router.beforeEach(async (to, from, next) => {
  if (to.path == '/') {
      next();
  }else {
    initConst(router, store)
    // console.log(to)
    // let user = {}
    // let resp = await get('/user/getUserInfo.do')
    // resp ? user = resp.data : null
    // if (user) {
    //   resp = await get('/user/getHighestRole.do')
    //   resp ? user.role = resp.data : null
    //   let roles = to.meta.roles
    //   roles.forEach(element => {
    //     if (element == user.role.name) {
    //       next();
    //     }
    //   })
    if (window.sessionStorage.getItem("user")) {
      next()
    } else{
      // Message({
      //   message: '您没有权限，请先登录！',
      //   type: 'warning'
      // });
      next('/?redirect='+to.path);
    }
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
