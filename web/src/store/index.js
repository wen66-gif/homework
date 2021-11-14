import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 当前用户名
    username:"",
    // 存储token
    Authorization:localStorage.getItem("Authorization") ? localStorage.getItem("Authorization") : '',
  },
  mutations: {
    // 保存当前用户名
    SET_USERNAME(state,username){
      console.log("username:"+username)
      state.username = username
    },

    // 保存token
    SET_TOKEN(state,value){
      localStorage.setItem("Authorization",JSON.stringify(value))
      state.Authorization = value
    }
  },
  actions: {
  },
  modules: {
  }
})
