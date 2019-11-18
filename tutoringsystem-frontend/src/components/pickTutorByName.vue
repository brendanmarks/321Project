<template>
  <div>
    <div id="headerBar">
      <h1 id="pickTutorHeader">GradeSmash Tutors</h1>
      <h2 id="pickTutorSubHeader">Step 2: pick a tutor for your session</h2>
    </div>

    <!-- Here is where the tutors will be displayed -->
    <div id="tutorDisplays">
      <div class="container-fluid">
        <!-- Each row will display 2 tutors -->
        <div class="row">
          <!-- Loop through all tutors (each "person" is a tutor) -->
          <tr>
            <div
              class="col-6"
              v-for="session in Sessions"
              :key="session.tutorId"
            >
              <!-- Bootstrap card-->
              <div class="card w-100 mb-4">
                <div class="row no-gutters">
                  <!-- Image will take up 3/12ths of the card
                            TODO: Change this to include pictures of the tutors -->
                  <div class="col-3">
                    <img
                      src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=802&q=80"
                      class="card-img"
                    />
                  </div>

                  <!--Info takes up 9/12ths of the card -->
                  <div class="col-9">
                    <div class="card-body">
                      <h5 class="card-title">{{ session.name }}</h5>
                      <p class="card-text">{{ session.username }}</p>
                      <a
                        class="btn btn-success"
                        @click="MovetoTutorial()"
                        type="submit"
                        value="tutor"
                        href="#"
                        role="button"
                      >
                        Select Tutor
                      </a>

                      <a
                        class="btn btn-dark"
                        @click="submitTutor(session.name)"
                        type="submit"
                        value="tutor"
                        href="#"
                        role="button"
                      >
                        View Tutor Reviews
                      </a>
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
    MovetoTutorial() {
      this.$router.push("CreateSession");
    },
    submitTutor(tutorName) {
      this.$router.push({
        name: "TutorReviews",
        params: { tutorName: tutorName }
      });
    },

    getSessions: function() {
      var self = this;
      const url =
        "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR";
      var sessionId = this.$route.params.sessionId;
      var currentuser = window.sessionStorage.getItem("username");
      console.log(currentuser);
      AXIOS.get("/tutors/").then(function(response) {
        console.log(JSON.stringify(response.data));
        self.Sessions = response.data;
      });
    }
  }
};
</script>
