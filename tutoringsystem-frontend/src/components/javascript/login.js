import axios from 'axios'
//import forge from 'node-forge'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'https://cors-anywhere.herokuapp.com/' + 'http://tutoringsystem-backend.herokuapp.com/'

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'login',
    data() {
        return {
            username: this.$cookie.get("username") || '',
            password: this.$cookie.get("password") || '',
            errorLogin: '',
            response: '',
        }
    },
    methods: {
        login(username, password) {
            if (username == '') {
                var errorMsg = "Invalid username"
                console.log(errorMsg)
                this.errorLogin = errorMsg
                return
            }
            if (password == '') {
                var errorMsg = "Invalid password"
                console.log(errorMsg)
                this.errorLogin = errorMsg
                return
            }
            AXIOS.get(`/students/` + username + '/')
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data
                    this.errorLogin = ''
                    // PROBLEMS HERE
                    if (this.response == 'Accepted') {
                        this.errorLogin = response.data
                        console.log(this.response)
                    }
                    else {
                        this.errorLogin = response.data
                        console.log(this.response)
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