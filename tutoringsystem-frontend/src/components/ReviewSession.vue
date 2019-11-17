<!DOCTYPE html>
<template>
<html lang="en">
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
            <h3>Write a Review</h3>
            <hr />
          </div>
        </div>

        <br />
        <br />
        <label>rate your tutor on 5 stars</label>
        <div>
          <select v-model="selected">
            <option disabled value>Please select one</option>
            <option>5</option>
            <option>4</option>
            <option>3</option>
            <option>2</option>
            <option>1</option>
          </select>
        </div>

        <br />
        <br />

        <div>
          <div class="form-group">
            <label form="exampleFormControlTextarea1">Let us know how the tutor was!</label>
            <input class="form-control" v-model="message" />
          </div>
        </div>

        <div class="container" align="left">
          <div class="login-grid">
            <input
              @click="createReview(rating, message)"
              type="submit"
              value="Submit review"
              class="btn btn-primary btn-lg btn-block"
            />
            <br />
            <span v-if="errorLogin" style="color:red">Error: {{errorLogin}}</span>
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
</style>


