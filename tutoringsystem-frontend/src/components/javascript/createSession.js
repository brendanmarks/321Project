import axios from 'axios'
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
            studentName: ''
        }
    },

    //2. add an initialization for the data

    created: function () {
    },
    methods: {
        requestSession(date1, startTime1, endTime1) {
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
            var tutorialId = 666
            console.log(start)
            console.log(end)
            console.log(date)

            AXIOS.post(`/sessions/` + sessionId + `?` + 'startTime=' + start + '&endTime=' + end + '&date=' + date + '&tutorialId=' + tutorialId + '&studentName=' + currentUser)
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data
                })
                .catch(e => {
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorTutorial = errorMsg
                });
        },
        returnHome() {
            this.$router.push('hello');
        }
    }
}

