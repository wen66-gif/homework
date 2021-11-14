import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
import Home from '../views/Home.vue'
import MyCourse from "../views/MyCourse";
import Login from "../views/Login";

Vue.use(VueRouter)

const routes = [
  {
    name: "mycourse",
    path: '/mycourse',
    component: MyCourse,
    meta:{title:"我教的课",isAuth:true}
  },
  {
    name:"login",
    path:'/login',
    component: Login,
    meta: {title: "登录",isAuth :false}
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  console.log("前置路由守卫",to,from)
  if (to.meta.isAuth){    // 判断是否需要权限控制
    let token = store.state.Authorization;
    if (token === null || token === '') {
      next('/login');
    }
    else {
      next();
    }
  }else {
    next()
  }
})


// 全局后置路由守卫，初始化及跳转后调用
router.afterEach((to,from)=>{
  console.log("后置路由守卫",to,from)
  document.title = to.meta.title || "作业管理系统"
})

export default router
