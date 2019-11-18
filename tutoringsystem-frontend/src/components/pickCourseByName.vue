<template>
  <div>
    <div id="headerBar">
      <h1 id="pickCourseHeader">GradeSmash Courses</h1>
      <h2 id="pickCourseSubHeader">Step 1: Pick a course for your session</h2>
    </div>

    <!-- Here is where the tutors will be displayed -->
    <div id="courseDisplays">
      <div class="container-fluid">
        <!-- Each row will display 2 tutors -->
        <div class="row">
          <!-- Loop through all tutors 
                
                
                CHANGE THIS !!!!
                
                
                (each "person" is a tutor) -->
          <div class="col-6" v-for="session in Sessions" :key="session.tutorId">
            <!-- Bootstrap card-->
            <div class="card w-100 mb-4">
              <div class="row no-gutters">
                <!-- Image will take up 3/12ths of the card -->
                <div class="col-3">
                  <img
                    src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=802&q=80"
                    class="card-img"
                  />
                </div>

                <!--Info takes up 9/12ths of the card -->
                <div class="col-9">
                  <div class="card-body">
                    <h5 class="card-title">{{ session.courseName }}</h5>
                    <p class="card-text">{{ session.courseId }}</p>
                    <a
                      class="btn btn-success"
                      @click="submitCouse(session.courseId)"
                      type="submit"
                      value="tutor"
                      href="#"
                      role="button"
                    >
                      Select Course
                    </a>
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

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;
console.log(frontendUrl);
console.log(backendUrl);
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
      Sessions: []
    };
  },
  methods: {
    submitCouse(sessionId) {
      this.$router.push({
        name: "pickTutorByName",
        params: { sessionId: sessionId }
      });
      //this.$router.push("ReviewSession", { sessionId: sessionId });
    },
    deleteSession(sessionId) {
      var self = this;
      const url =
        "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR";

      var currentuser = window.sessionStorage.getItem("username");
      AXIOS.delete("/sessions/" + sessionId, {}, {}).then(function(response) {
        self.Sessions = response.data;
        this.$router.push("Hello");
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
        self.Sessions = response.data;
      });
    }
  }
};
</script>
