import {reactive} from 'vue'

export const userStorage = reactive({
    username: "",
    firstName: "",
    secondName: "",
    id: -1,
    userLoggedIn: false,
    userMapper(user) {
        this.username = user.username
        this.id = user.id
        this.firstName = user.firstName
        this.secondName = user.secondName
    }
})