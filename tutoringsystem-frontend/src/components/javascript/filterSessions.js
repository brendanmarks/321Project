import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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