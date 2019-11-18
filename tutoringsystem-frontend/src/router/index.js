import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import EventRegistration from '@/components/Login'
import Signup from '@/components/Signup'
import SessionList from '@/components/SessionList'
import Register from '@/components/Register'
import ReviewSession from '@/components/ReviewSession'
import PickTutor from '@/components/PickTutor'
import PickCourse from '@/components/PickCourse'
import pickTutorByName from '@/components/pickTutorByName'
import pickCourseByName from '@/components/pickCourseByName'

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
      path: '/PickCourse',
      name: 'PickCourse',
      component: PickCourse,
    },
    {
      path: '/pickTutorByName',
      name: 'pickTutorByName',
      component: pickTutorByName,
    },
    {
      path: '/pickCourseByName',
      name: 'pickCourseByName',
      component: pickCourseByName,
    },
    {
      path: '/PickTutor',
      name: 'PickTutor',
      component: PickTutor,
      /*
      beforeEnter(to, from, next) {

        window.sessionStorage.setItem("username", "a");
        var currentUser = window.sessionStorage.getItem("username");

        if (currentUser == '' || currentUser == null || currentUser.equals(null)) {

            window.alert("Cannot open this page without being logged in. Please login first before accessing this page.");

            next({
              name: "Login" // back to login page //
            });
            
        } else {

            console.log("The user currently logged in is :"+currentUser);

            next({
              //name: "Login" // back to login page //
            });

        }
      }
      */
    }



  ]
})