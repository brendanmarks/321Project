import axios from 'axios'
import Navigation from "../Navigation";

//import forge from 'node-forge'
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

export default {
    name: 'signup',
    data() {
        return {
            sessionId: '',
            message: '',
            rating: ''
        }
    },
    created: function () {
    },
    methods: {
        reviewSession(message, rating) {
            // review (randomly generate), session id, review id, message, rating, currentUser
            var currentuser = window.sessionStorage.getItem("username")
            var sessionId = this.$route.params.sessionId
            console.log(currentuser)
            console.log(message)
            console.log(rating)
            //we can generate review id
            var name = "studentName"
            var reviewId = Math.floor(Math.random() * 10000) + 1
            AXIOS.post(`/reviews/` + sessionId + '/' + reviewId + '?' + "comment=" + message + "&rating=" + rating + "&studentName=" + "studentName", {}, {})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data
                    console.log(this.response)
                    if (response.data !== 200) {
                        this.$router.push('SessionList')
                    }
                })
                .catch(e => {
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorSignup = errorMsg
                    this.response = ''
                });


        }
    },
    components: {
      Navigation: Navigation
    }
}