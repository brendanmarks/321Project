import axios from 'axios'
var config = require('../../../config')
let frontendUrlConfig = function(){
    if (process.env.NODE_END === 'production'){
        return 'https://' + config.build.host + ':' + config.build.port
    }
    else {
        return 'http://' + config.dev.host + ':' + config.dev.port
    } 
}
let backendUrlConfig = function(){
    if (process.env.NODE_END === 'production'){
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


function CourseDto(id) {
    this.id = id
}

export default {
    name: 'pickcourse',
    data () {
        return {
            courses: [],
            newCourse: '',
            errorCourse: '',
            response: []
        }
    },
    created: function () {
        const c1 = new CourseDto(math240)
        const c2 = new CourseDto(comp251)
        this.courses = [c1,c2]
    },
    method: {
        createPerson: function (personName) {
            var c = new CourseDto(id)
            this.courses.push(c)
            this.newCourse = ''
        }
    }
}