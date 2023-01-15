<template>
    <div>
        <PageHeader :loggedIn="loggedIn" @loginFlagChanged="loggedInSignal"></PageHeader>
        <PageContent v-if="loggedIn">
        </PageContent>
        <PagePlaceHolder v-else></PagePlaceHolder>
    </div>
</template>

<script>
import PageHeader from "@/components/PageHeader";
import PageContent from "@/components/PageContent";
import PagePlaceHolder from "@/components/PagePlaceHolder.vue";
import axios from "axios";
import urlConstants from "@/urlConstants";

export default {
    name: "BasePage",
    components: {
        PagePlaceHolder,
        PageHeader,
        PageContent
    },
    data() {
        return {
            loggedIn: false
        }
    },
    mounted() {
        axios.get(urlConstants.existsURL, {
            "Access-Control-Allow-Origin": "http://localhost:8000/exists",
            withCredentials: true,
            'Access-Control-Allow-Credentials': true,
        }).then(resp => {
            this.loggedIn = resp.data
        }).catch(error => {
            console.log(error)
        })
    },
    methods: {
        loggedInSignal(flag) {
            this.loggedIn = flag;
        }
    },
}
</script>

<style scoped>

</style>