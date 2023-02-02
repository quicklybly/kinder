import {createRouter, createWebHashHistory} from 'vue-router'
import MainView from "@/views/MainView.vue";
import ProfileView from "@/views/ProfileView.vue";

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            name: 'MainView',
            component: MainView
        },
        {
            path: '/profile/:id',
            name: 'ProfileView',
            props: true,
            component: ProfileView
        }
    ]
})

export default router
