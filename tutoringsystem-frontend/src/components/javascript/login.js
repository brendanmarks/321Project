import axios from 'axios'
//import forge from 'node-forge'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
            AXIOS.get(`/students/` + username + '/' + password)
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data
                    this.errorLogin = ''
                    // PROBLEMS HERE
                    if (response.data !== 200) {
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