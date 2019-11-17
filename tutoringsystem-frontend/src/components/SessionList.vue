<template>
  <div id="id">
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
    submitReview(sessionId) {
      this.$router.push({
        name: "ReviewSession",
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
      AXIOS.get("/sessions/student/" + currentuser).then(function(response) {
        console.log(JSON.stringify(response.data));
        self.Sessions = response.data;
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
  }
};
</script>
