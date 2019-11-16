import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })


/*
  
function TutorDto(name,email,username,password,sessions) {
    this.name = name
    this.email = email
    this.username = username
    this.password = password
    this.sessions = sessions
}

function ReviewDto(reviewId,comment,rating){
    this.reviewId = reviewId
    this.comment = comment
    this.rating = rating
}


export default {
    name: 'pickTutor',
    data () {
        return{
            tutors: [],
            newTutor: '',
            errorTutor: '',
            reviews: [],
            newReview: '',
            errorReview: '',
            response: ''
        }
    },
    created: function () {
        
        //Test data for tutors
        const t1 = new TutorDto('sean','sean@gmail.com','seanusername','seanpassword', [])
        const t2 = new TutorDto('naes','naes@gmail.com','naesusername','naespassword', [])
        //Sample initial content for tutors
        this.tutors = [t1,t2]
        

        AXIOS.get(`/tutors`)
        .then(response => {
            // JSON responses are automatically parsed.
            this.tutor = response.data
        })
        .catch(e => {
            this.errorTutor = e;
        });
        
    },
    methods: {
        createTutor: function (name,email,username,password,sessions){
            var t = new TutorDto(name,email,username,password,sessions)
            this.tutors.push(t)
            this.newTutor=''
        }
    }
    
} 
*/



//Constructor methods
function PersonDto (name) {
    this.name = name
    this.events = []
}
  
function EventDto (name, date, start, end) {
    this.name = name
    this.eventDate = date
    this.startTime = start
    this.endTime = end
}


export default {
    
    //add data variables to the export declaration of the component
    name: 'eventregistration',
    data () {
      return {
        people: [],
        newPerson: '',
        errorPerson: '',
        response: []
      }
    },

    //add a test initialization for the data
    created: function () {
        // Test data
        const p3 = new PersonDto('Sean')
        const p1 = new PersonDto('Naes')
        const p2 = new PersonDto('qwert')
        const p4 = new PersonDto('new')
        // Sample initial content
        this.people = [p3,p1, p2,p4]
      },

    //add event handling method: createPerson()
    methods: {
        createPerson: function (personName) {
          // Create a new person and add it to the list of people
          var p = new PersonDto(personName)
          this.people.push(p)
          // Reset the name field for new people
          this.newPerson = ''
        }
      }
}

 