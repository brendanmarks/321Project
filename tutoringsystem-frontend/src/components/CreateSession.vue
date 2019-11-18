<template>

<div >
    
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
            <span v-if="errorLogin" style="color:red">Error: {{errorLogin}}</span>
          </div>
        </div>
      </div>
    <div id=tutorDisplays>
        <div class="container-fluid">

            <!-- Each row will display 2 tutorials -->
            <div class="row" >

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
#tutorDisplays{
    background-color: rgb(0, 153, 153);;
}
/* Ensure the tutor options takes up 100% of width */
.container-fluid{
    width: 100%;
    max-width: 100%;
}
/* Each of the tutor cards */
.card{
    max-width: 100%;
    color: rgb(255, 248, 235);
    background-color: rgb(0, 100, 100);;
    border-style:inset;
    border-width:0.3rem;
    border-color: rgb(0, 0, 0);
}
/* Image formatting for cards */
img {
  border: 0.1rem solid rgb(0, 100, 100);
  border-radius: 0.1rem;
  padding: 0.3rem;
}
img:hover {
  box-shadow: 0 0 3px 3px rgb(0, 150, 150);
}
/* ============= Header ============== */
#headerBar{
    background-color: rgb(0, 100, 100);;
    border-style: solid;
    border-width:1rem;
    border-color: rgb(0, 63, 63);
}
#pickTutorHeader{
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    background: rgb(0, 100, 100);;
    color: rgb(255, 255, 255);
    align-self: auto;
}
#pickTutorSubHeader{
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    background: rgb(0, 100, 100);;
    color: rgb(255, 255, 255);
    align-self: auto;
}
/* ================================= */
/* Seperating all elements */
[class*="col-"] {
    padding: 0.5rem;
}

html
{
    background-color: rgb(0, 153, 153); /* The page background will be black */
}
</style>