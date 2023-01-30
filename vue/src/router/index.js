import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import User from "../views/User";
import Home from "../views/Home";
import store from "../store";
import Login from "../views/Login";
import Register from "../views/Register";
import Person from "../views/Person";
import Role from "../views/Role";
import Files from "../views/Files";

Vue.use(Router)

const routes=[
    {
      path: '/',
      component: HelloWorld,
      redirect:'/login',
      children:[
        {
        path:'user',
        name:'用户管理',
        component: User,
        },
        {
          path:'home',
          name:'主页',
          component: Home,
        },
        {
          path:'person',
          name:'个人信息',
          component: Person,
        },
        {
          path:'files',
          name:'文件管理',
          component: Files,
        },
        {
          path:'role',
          name:'文件管理',
          component: Role,
        }
      ]
    },
    {
      path: '/login',
      component: Login,
    },
    {
      path: '/register',
      component: Register
    }
  ]

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName",to.name)
    store.commit("setPath")
    next()
  }
)
export default router
