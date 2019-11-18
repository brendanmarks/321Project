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
        login() {
            this.$router.push('Login');
        },
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
                        this.errorSignup = 'UserID is not a Valid Student ID!'
                        this.response = ''
                    } else {
                        this.response = 'Student Created!'
                        this.errorSignup = ''
                    }
                    this.name = ''
                    this.username = ''
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
