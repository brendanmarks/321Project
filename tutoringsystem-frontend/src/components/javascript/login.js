import axios from 'axios'
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
    name: 'login',
    data() {
        return {
            username: '',
            password: '',
            errorLogin: '',
            response: '',
        }
    },
    methods: {
        signup() { // if user hasnt signed up yet push to sign up page
            this.$router.push('SignUp');
        },
        login(username, password) { //once user has clicked on login
            if (username == '') { // if username field is empty throw error
                var errorMsg = "Invalid username"
                //alert("a");
                console.log(errorMsg)
                this.errorLogin = errorMsg
                return
            }
            if (password == '') { // if password field is empty throw error
                var errorMsg = "Invalid password"
                console.log(errorMsg)
                this.errorLogin = errorMsg
                return
            }
            AXIOS.get(`/students/` + username + '/' + password) //else you will try and retreive student from DB
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data
                    window.sessionStorage.setItem("username", username)
                    this.errorLogin = ''
                    if (response.data !== 200) { //if the respoce was 200 (actually exists in DB route to hello page)
                        this.errorLogin = response.data
                        console.log(this.response)
                    } else {
                        this.$router.push('Hello');
                    }
                })
                .catch(e => {
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorLogin = errorMsg
                });
        }
    }
}