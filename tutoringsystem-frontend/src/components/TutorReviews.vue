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
      <h1 id="pickTutorHeader">Tutor Reviews:</h1>
      <h2 id="pickTutorSubHeader">{{ tutorName }}</h2>
    </div>
    <span v-if="errorReview" style="color:red">{{ errorReview }}</span>
    <!-- Here is where the tutors will be displayed -->
    <div id="tutorDisplays">
      <div class="container-fluid">
        <!-- Each row will display 2 tutors -->
        <div class="row">
          <!-- Loop through all tutors (each "person" is a tutor) -->
          <div class="col-6" v-for="review in reviews" :key="review">
            <!-- Bootstrap card-->
            <div class="card w-100 mb-4">
              <div class="row no-gutters">
                <!--Info takes up 9/12ths of the card -->
                <div class="col-12">
                  <div class="card-body">
                    <h5 class="card-title">Rating: {{ review.rating }}</h5>
                    <p class="card-text">Comment: {{ review.comment }}</p>
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

<script src="./javascript/tutorReviews.js"></script>

<style>
/* ============= For the tutor cards ============== */
/* Container where all the cards will be stored  */

/* ================================= */
/* Seperating all elements */
[class*="col-"] {
  padding: 0.5rem;
}
</style>
