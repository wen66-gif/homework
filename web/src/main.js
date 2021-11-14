import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import axios from 'axios'

//引入antd
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
// 注册并添加拦截器
axios.interceptors.request.use(
    config => {
      if (localStorage.getItem('Authorization')) {
        config.headers.Authorization = store.state.Authorization;
        config.baseURL = "http://localhost:9000"
      }
      return config;
    },
    error => {
      return Promise.reject(error);
    }
);
Vue.config.productionTip = false
Vue.use(Antd)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
