import Navigation from '../Navigation'

export default {

    name: 'hello',
    data() {
        return {
            username: '',
            password: '',
            errorLogin: '',
            response: '',
        }
    },
    methods: {
        SessionRegister() { //if button Register is clicked on hello screen route to pickCourseByName
            this.$router.push('pickCourseByName');
        },
        SessionList() { //if button See session is clicked on hello screen route to SessionList
            this.$router.push('SessionList')
        }
    },
    components: {
        'Navigation': Navigation
    }

}