import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import EventRegistration from '@/components/Login'
import Signup from '@/components/signup'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/Login',
      name: 'Login',
      component: EventRegistration
    },
    {
      path: '/Signup',
      name: 'Signup',
      component: Signup
    }
  ]
})