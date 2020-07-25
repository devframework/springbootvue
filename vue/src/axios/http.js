/**
 * ajax请求配置
 */
import Vue from 'vue'
import axios from 'axios'
import { Message } from 'element-ui'
import { store } from '../store/store'
import router from '../router/index'
import { showLoading, hideLoading } from '../plugins/loading'
// import Qs from 'qs'

// axios默认配置
axios.defaults.timeout = 10000 // 超时时间

// 整理数据

// 路由请求拦截
// http request 拦截器
axios.interceptors.request.use(
  config => {
    console.log(store)
    if (store.state.token) {
      if (config.url.indexOf('login') > -1) {
        localStorage.setItem('token', '')
        config.headers.Authorization = ''
      } else {
        config.headers.Authorization = 'Bearer ' + store.state.token
      }
    }
    showLoading()
    return config
  },
  error => {
    hideLoading()
    return Promise.reject(error.response)
  }
)

// 路由响应拦截
// http response 拦截器
axios.interceptors.response.use(
  response => {
    if (response.status) {
      if (response.data.code == '401') {
        hideLoading()
        Message.error('登录过期,请重新登录')
        router.replace({
          path: '/login'
        })
      }
    }
    hideLoading()
    if (response.data) {
      if (response.data.data) {
        if (response.data.data.current) {
          response.data.data.current = parseInt(response.data.data.current)
        }
        if (response.data.data.pages) {
          response.data.data.pages = parseInt(response.data.data.pages)
        }
        if (response.data.data.size) {
          response.data.data.size = parseInt(response.data.data.size)
        }
        if (response.data.data.current) {
          response.data.data.total = parseInt(response.data.data.total)
        }
      }
    }
    return response.data
  },
  error => {
    hideLoading()
    console.log(error)
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 返回 401 清除token信息并跳转到登录页面
          Message.error('登录过期,请重新登录')
          router.replace({
            path: '/login'
          })
          break
        case 404:
          Message.error('请求地址不存在')
          break
        default:
          Message.error('请求失败')
          break
      }
    }
    console.log('error' + error.response)
    return Promise.reject(error.response) // 返回接口返回的错误信息
  }
)
export default axios
