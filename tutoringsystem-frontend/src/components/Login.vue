<template>
  <div id="login">
    <div class="site-section site-hero inner">
      <div class="container-fluid" id="top-container">
        <div class="container text-center" id="img-container">
          <img
            src="https://ballstateeconomics.files.wordpress.com/2014/04/tutoring-banner.png"
            width="500"
            height="100"
          />
        </div>
      </div>

      <br />
      <br />
      <br />
      <br />
      <div class="half left">
        <div class="container" align="center">
          <div class="row align-items-center" align="center">
            <div class="col-md-0" align="center" style="padding-left:10%">
              <h1 class="d-block mb-0"></h1>
            </div>
          </div>
        </div>
      </div>

      <div class="half right" data-aos="fade-up">
        <div class="container" align="center">
          <div class="login-grid" style="padding-right:0%">
            <input class="login-text" type="text" placeholder="Username" v-model="username" />
            <input class="login-text" type="password" placeholder="Password" v-model="password" />
            <input
              @click="login(username, password)"
              type="submit"
              value="Login"
              class="btn btn-primary py-2 px-4 text-white"
            />
            <br />
            <span v-if="errorLogin" style="color:red">Error: {{errorLogin}}</span>
          </div>
        </div>
      </div>

      <br />
      <input
        @click="signup()"
        type="submit"
        value="Sign Up here"
        class="btn btn-primary py-2 px-4 text-white"
      />
    </div>
  </div>
</template>

<script src="./javascript/login.js"></script>