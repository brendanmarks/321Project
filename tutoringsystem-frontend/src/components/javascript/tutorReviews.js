import axios from 'axios'
import Navigation from "../Navigation";

var config = require('../../../config')

let frontendUrlConfig = function () {
  if (process.env.NODE_ENV === 'production') {
    return 'https://' + config.build.host + ':' + config.build.port
  }
  else {
    return 'http://' + config.dev.host + ':' + config.dev.port
  }
}
let backendUrlConfig = function () {
  if (process.env.NODE_ENV === 'production') {
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
function TutorDto(name, email, username, password, sessions) {
  this.name = name
  this.email = email
  this.username = username
  this.password = password
  this.sessions = sessions
}

function ReviewDto(reviewId, comment, rating) {
  this.reviewId = reviewId;
  this.comment = comment;
  this.rating = rating;
}


export default {

  //1. add data variables to the export declaration of the component
  name: 'tutorReviews',
  data() {
    return {
      reviews: [],
      newReview: '',
      errorReview: '',
      response: [],
      message: '',
      errorLogin: '',
      selected: '',
      tutorName: ''
    }
  },

  created: function () {


    this.tutorName = this.$route.params.tutorName
    var tutorN = ''
    tutorN = this.tutorName // getting the tutor name (the tutor that was clicked on previous page)
    if (tutorN == '') {
      var errorMsg = "Missing tutor name"
      console.log(errorMsg)
      this.errorReview = errorMsg
      return
    }

    // Initializing people from backend
    AXIOS.get(`/reviews?tutorName=` + tutorN) // try to get the specific tutors reviews from db
      .then(response => {
        // JSON responses are automatically parsed.
        this.reviews = response.data
        if (response.data.length == 0) {
          this.errorReview = 'This tutor has no reviews'
        }


      })
      .catch(e => {
        this.errorReview = e.message;
        console.log(e)
      });

  },
  components: {
    Navigation: Navigation
  }

  //3. add event handling method: createTutor()
  /*methods: {
    createReview: function (tutorName) {
        AXIOS.get(`/reviews`+tutorName, {}, {})
        .then(response => {
            // JSON responses are automatically parsed.
            this.reviews.push(response.data)
            this.newReview = ''
            this.errorReview = ''
        })
        .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorReview = errorMsg
        });
    }
  }*/
}