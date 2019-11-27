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
function TutorialDto(tutor, course) {
    this.tutor = tutor
    this.course = course
}



export default {

    //1. add data variables to the export declaration of the component
    name: 'tutorSession',
    data() {
        return {
            tutorials: [],
            newTutorial: '',
            errorTutorial: '',
            response: [],
            message: '',
            errorLogin: '',
            selected: '',
            tutorUserName: '',
            startTime: '',
            endTime: '',
            date: '',
            tutorialId: '',
            studentName: '',
            tutorName: '',
            courseId: '',
            success: ''
        }
    },

    //2. add an initialization for the data

    created: function () {
        this.tutorName = this.$route.params.tutorName
        this.courseId = this.$route.params.courseId
        var tutorN = ''
        var courseI = ''
        courseI = this.courseId
        tutorN = this.tutorName // getting the tutor name (the tutor that was clicked on previous page)
        if (tutorN == '') {
            var errorMsg = "Missing tutor name"
            console.log(errorMsg)
            this.errorTutorial = errorMsg
        return
        }
        if (courseI == '') {
            var errorMsg = "Missing course Id"
            console.log(errorMsg)
            this.errorTutorial = errorMsg
        return
        }
    },
    methods: {
        requestSession(date1, startTime1, endTime1) {
            
            
            var tutorName = this.tutorName
            var courseId = this.courseId

            var date = date1
            var start = startTime1
            var end = endTime1
            
            let self = this
            console.log("hi")
            AXIOS.get('/tutorials/search?tutorName=' + tutorName + '&courseId=' + courseId)
                .then(response =>{
                    console.log("hi")
                    this.tutorials = response.data
                    console.log(response.data)
                })
                .catch(e => {
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorTutorial = errorMsg
                })
                .finally(function () {
                    self.postSession(date, start, end)

                });

           // var TutorialId = this.tutorials[0].id;
            
           // console.log(TutorialId)
            // once all data has been entered by user. Create session and send to DB.
           
        },
        returnHome() {
            this.$router.push('hello');
        },
        postSession(date1, startTime1, endTime1){
            if (date1 == '') {
                var errorMsg = "Invalid Date"
                //alert("a");
                console.log(errorMsg)
                this.errorTutorial = errorMsg
                return
            }
            if (startTime1 == '') {
                var errorMsg = "Invalid Start Time"
                console.log(errorMsg)
                this.errorTutorial = errorMsg
                return
            }
            if (endTime1 == '') {
                var errorMsg = "Invalid End Time"
                console.log(errorMsg)
                this.errorTutorial = errorMsg
                return
            }
            this.date = date1
            this.startTime = startTime1
            this.endTime = endTime1

            var start = this.startTime
            var end = this.endTime
            var date = this.date

            var sessionId = Math.floor(Math.random() * 10000) + 1
            var currentUser = window.sessionStorage.getItem("username")


            AXIOS.post(`/sessions/` + sessionId + `?` + 'startTime=' + start + '&endTime=' + end + '&date=' + date + '&tutorialId=' + this.tutorials[0].id + '&studentName=' + currentUser)
            .then(response => {
                this.response = response.data
                this.success = "Yay! You are now signed up."
                this.errorTutorial = ""

            })
            .catch(e => {
                var errorMsg = e.message
                console.log(errorMsg)
                this.errorTutorial = errorMsg
            });
        }
    },
    components: {
      Navigation: Navigation
    }
}

