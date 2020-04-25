import Vue from 'vue'
import VueRouter from 'vue-router'
const Login = () => import('@/views/Login/Login.vue')
const Logout = () => import('@/views/Login/Logout.vue')
const Home = () => import('@/views/Home/Home.vue')
const Register = () => import('@/views/Login/Register.vue')
const ForgetResetPsd = () => import('@/views/Login/ForgetResetPsd.vue')
const Setting = () => import('@/views/backend/setting.vue')
const PersonalInfo = () => import('@/views/backend/personalInfo.vue')
const SecurityManage = () => import('@/views/backend/securityManage.vue')
const Authentication = () => import('@/views/backend/authentication.vue')
const Resume = () => import('@/views/backend/resume.vue')
const Index = () => import('@/views/Home/Index.vue')
const SearchList = () => import('@/views/protal/SearchList.vue')
const JobsDetail = () => import('@/views/protal/JobsDetail.vue')
const CompanyDetail = () => import('@/views/protal/CompanyDetail.vue')
const Company = () => import('@/views/protal/Company.vue')
const AnnounceList = () => import('@/views/backend/announceList.vue')
const Collections = () => import('@/views/backend/collections.vue')
const EmploymentManage = () => import('@/views/backend/employmentManage.vue')
const JobSeekerDeliver = () => import('@/views/backend/jobSeekerDeliver.vue')
const ApproveEntUser = () => import('@/views/backend/approveEntUser.vue')


Vue.use(VueRouter)

// Vue-router 报NavigationDuplicated的解决方案
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
  const routes = [
    {path: '/', redirect: '/index'},
    {
      path: '/home',
      name: 'Home',
      component: Home,
      children: [
        {
          path: '/setting',
          name: 'Setting',
          component: Setting,
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          children: [
            {
              path: '/personalInfo',
              name: 'PersonalInfo',
              meta: {
                requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
              },
              component: PersonalInfo
            },
            {
              path: '/securityManage',
              name: 'SecurityManage',
              meta: {
                requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
              },
              component: SecurityManage
            },
            {
              path: '/authentication',
              name: 'Authentication',
              meta: {
                requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
              },
              component: Authentication
            }
          ]
        },
        {
          path: '/resume',
          name: 'Resume',
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          component: Resume
        },
        {
          path: '/index',
          name: 'Index',
          component: Index
        },
        {
          path: '/company',
          name: 'Company',
          component: Company
        },
        {
          path: '/searchList',
          name: 'SearchList',
          component: SearchList,
          children: [
          ]
        },
        {
          path: '/jobsDetail',
          name: 'JobsDetail',
          component: JobsDetail
        },
        {
          path: '/companyDetail/:id',
          name: 'CompanyDetail',
          component: CompanyDetail
        },
        {
          path: '/announceList',
          name: 'AnnounceList',
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          component: AnnounceList
        },
        {
          path: '/collections',
          name: 'Collections',
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          component: Collections
        },
        {
          path: '/delivery',
          name: 'Delivery',
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          component: Collections
        },
        {
          path: '/employmentManage',
          name: 'EmploymentManage',
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          component: EmploymentManage
        },
        {
          path: '/jobSeekerDeliver',
          name: 'JobSeekerDeliver',
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          component: JobSeekerDeliver
        },
        {
          path: '/approveEntUser',
          name: 'ApproveEntUser',
          meta: {
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
          },
          component: ApproveEntUser
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/logout',
      name: 'Logout',
      component: Logout
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/forgetResetPsd',
      name: 'ForgetResetPsd',
      component: ForgetResetPsd
    }
    
  ]

const router = new VueRouter({
  // mode: 'history',
  routes
})

export default router
