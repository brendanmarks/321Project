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
            this.$router.push('SessionList');
        },
        SessionList() {
            this.$router.push('SessionList')
        }
    }

}