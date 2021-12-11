import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
import Home from '../views/Home.vue'
import TCourse from "../views/TCourse";
import Login from "../views/Login";
import CourseManage from "../views/CourseManage";
import EditHomework from "../views/EditHomework";
import SubmitList from "../views/SubmitList";
import CheckHomework from "../views/CheckHomework";
import HomeworkRep from "../views/HomeworkRep";
import SCourse from "../views/SCourse";
import MyHomework from "../views/MyHomework";
import DoHomework from "../views/DoHomework";

Vue.use(VueRouter)

const routes = [
  {
    name: "tcourse",
    path: '/tea_course',
    component: TCourse,
    meta:{title:"我教的课",isAuth:true}
  },
  {
    name:"login",
    path:'/login',
    component: Login,
    meta: {title: "登录",isAuth :false}
  },
  {
    name:'CourseManage',
    path:'/course_manage',
    component:CourseManage,
    meta:{isAuth:true}
  },
  {
    name:'EditHomework',
    path:'/edit_homework',
    component:EditHomework,
    meta:{isAuth:true,title:"编辑作业"}
  },
  {
    name:'SubmitList',
    path:'/submit_list',
    component:SubmitList,
    meta:{isAuth:true,title:"提交列表"},

  },
  {
    name:'CheckHomework',
    path:'/check_homework',
    component:CheckHomework,
    meta:{isAuth:true,title:"批阅作业"},
  },
  {
    name:'HomeworkRep',
    path:'/homeworkrep',
    component:HomeworkRep,
    meta:{isAuth:true,title:"作业库"}
  },

  // 学生端
  {
    name: "scourse",
    path: '/stu_course',
    component: SCourse,
    meta:{title:"我学的课",isAuth:true}
  },
  {
    name: "myHomework",
    path: '/myHomework',
    component: MyHomework,
    meta:{title:"我的作业",isAuth:true}
  },
  {
    name: "doHomework",
    path: '/doHomework',
    component: DoHomework,
    meta:{title:"作业作答",isAuth:true}
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  // console.log("前置路由守卫",to,from)
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
  document.title = to.meta.title || "作业管理系统"
})

export default router
