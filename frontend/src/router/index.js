import {createRouter, createWebHistory} from 'vue-router'
import MainView from "@/routerViews/MainView.vue";
import ProfileView from "@/routerViews/ProfileView.vue";

const router = createRouter({
    history: createWebHistory(),
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