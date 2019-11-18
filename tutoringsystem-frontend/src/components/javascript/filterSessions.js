import axios from 'axios'
var config = require('../../../config')
let frontendUrlConfig = function(){
    if (process.env.NODE_ENV === 'production'){
        return 'https://' + config.build.host + ':' + config.build.port
    }
    else {
        return 'http://' + config.dev.host + ':' + config.dev.port
    } 
}
let backendUrlConfig = function(){
    if (process.env.NODE_ENV === 'production'){
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
    name: 'SessionList',
    data() {
        return {
            sessions: [],
            date: '',
            errorLogin: '',
            response: '',
        }
    },
    methods: {
        SessionList() {
            //alert("a")
            this.$router.push('SignUp');
        },
        getSessions() {
            //alert("a")
            this.$router.push('SessionList');
            var currentuser = window.sessionStorage.getItem("username")
            console.log(currentuser)
            AXIOS.get('/sessions/')
                .then(response => {
                    alert(response.data)
                    this.sessions = response.data
                    console.log("hi" + sessions)
                })
                .catch(e => {
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorLogin = errorMsg
                });
        }
    }
}