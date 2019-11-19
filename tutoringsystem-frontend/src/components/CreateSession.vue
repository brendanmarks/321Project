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
      <h1 id="pickTutorHeader">Request a Session:</h1>
    </div>
    <span v-if="errorTutorial" style="color:red">{{ errorTutorial }}</span>
    <!-- Here is where the tutors will be displayed -->
    <div class="half right" data-aos="fade-up">
      <div class="container" align="center">
        <div class="login-grid" style="padding-right:0%">
          <input
            class="login-text"
            type="text"
            placeholder="Date"
            v-model="date"
          />
          <input
            class="login-text"
            type="text"
            placeholder="Start Time"
            v-model="startTime"
          />
          <input
            class="login-text"
            type="text"
            placeholder="End Time"
            v-model="endTime"
          />
          <input
            @click="requestSession(date, startTime, endTime)"
            type="submit"
            value="Request Session"
            class="btn btn-primary py-2 px-4 text-white"
          />
          <br />
          <span v-if="errorLogin" style="color:red"
            >Error: {{ errorLogin }}</span
          >
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
    <div id="tutorDisplays">
      <div class="container-fluid">
        <!-- Each row will display 2 tutorials -->
        <div class="row">
          <!-- Loop through all tutorials (each "person" is a tutor) -->
          <div class="col-6" v-for="tutorial in tutorials" :key="tutorial">
            <!-- Bootstrap card-->
            <div class="card w-100 mb-4">
              <div class="row no-gutters">
                <!--Info takes up 9/12ths of the card -->
                <div class="col-12">
                  <div class="card-body">
                    <h5 class="card-title">Rating:</h5>
                    <p class="card-text">Comment:</p>
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
