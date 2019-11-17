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