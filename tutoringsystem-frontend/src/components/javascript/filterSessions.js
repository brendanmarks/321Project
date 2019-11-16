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
        login() {
            var currentuser = window.sessionStorage.getItem("username")
            console.log(currentuser)
            AXIOS.get(`/sessions/` + currentuser + '/')
                .then(response => {
                    // JSON responses are automatically parsed.
                    if (response.data !== 200) {
                        this.errorLogin = response.data
                        console.log(this.response)
                    } else {
                        this.$router.push('SessionList');
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