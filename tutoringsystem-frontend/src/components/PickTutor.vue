<template>

<div >
    <div id="headerBar">
        <h1 class="pickTutorHeader">GradeSmash Tutors</h1>
        <h2 class="pickTutorSubHeader">Step 2: pick a tutor for your session</h2>
    </div>
    <div id=tutorDisplays>
        <div class="container-fluid">
	        <div class="row">
		        <div class="col-8">		
			        <a href="#"><img class="tutorImg" src="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=802&q=80"></a>
		        </div>
                <div class="col-2">		
			        <a href="#"><button type="button" class="btn btn-dark">View Reviews</button></a>
		        </div>
                <div class="col-2">		<!--Define the number of columns for different size of displays-->
			        <a href="#"><button type="button" class="btn btn-dark">Select Tutor</button></a>
		        </div>
	        </div>
        </div>
    </div>

</div>

</template>

<script src="./javascript/pickTutor.js"></script>


<style>

#headerBar{
    background-color: rgb(0, 100, 100);;
    border-style: solid;
    border-width:1rem;
    border-color: rgb(0, 63, 63);
}

.pickTutorHeader{
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    background: rgb(0, 100, 100);;
    color: rgb(255, 255, 255);
    align-self: auto;
}

.pickTutorSubHeader{
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    background: rgb(0, 100, 100);;
    color: rgb(255, 255, 255);
    align-self: auto;
}

#tutorDisplays{
    background-color: rgb(0, 153, 153);;
    border-style: solid;
    border-width:1rem;
    border-color: rgb(0, 63, 63);
}

img {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 5px;
  width: 250px;
}

[class*="col-"] {
    padding: 0.5rem;
}
</style>