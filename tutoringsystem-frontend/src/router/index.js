import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import EventRegistration from '@/components/Login'
import Signup from '@/components/Signup'
import SessionList from '@/components/SessionList'
import Register from '@/components/Register'
import ReviewSession from '@/components/ReviewSession'
import PickTutor from '@/components/PickTutor'
import TutorReviews from '@/components/TutorReviews'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Hello',
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
    },
    {
      path: '/SessionList',
      name: 'SessionList',
      component: SessionList
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/ReviewSession',
      name: 'ReviewSession',
      component: ReviewSession
    },
    {
      path: '/PickTutor',
      name: 'PickTutor',
      component: PickTutor
    },
    {
      path: '/TutorReviews',
      name: 'TutorReviews',
      component: TutorReviews
    }
  ]
})