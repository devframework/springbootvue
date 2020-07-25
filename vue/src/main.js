import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
import axios from './axios/http'
import { store } from './store/store'
import './plugins/dialog'

Vue.use(Vuex)
Vue.prototype.$http = axios
Vue.config.productionTip = false

new Vue({
  axios,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
