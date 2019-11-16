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
                <div class="col-2">	
			        <a href="#"><button type="button" class="btn btn-dark">Select Tutor</button></a>
		        </div>
	        </div>
            
            <div class="test">
                <table>
                    <tr v-for="person in people" :key="person" >
                        <td>{{ person.name }}</td>
                        <td>
                            <ul>
                                <li v-for="event in person.events" :key="event">
                                    {{event.name}}
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <!-- ... -->
                </table>
            </div> 
            <!--
            <div class="row">
                <div class="col-md-6" v-for="tutor in tutors" v-bind="tutor.name">
                    <div class="article">
                        <div class="card">
                            <div class="card-body">
                                <h3 class="card-title">{{ post.title }}</h3>
                                <h6 class="card-subtitle text-muted">21st Dec 17</h6>
                                <p class="card-text">{{ post.content }}</p>
                            </div>
                        </div>
                    </div>
                </div>
	        </div>
            -->
        </div>

    </div>

</div>

</template>

<script src="./javascript/picktutor.js"></script>


<style>

.test{
    color: wheat;
    background-color: rgb(145, 0, 0);;
    border-style: dashed;
    border-width:2rem;
    border-color: rgb(4, 0, 63);
}

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
img:hover {
  box-shadow: 0 0 2px 1px rgba(0, 140, 186, 0.5);
}

[class*="col-"] {
    padding: 0.5rem;
}
</style>