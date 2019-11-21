<template>
  <div>
    <div class="container-fluid" id="top-container">
      <div class="container text-center" id="img-container">
        <img
          src="https://ballstateeconomics.files.wordpress.com/2014/04/tutoring-banner.png"
          width="500"
          height="100"
        />
      </div>
    </div>
    <div id="headerBar">
      <h2 id="pickCourseSubHeader">Step 1: Pick a course for your session</h2>
    </div>

    <!-- Here is where the tutors will be displayed -->
    <div id="courseDisplays">
      <div class="container-fluid">
        <!-- Each row will display 2 tutors -->
        <div class="row">
          <!-- Loop through all tutors 
                
                
                
          (each "person" is a tutor)-->
          <div class="col-6" v-for="course in Courses" :key="course.tutorId">
            <!-- Bootstrap card-->
            <div class="card w-100 mb-4">
              <div class="row no-gutters">
                <!-- Image will take up 3/12ths of the card, can modify to be an iamge of the course-->
                <div class="col-3">
                  <img
                    src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=802&q=80"
                    class="card-img"
                  />
                </div>

                <!--Info takes up 9/12ths of the card  here you willbe iterating through all course objects and displaying their courseName and courseId in the card-->
                <div class="col-9">
                  <div class="card-body">
                    <h5 class="card-title">{{ course.courseName }}</h5>
                    <p class="card-text">{{ course.courseId }}</p>
                    <a
                      class="btn btn-success"
                      @click="submitCouse(course.courseId)"
                      type="submit"
                      value="tutor"
                      href="#"
                      role="button"
                    >Select Course</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
var config = require("../../config");

let frontendUrlConfig = function() {
  if (process.env.NODE_ENV === "production") {
    return "https://" + config.build.host + ":" + config.build.port;
  } else {
    return "http://" + config.dev.host + ":" + config.dev.port;
  }
};
let backendUrlConfig = function() {
  if (process.env.NODE_ENV === "production") {
    return (
      "https://" + config.build.backendHost + ":" + config.build.backendPort
    );
  } else {
    return "http://" + config.dev.backendHost + ":" + config.dev.backendPort;
  }
};
var frontendUrl = frontendUrlConfig();
var backendUrl = backendUrlConfig();
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "course-list-row",
  mounted: function() {
    this.getSessions();
    //console.log("mounted: got here");
  },
  data: function() {
    return {
      message: "Session List Row",
      Courses: []
    };
  },
  methods: {
    submitCouse(courseId) {
      this.$router.push({
        name: "pickTutorByName",
        params: { courseId: courseId }
      });
    },
    getSessions: function() {
      var self = this;
      const url =
        "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR";

      var currentuser = window.sessionStorage.getItem("username");
      console.log(currentuser);
      AXIOS.get("/courses/").then(function(response) {
        console.log(JSON.stringify(response.data));
        self.Courses = response.data;
      });
    }
  }
};
</script>
