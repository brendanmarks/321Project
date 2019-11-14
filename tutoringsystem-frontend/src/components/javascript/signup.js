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
    name: 'signup',
    data() {
        return {
            name: '',
            username: '',
            userId: '',
            email: '',
            password: '',
            errorSignup: '',
            response: '',
        }
    },
    created: function () {

    },
    methods: {
        signup(name, username, userId, email, password) {
            if (name == '') {
                var errorMsg = "Invalid name"
                console.log(errorMsg)
                this.errorSignup = errorMsg
                return
            }
            if (username == '') {
                var errorMsg = "Invalid username "
                console.log(errorMsg)
                this.errorSignup = errorMsg
                return
            }
            if (userId == '') {
                var errorMsg = "Invalid user ID"
                console.log(errorMsg)
                this.errorSignup = errorMsg
                return
            }
            if (email == '') {
                var errorMsg = "Invalid email "
                console.log(errorMsg)
                this.errorSignup = errorMsg
                return
            }
            if (password == '') {
                var errorMsg = "Invalid password "
                console.log(errorMsg)
                this.errorSignup = errorMsg
                return
            }

            AXIOS.post(`/students/` + name + '?' + "email=" + email + "&username=" + username + "&password=" + password, {}, {})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data
                    console.log(this.response)
                    if (this.response.name == null) {
                        this.errorSignup = 'UserID is not a Valid Admin ID!'
                        this.response = ''
                    } else {
                        this.response = 'Admin Created!'
                        this.errorSignup = ''
                    }
                    this.name = ''
                    this.username = ''
                    this.userId = ''
                    this.email = ''
                    this.password = ''
                })
                .catch(e => {
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorSignup = errorMsg
                    this.response = ''
                });

        }
    }
}
