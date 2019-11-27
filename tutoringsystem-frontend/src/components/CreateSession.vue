<template>
  <div>
    <div class="container-fluid" id="top-container">
      <div class="container text-center" id="img-container">
        <img
          src="\static\banner.PNG"
          width="750"
          height="150"
        />
      </div>
    </div>
    <div id="headerBar">
      <h1 id="pickTutorHeader">Request a Session:</h1>
    </div>
    <span v-if="errorTutorial" style="color:red">{{ errorTutorial }}</span>
    <!-- Here is where the tutors will be displayed -->
    <div class="half right" data-aos="fade-up">
      <div class="container" align="center">
        <div class="login-grid" style="padding-right:0%">
          <input class="login-text" type="text" placeholder="Date" v-model="date" />
          <input class="login-text" type="text" placeholder="Start Time" v-model="startTime" />
          <input class="login-text" type="text" placeholder="End Time" v-model="endTime" />
          <input
            @click="requestSession(date, startTime, endTime)"
            type="submit"
            value="Request Session"
            class="btn btn-primary py-2 px-4 text-white"
          />
          <br />
          <span v-if="errorLogin" style="color:red">Error: {{ errorLogin }}</span>
          <div>
            <span v-if="success" style="color:green">Congrats: {{ success }}</span>
          </div>
        </div>
      </div>
    </div>
    <div>
      <input
        @click="returnHome()"
        type="submit"
        value="Return Home"
        class="btn btn-primary py-2 px-4 text-white"
      />
    </div>
  </div>
</template>

<script src="./javascript/createSession.js"></script>

<style>
/* ============= For the tutor cards ============== */
/* Container where all the cards will be stored  */
/* ================================= */
/* Seperating all elements */
[class*="col-"] {
  padding: 0.5rem;
}
</style>
