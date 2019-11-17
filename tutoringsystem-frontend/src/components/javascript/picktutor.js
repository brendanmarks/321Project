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
        /*
        //Test data for tutors
        const t1 = new TutorDto('sean','sean@gmail.com','seanusername','seanpassword', [])
        const t2 = new TutorDto('naes','naes@gmail.com','naesusername','naespassword', [])
        //Sample initial content for tutors
        this.tutors = [t1,t2]
        */

        // Initializing tutors from backend
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

