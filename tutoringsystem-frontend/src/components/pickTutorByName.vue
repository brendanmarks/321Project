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
      <h2 id="pickTutorSubHeader">Step 2: pick a tutor for your session</h2>
    </div>

    <!-- Here is where the tutors will be displayed -->
    <div id="tutorDisplays">
      <div class="container-fluid">
        <!-- Each row will display 2 tutors -->
        <div class="row">
          <!-- Loop through all tutors (each "person" is a tutor) -->
          <tr>
            <div class="col-6" v-for="tutor in Tutors" :key="tutor.tutorId">
              <!-- Bootstrap card-->
              <div class="card w-100 mb-4">
                <div class="row no-gutters">
                  <!-- Image will take up 3/12ths of the card
                  TODO: Change this to include pictures of the tutors-->
                  <div class="col-3">
                    <img
                      src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=802&q=80"
                      class="card-img"
                    />
                  </div>

                  <!--Info takes up 9/12ths of the card -->
                  <div class="col-9">
                    <div class="card-body">
                      <h5 class="card-title">{{ tutor.name }}</h5>
                      <p class="card-text">{{ tutor.username }}</p>
                      <a
                        class="btn btn-success"
                        @click="MovetoTutorial(tutor.name)"
                        type="submit"
                        value="tutor"
                        href="#"
                        role="button"
                      >Select Tutor</a>

                      <a
                        class="btn btn-dark"
                        @click="submitTutor(tutor.name)"
                        type="submit"
                        value="tutor"
                        href="#"
                        role="button"
                      >View Tutor Reviews</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </tr>
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
      Tutors: []
    };
  },
  methods: {
    MovetoTutorial(tutorName) {
      var courseId = this.$route.params.courseId;
      this.$router.push({
        name: "CreateSession",
        params: { tutorName: tutorName, courseId: courseId }
      });
    },

    submitTutor(tutorName) {
      this.$router.push({
        name: "TutorReviews",
        params: { tutorName: tutorName }
      });
    },
    getSessions: function() {
      // get all the tutors for that course
      var self = this;
      var courseId = this.$route.params.courseId;
      alert(courseId);
      AXIOS.get("/tutors/course?courseId=" + courseId).then(function(response) {
        console.log(JSON.stringify(response.data));
        self.Tutors = response.data;
      });
    }
  }
};
</script>
