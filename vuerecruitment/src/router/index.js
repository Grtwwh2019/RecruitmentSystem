import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import index from './views/index.vue'

Vue.use(Router)

// https://segmentfault.com/q/1010000007788495

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: Home,
      //根目录/下的页面情况，比如/jobSearch,/company等
      children: [
        //默认根目录  打开index主页
        {
          path: '',
          name: 'index',
          components: {
            content: index
          }
        },

        //职位搜索页面  打开jobSearch页面
        {
          path: 'jobSearch',
          name: 'jobSearch',
          components: {
            content: () => import('./views/JobSearch.vue')
          },
          //职位搜索页面下的 子路由  负责jobSearch的具体内容
          children: [
            /* {
              //当打开/jobSearch/的默认子路由
              path: '',
              components: {
                jobList: () => import('./components/JobSearch/JobList.vue')
              },
            }, */
            {
              //TODO:这时候应该完成路由信息的传递
              //当打开/jobSearch/jobLIst的时候，重定向至/jobSearch
              path: 'jobList',
              name: "jobList",
              components: {
                jobList: () => import('./components/JobSearch/JobList.vue')
              },
            }
          ]
        },

        //职位详情页面  打开jobPage页面
        {
          path: 'job/:jobId',
          name: 'job',
          components: {
            content: () => import('./views/JobPage.vue')
          },
        }
      ]
    },

    //当不需要Home.vue作为页面骨架的时候，比如开发页面
    // {
    //   path: '/jobSearch',
    //   name: 'jobSearch',
    //   // route level code-splitting
    //   // this generates a separate chunk (about.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import(/* webpackChunkName: "about" */ './views/JobSearch.vue')
    // }
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})
