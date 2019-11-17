import axios from 'axios'
var config = require('../../../config')
let frontendUrlConfig = function(){
    if (process.env.NODE_END === 'production'){
        return 'https://' + config.build.host + ':' + config.build.port
    }
    else {
        return 'http://' + config.dev.host + ':' + config.dev.port
    } 
}
let backendUrlConfig = function(){
    if (process.env.NODE_END === 'production'){
        return 'https://' + config.build.backendHost + ':' + config.build.backendPort
    }
    else {
        return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
    } 
}
var frontendUrl = frontendUrlConfig()
var backendUrl = backendUrlConfig()

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

    //2. add an initialization for the data,

      //Uncomment below for testing on pickTutor page

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
      }*/
    

    // Initializing people from backend
    created: function () {
        AXIOS.get(`/tutors`)
        .then(response => {
            // JSON responses are automatically parsed.
            this.tutor = response.data
        })
        .catch(e => {
            this.errorTutor = e;
        });
    }, 
    
    //3. add event handling methods
    methods: {
      
      //submit tutor to be added to reviews page and session page
      submitTutor(tutorName){
        this.$router.push({
          name: "tutor",
          params: { tutorName: tutorName }
        });
      },
      
      //create tutor from url
      createTutor: function (tutorName) {
      AXIOS.get(`/tutors/`+tutorName, {}, {})
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
}