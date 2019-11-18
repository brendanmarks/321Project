<template>

<div >
    
    <div id="headerBar">
        <h1 id="pickCourseHeader">GradeSmash Courses</h1>
        <h2 id="pickCourseSubHeader">Step 2: pick a course for your session</h2>
    </div>

    <!-- Here is where the tutors will be displayed -->
    <div id=courseDisplays>
        <div class="container-fluid">

            <!-- Each row will display 2 tutors -->
            <div class="row" >

                <!-- Loop through all tutors 
                
                
                CHANGE THIS !!!!
                
                
                (each "person" is a tutor) -->
                <div class="col-6" v-for="person in people" :key="person">
                    
                    <!-- Bootstrap card-->
                    <div class="card w-100 mb-4">
                        <div class="row no-gutters">
                            
                            <!-- Image will take up 3/12ths of the card -->
                            <div class="col-3">
                                <img src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=802&q=80" class="card-img">
                            </div>
                            
                            <!--Info takes up 9/12ths of the card -->
                            <div class="col-9">
                                <div class="card-body">
                                
                                    <h5 class="card-title">{{person.name}}</h5>
                                    <p class="card-text">{{person.username}}</p>
                                    <a class="btn btn-success" href="#" role="button">Select Tutor</a>
                                    <a class="btn btn-dark" href="#" role="button">View Tutor Reviews</a>

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

<script src="./javascript/pickcourse.js"></script>


<style>

/* ============= For the course cards ============== */

/* Container where all the cards will be stored  */
#courseDisplays{
    background-color: #dc3545;
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
    background-color: #dc3545;
    border-style:inset;
    border-width:0.3rem;
    border-color: rgb(0, 0, 0);
    font-family: Lucida;
    font-style: oblique;
}

/* Image formatting for cards */
img {
  border: 0.1rem solid #dc3545;
  border-radius: 0.1rem;
  padding: 0.3rem;
}
img:hover {
  box-shadow: 0 0 3px 3px rgb(255, 255, 255);
}


/* ============= Header ============== */

#headerBar{
    background-color: #dc3545;
    border-style: solid;
    border-width:1rem;
    border-color: #db1327;
    font-size: 30px;
    font-family: Lucida;
    font-style: oblique;
}

#pickCourseHeader{
    font-family: Lucida;
    font-style: oblique;
    background: #dc3545;
    color: rgb(255, 255, 255);
    align-self: auto;
}

#pickCourseSubHeader{
    font-family: Lucida;
    font-style: oblique;
    background: #dc3545;
    color: rgb(255, 255, 255);
    align-self: auto;
}

/* ================================= */

/* Seperating all elements */
[class*="col-"] {
    padding: 0.5rem;
}


</style>