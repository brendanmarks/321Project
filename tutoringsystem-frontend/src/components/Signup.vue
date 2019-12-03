<template>
  <div id="Signup">
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
            <div class="col-md-10" align="center" style="padding-left:10%">
              <h1 class="d-block mb-4">&nbsp; &nbsp; &nbsp; &nbsp; Sign Up</h1>
            </div>
          </div>
        </div>
      </div>

      <div class="half right" data-aos="fade-up">
        <div class="container" align="center">
          <hr />
          <input class="login-text" type="text" placeholder="Name" v-model="username" />
          <input class="login-text" type="text" placeholder="Username" v-model="name" />
          <input class="login-text" type="text" placeholder="Email" v-model="email" />
          <input class="login-text" type="password" placeholder="Password" v-model="password" />
          <input
            @click="signup(name, username, userId, email, password)"
            type="submit"
            value="Sign Up"
            class="btn btn-primary py-2 px-4 text-white"
          />
          <br />
          <span v-if="errorSignup" style="color:red">Error: {{ errorSignup }}</span>
          <span v-if="response" style="color:green">Success: {{ response }}</span>
        </div>
      </div>

      <input
        @click="login()"
        type="submit"
        value="Go to Login"
        class="btn btn-primary py-2 px-4 text-white"
      />
    </div>
  </div>
</template>

<script src="./javascript/signup.js"></script>