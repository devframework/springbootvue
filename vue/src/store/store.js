import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// 一个store  , Vuex.store的 实例
export const store = new Vuex.Store({
  strict: true,
  state: localStorage.getItem('state')
    ? JSON.parse(localStorage.getItem('state'))
    : {
      token: '',
      menus: []
    },
  getters: { //  Getter相当于vue中的computed计算属性
    getToken: (state) => { return state.token }
  },
  mutations: {
    set_token (state, ltoken) { // 第一个参数是拿到state对象
      localStorage.setItem('token', ltoken)
      state.token = ltoken
    },
    del_token (state) {
      localStorage.removeItem('token')
      state.token = ''
    },
    set_menu (state, menus) { // 第一个参数是拿到state对象
      localStorage.setItem('menu', menus)
      state.menus = menus
    },
    del_menu (state) {
      localStorage.removeItem('menu')
      state.menus = []
    },
    clear_state (state) {
      localStorage.clear(); state.token = null; state.menus = null; state = null
    }
  },
  actions: {
    // 注册actions，类似vue里面的methods
    // 通过这个修改state里面的值
    // 可以直接用mutations修改，但是官方建议使用actions去调用mutations去修改
    set_token (context, token) {
      context.commit('set_token', token)
    },
    del_token (context) {
      context.commit('del_token')
    },
    set_menu (context, menus) {
      context.commit('set_menu', menus)
    },
    del_menu (context) {
      context.commit('del_menu')
    },
    clear_state (context) {
      context.commit('clear_state')
    }
  }
})
