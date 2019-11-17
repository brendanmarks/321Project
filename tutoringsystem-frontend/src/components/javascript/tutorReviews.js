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

function ReviewDto(reviewId, comment, rating) {
  this.reviewId = reviewId;
  this.comment = comment;
  this.rating = rating;
}


export default {
    
    //1. add data variables to the export declaration of the component
    name: 'tutorReviews' ,
    data () {
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

    //2. add an initialization for the data
    
    /*created: function () {
        // Test data
        const t0 = new ReviewDto('111','GreatGreat Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great Great','5')
        const t1 = new ReviewDto('222', 'Good','4')
        const t2 = new ReviewDto('333', 'Decent','3')
        const t3 = new ReviewDto('444','Meh','2')
        const t4 = new ReviewDto('555','Bad','1')

        // Sample initial content
        this.reviews = [t0,t1,t2,t3,t4]
      }*/
      /*methods: {
        createReview: function (reviewId) {
          // Create a new person and add it to the list of people
          var t = new ReviewDto(reviewId)
          this.reviews.push(t)
          // Reset the name field for new people
          this.newReview = ''
        }
      }*/

    
    created: function () {

        
        this.tutorName = 'Brad' /*this.$route.params.tutorName*/
        var tutorN = ''
        tutorN = this.tutorName
        if (tutorN == '') {
          var errorMsg = "Missing tutor name"
          //alert("a");
          console.log(errorMsg)
          this.errorReview= errorMsg
          return
        }
        
        // Initializing people from backend
        
        AXIOS.get(`/reviews?tutorName=`+ tutorN)
        .then(response => {
            // JSON responses are automatically parsed.
            this.reviews = response.data
            if(response.data.length == 0){
              this.errorReview = 'This tutor has no reviews'
            }
           
            
        })
        .catch(e => {
            this.errorReview = e.message;
            console.log(e)
        });

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