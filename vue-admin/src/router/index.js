import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Login',
    hidden: true,
    component: () => import('@/views/Login/Login.vue')
  },
  {
    path: '/home',
    name: 'Home',
    // hidden: true,
    meta: { title:'首页', icon: 'el-icon-menu', roles: ['ROLE_admin'] },
    component: () => import('@/views/Layout/Home.vue')
  },
  {
    path: '/home',
    name: 'Home',
    // hidden: true,
    meta: { roles: ['ROLE_admin'] },
    component: () => import('@/views/Layout/Home.vue'),
    children: [
      {
        path: 'home',
        name: 'Home',
        meta: { title:'首页', icon: 'el-icon-menu'},
        component: () => import('@/views/Layout/Home.vue'),
      },
      {
        path: '/announceManage',
        name: 'AnnounceManage',
        component: () => import('@/views/AnnounceManage'),
        meta: {
          title: '公告管理', //设置该路由在侧边栏和面包屑中展示的名字
          icon: 'fa fa-bullhorn', //设置该路由的图标
          roles: ['ROLE_admin'] //设置该路由进入的权限，支持多个权限叠加
          // noCache: true, //如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
          // breadcrumb: false, // 如果设置为false，则不会在breadcrumb面包屑中显示
        }
      },
      {
        path: '/companyManage',
        name: 'CompanyManage',
        component: () => import('@/views/CompanyManage'),
        meta: {
          title: '公司管理', 
          icon: 'fa fa-building-o',
          roles: ['ROLE_admin']
        }
      },
      {
        path: '/userManage',
        name: 'UserManage',
        component: () => import('@/views/UserManage'),
        meta: {
          title: '用户管理', 
          icon: 'fa fa-user-circle-o',
          roles: ['ROLE_admin']
        }
      },
      {
        path: '/entAccountManage',
        name: 'EntAccountManage',
        component: () => import('@/views/EntAccountManage'),
        meta: {
          title: '企业账号管理', 
          icon: 'fa fa-address-card-o',
          roles: ['ROLE_admin']
        }
      },
      {
        path: '/employmentManage',
        name: 'EmploymentManage',
        component: () => import('@/views/EmploymentManage'),
        meta: {
          title: '招聘信息管理', 
          icon: 'fa fa-folder-o',
          roles: ['ROLE_admin']
        }
      }
    ]
  }
  // {
  //   path: '/announceMange',
  //   // meta: { title:'首页', icon: 'el-icon-menu', roles: ['ROLE_admin'] },
  //   component: () => import('@/views/Layout/Home.vue'),
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'AnnounceMange',
  //       component: () => import('@/views/AnnounceManage'),
  //       meta: {
  //         title: '公告管理', //设置该路由在侧边栏和面包屑中展示的名字
  //         icon: 'fa fa-user-circle-o', //设置该路由的图标
  //         roles: ['ROLE_admin'], //设置该路由进入的权限，支持多个权限叠加
  //         // noCache: true, //如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
  //         // breadcrumb: false, // 如果设置为false，则不会在breadcrumb面包屑中显示
  //       }
  //     }
  //   ]
  // }
  
]

const router = new VueRouter({
  routes
})

export default router
