import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })


//Constructor methods
function TutorDto(name,email,username,password,sessions) {
    this.name = name
    this.email = email
    this.username = username
    this.password = password
    this.sessions = sessions
}


export default {
    
    //1. add data variables to the export declaration of the component
    name: 'picktutor' ,
    data () {
        return {
            tutors: [],
            newTutor: '',
            errorTutor: '',
            response: []
        }
    },

    //2. add an initialization for the data
    /*
    created: function () {
        // Test data
        const t0 = new TutorDto('name','email','username','password',[])
        const t1 = new TutorDto('Sean Smith','email1','uname1','1',[])
        const t2 = new TutorDto('Shane Julie','email2','uname2','2',[])
        const t3 = new TutorDto('Elliot Merlot','email3','uname3','3',[])
        const t4 = new TutorDto('Kasper Renault','email4','uname4','4',[])
        const t5 = new TutorDto('Corally Donald','email5','uname5','5',[])
        const t6 = new TutorDto('Chantalle Mac','email6','uname6','6',[])
        const t7 = new TutorDto('Gareth Richards','email7','uname7','7',[])
        const t8 = new TutorDto('Jame Jon','email8','uname8','8',[])
        const t9 = new TutorDto('Samuel Frodo','email9','uname9','9',[])

        // Sample initial content
        this.people = [t1,t2,t3,t4,t5,t6,t7,t8,t9]
      },

      methods: {
        createPerson: function (tutorName) {
          // Create a new person and add it to the list of people
          var t = new tutorDto(tutorName)
          this.tutors.push(t)
          // Reset the name field for new people
          this.newPerson = ''
        }
      }
    */

    
    created: function () {
        // Initializing people from backend
        AXIOS.get(`/tutors`)
        .then(response => {
            // JSON responses are automatically parsed.
            this.tutor = response.data
        })
        .catch(e => {
            this.errorPerson = e;
        });
    },

    //3. add event handling method: createTutor()
    createTutor: function (tutorUserName) {
        AXIOS.get(`/tutors/`+tutorUserName, {}, {})
        .then(response => {
            // JSON responses are automatically parsed.
            this.tutor.push(response.data)
            this.newTutor = ''
            this.errorTutor = ''
        })
        .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorPerson = errorMsg
        });
    }
    
}



/*
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
        // Initializing people from backend
          AXIOS.get(`/persons`)
          .then(response => {
            // JSON responses are automatically parsed.
            this.people = response.data
          })
          .catch(e => {
            this.errorPerson = e;
          });
      },

    //add event handling method: createPerson()
    methods: {
        createPerson: function (personName) {
          // Create a new person and add it to the list of people
          var p = new PersonDto(personName)
          this.people.push(p)
          // Reset the name field for new people
          this.newPerson = ''
        } */
        /*
createPerson: function (personName) {
  AXIOS.post(`/persons/`+personName, {}, {})
  .then(response => {
    // JSON responses are automatically parsed.
    this.people.push(response.data)
    this.newPerson = ''
    this.errorPerson = ''
  })
  .catch(e => {
    var errorMsg = e.message
    console.log(errorMsg)
    this.errorPerson = errorMsg
  });
}
        
      }
}

 */