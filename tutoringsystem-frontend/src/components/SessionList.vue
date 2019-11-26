<template>
  <div id="id">
    <div id="navbar">
      <Navigation></Navigation>
    </div>
    <div class="site-wrap">
      <div class="container-fluid" id="top-container">
        <div class="container text-center" id="img-container">
          <img
            src="https://ballstateeconomics.files.wordpress.com/2014/04/tutoring-banner.png"
            width="500"
            height="100"
          />
        </div>
      </div>
      <div class="container-fluid text-center" id="background">
        <div class="site-section" style="padding-top:10%">
          <div class="container" style="align:center">
            <h1 class="page-title">My Sessions</h1>
            <div id="table">
              <table
                class="table table-bordered"
                style="width: 100%; height: 100%;"
              >
                <!-- Iterate through all session objects. Print on each line line of table all session criteria (with two buttons one to direct you to writing a review. Other one is to cancel the session-->
                <tr>
                  <th style="padding:5px">SessionId</th>
                  <th style="padding:5px">StartTime</th>
                  <th style="padding:5px">EndTime</th>
                  <th style="padding:5px">Date</th>
                  <th style="padding:5px">Room Number</th>
                  <th style="padding:5px">Write a review</th>
                  <th style="padding:5px">Refuse a session</th>
                </tr>
                <tr v-for="session in Sessions" :key="session.sessionId">
                  <td>{{ session.sessionId }}</td>
                  <td>{{ session.startTime }}</td>
                  <td>{{ session.endTime }}</td>
                  <td>{{ session.date }}</td>
                  <td>{{ "mc206" }}</td>
                  <td>
                    <input
                      @click="submitReview(session.sessionId)"
                      type="submit"
                      value="review"
                      class="btn btn-primary py-2 px-4 text-white"
                    />
                  </td>
                  <td>
                    <input
                      @click="deleteSession(session.sessionId)"
                      v-if="checkEndDate(session)"
                      type="submit"
                      value="cancel"
                      class="btn btn-primary py-2 px-4 text-white"
                    />
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
#navbar {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  font-size: 20px;
}
</style>

<script>
import axios from "axios";
import Navigation from "./Navigation";

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
  },
  data: function() {
    return {
      // initialise sessions array
      message: "Session List Row",
      Sessions: []
    };
  },
  methods: {
    submitReview(sessionId) {
      // if user wants to submit review
      this.$router.push({
        name: "ReviewSession",
        params: { sessionId: sessionId }
      });
    },
    checkEndDate(session){
      var endDate = new Date(session.date + "T" + session.endTime);
      var now = new Date();
      return !(endDate < now);
    },
    deleteSession(sessionId) {
      var self = this;
      var currentuser = window.sessionStorage.getItem("username");
      AXIOS.delete("/sessions/" + sessionId, {}, {}).then(function(response) {
        for (var i = 0; i < self.Sessions.length; i++){
          if (self.Sessions[i].sessionId === sessionId){
            self.Sessions.splice(i,1);
            break;
          }
        }
      });
    },
    getSessions: function() {
      var self = this;
      var currentuser = window.sessionStorage.getItem("username");
      console.log(currentuser);
      AXIOS.get("/sessions/student/" + currentuser).then(function(response) {
        // get all the sessions for the current logged user using the username stored in the cache
        console.log(JSON.stringify(response.data));
        self.Sessions = response.data; // put all the JSON responses in the sessions array
      });
    }
  },
  components: {
    Navigation: Navigation
  }
};
</script>
