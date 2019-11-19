<!DOCTYPE html>
<template>
  <html lang="en">
    <div id="navbar">
      <Navigation></Navigation>
    </div>
    <head>
      <title>Bootstrap Example</title>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1" />
      <link
        rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"
      />
    </head>
    <body>
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
      <div class="container-fluid text-center" id="background">
        <div class="col-sm-0 sidenav"></div>
        <div class="col-sm-0">
          <div class="row">
            <br />
            <div class="container-fluid" id="welcome">
              <h2></h2>
              <br />
              <h3>Welcome !</h3>
              <hr />
            </div>
          </div>

          <br />
          <br />
          <br />
          <br />
          <div>
            <div class="container" align="left">
              <div class="login-grid">
                <input
                  @click="SessionList()"
                  type="submit"
                  value="View Personal Session Schedule"
                  class="btn btn-primary btn-lg btn-block"
                />
                <br />
                <span v-if="errorLogin" style="color:red"
                  >Error: {{ errorLogin }}</span
                >
              </div>
            </div>
          </div>
          <div class="container" align="left">
            <div class="login-grid">
              <input
                @click="SessionRegister()"
                type="submit"
                value="Register for a Tutoring Session"
                class="btn btn-primary btn-lg btn-block"
              />
              <br />
              <span v-if="errorLogin" style="color:red"
                >Error: {{ errorLogin }}</span
              >
            </div>
          </div>
        </div>
        <div class="col-sm-2 sidenav"></div>
      </div>
    </body>
  </html>
</template>

<style>
#top-container {
  margin-bottom: 0;
  margin-top: 0;
  color: #d3d3d3;
  background-color: #d3d3d3;
  color: #ffffff;
}
#welcome h2 {
  text-align: center;
  margin-top: 15px;
  margin-bottom: 10px;
  font-size: 37px;
}
#welcome h3 {
  text-align: center;
  margin-top: 5px;
  margin-bottom: 0px;
  font-size: 30px;
  font-family: Lucida;
  font-style: oblique;
  color: #d3d3d3;
}
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

<script src="./javascript/hello.js"></script>
