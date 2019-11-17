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
    name: 'verifyUser',
    data() {
        return {
            userVerified: Boolean,
        }
    },
    beforeCreate: function() {
        var currentUser = window.sessionStorage.getItem("username");
        //var loginUrl = "http://127.0.0.1:8087/#/login";

        if (currentUser == '' || currentUser == null || currentUser.equals(null)) {
            window.alert("Cannot open this page without being logged in. Please login first before accessing this page.");
            userVerified = false;
            //window.location = loginUrl;
            return userVerified;
        } else {
            console.log("The user currently logged in is :"+currentUser);
            userVerified = true;
            return userVerified;
        }
    }
}