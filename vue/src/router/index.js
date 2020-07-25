import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import { Message } from 'element-ui'
import { store } from '../store/store'
// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  {
    path: '/home',
    component: resolve => require(['../views/layout/Layout.vue'], resolve),
    redirect: '/welcome',
    children: [
      {
        path: '/welcome',
        component: resolve => require(['../views/Welcome.vue'], resolve),
        meta: { requestLogin: true, permissions: false }
      },
      { path: '/sysuser', component: resolve => require(['../views/system/SysUser.vue'], resolve), meta: { requestLogin: true, permissions: true } },
      { path: '/sysrole', component: resolve => require(['../views/system/SysRole.vue'], resolve), meta: { requestLogin: true, permissions: true } },
      { path: '/sysmenu', component: resolve => require(['../views/system/SysMenu.vue'], resolve), meta: { requestLogin: true, permissions: true } },
      { path: '/syslog', component: resolve => require(['../views/system/SysLog.vue'], resolve), meta: { requestLogin: true, permissions: true } },
      { path: '/sysdic', component: resolve => require(['../views/system/SysDic.vue'], resolve), meta: { requestLogin: true, permissions: true } },
      { path: '/syssequence', component: resolve => require(['../views/system/SysSequence.vue'], resolve), meta: { requestLogin: true, permissions: true } },
      { path: '/sysjob', component: resolve => require(['../views/system/SysJob.vue'], resolve), meta: { requestLogin: true, permissions: true } },
      { path: '/wflowdesign', component: resolve => require(['../views/workflow/WflowDesign.vue'], resolve), meta: { requestLogin: false, permissions: false } },
      { path: '/wflowdefine', component: resolve => require(['../views/workflow/WflowDefine.vue'], resolve), meta: { requestLogin: false, permissions: false } },
      { path: '/wflowtask', component: resolve => require(['../views/workflow/WflowTask.vue'], resolve), meta: { requestLogin: false, permissions: false } },
      { path: '/wflowhistory', component: resolve => require(['../views/workflow/WflowHistory.vue'], resolve), meta: { requestLogin: false, permissions: false } },
      { path: '/401', component: resolve => require(['../views/error/401.vue'], resolve) },
      { path: '/404', name: 'notFound', component: resolve => require(['../views/error/404.vue'], resolve) },
      { path: '*', redirect: '/404' }
    ]
  },
  { path: '*', redirect: '/404' }
]

const router = new VueRouter({
  routes
})
// 全局守卫 需要跳转的路由to
router.beforeEach((to, form, next) => {
  // eslint-disable-next-line eqeqeq
  if (to.path == '/login' || to.path == '/') {
    next()
  } else {
    console.log('to.path' + to.path)
    console.log('form.path' + form.path)
    const token = store.state.token
    const menus = store.state.menus
    console.log(menus)
    // 校验全选
    let hasRole = false
    //
    if (to.meta.requestLogin) {
      // 校验是否需要检验登录
      if (token == null || token == '') {
        Message.error('登录过期,请重新登录')
        if (to.path != 'login') {
          router.replace({
            path: '/login'
          })
        }
      } else {
        // 是否需要校验权限
        if (to.meta.permissions) {
          console.log(menus)
          menus.forEach(item => {
            if (to.path == item.menuUrl) {
              hasRole = true
            }
          })
          if (hasRole) {
            next()
          } else {
            // 没有权限跳转到401页面
            if (form.path == '401') {

            } else {
              next('/401')
            }
            // next()
          }
        } else {
          next()
        }
      }
    } else {
      next()
    }
  }
})

// 后置钩子
router.afterEach((to, form) => {
  console.log('form.path' + form.path)
})

export default router
