import {reactive} from 'vue'

export const userStorage = reactive({
    username: "",
    firstName: "",
    lastName: "",
    id: -1,
    userLoggedIn: false,
    userMapper(user) {
        this.username = user.username
        this.id = user.id
        this.firstName = user.firstName
        this.lastName = user.lastName
    }
})