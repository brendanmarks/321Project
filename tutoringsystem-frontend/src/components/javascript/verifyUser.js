import axios from 'axios'
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
    name: 'verifyUser',
    data() {
        return {
            userVerified: Boolean,
        }
    },
    beforeCreate: function () {
        var currentUser = window.sessionStorage.getItem("username"); // get the username session variable when you login

        if (currentUser == '' || currentUser == null || currentUser.equals(null)) { // here you cant navigate to a page is the username stored as a session variable is not there. (meaning you havent logged in)
            window.alert("Cannot open this page without being logged in. Please login first before accessing this page.");
            userVerified = false;
            return userVerified;
        } else { // here user is currently logged in. can navigate as he likes
            console.log("The user currently logged in is :" + currentUser);
            userVerified = true;
            return userVerified;
        }
    }
}