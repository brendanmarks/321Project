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
          <div class="col-6" v-for="person in people" :key="person">
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
                    <h5 class="card-title">{{ person.name }}</h5>
                    <p class="card-text">{{ person.username }}</p>
                    <a
                      class="btn btn-success"
                      @click="submitTutor(person.name)"
                      type="submit"
                      value="tutor"
                      href="#"
                      role="button"
                    >
                      Select Tutor
                    </a>

                    <a
                      class="btn btn-dark"
                      @click="submitTutor(person.name)"
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
        </div>
      </div>
    </div>
  </div>
</template>

<style>
/* ============= For the tutor cards ============== */

/* Container where all the cards will be stored  */
#tutorDisplays {
  background-color: #dc3545;
}

/* Ensure the tutor options takes up 100% of width */
.container-fluid {
  width: 100%;
  max-width: 100%;
  font-family: Lucida;
  font-style: oblique;
}

/* Each of the tutor cards */
.card {
  max-width: 100%;
  color: rgb(255, 248, 235);
  background-color: #dc3545;
  border-style: inset;
  border-width: 0.3rem;
  border-color: rgb(0, 0, 0);
  font-family: Lucida;
  font-style: oblique;
}

/* Image formatting for cards */
img {
  border: 0.1rem solid #dc3545;
  border-radius: 0.1rem;
  padding: 0.3rem;
}
img:hover {
  box-shadow: 0 0 3px 3px rgb(255, 255, 255);
}

/* ============= Header ============== */

#headerBar {
  background-color: #dc3545;
  border-style: solid;
  border-width: 1rem;
  border-color: #db1327;
  font-size: 30px;
  font-family: Lucida;
  font-style: oblique;
}

#pickTutorHeader {
  font-family: Lucida;
  font-style: oblique;
  background: #dc3545;
  color: rgb(255, 255, 255);
  align-self: auto;
}

#pickTutorSubHeader {
  font-family: Lucida;
  font-style: oblique;
  background: #dc3545;
  color: rgb(255, 255, 255);
  align-self: auto;
}

/* ================================= */

/* Seperating all elements */
[class*="col-"] {
  padding: 0.5rem;
}
</style>

<script>
import axios from "axios";
import Navigation from "./Navigation";
var config = require("../../../config");
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
      Tutors: []
    };
  },
  methods: {
    submitReview(sessionId) {
      this.$router.push({
        name: "ReviewSession",
        params: { sessionId: sessionId }
      });
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
    getTutors: function() {
      var self = this;
      const url =
        "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR";

      var currentuser = window.sessionStorage.getItem("username");
      console.log(currentuser);
      AXIOS.get("/tutors/").then(function(response) {
        console.log(JSON.stringify(response.data));
        self.Tutors = response.data;
      });
      /*
      AXIOS.get("/sessions/")
        .then(function(response) {
          console.log(JSON.stringify(response.data));
          self.Sessions = response.data;
        })
        .catch(function(error) {
          console.log(error);
        });
        */
    }
  },
  components: {
    Navigation: Navigation
  }
};
</script>
