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
        SessionRegister() {
            this.$router.push('pickCourseByName');
        },
        SessionList() {
            this.$router.push('SessionList')
        }
    }

}