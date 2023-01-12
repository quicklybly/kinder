<template>
    <div>
        <PageHeader :loggedIn="loggedIn" @loginFlagChanged="loggedInSignal"></PageHeader>
        <PageContent v-if="loggedIn">

        </PageContent>
    </div>
</template>

<script>
import PageHeader from "@/components/PageHeader";
import PageContent from "@/components/PageContent";

export default {
    name: "BasePage",
    components: {
        PageHeader,
        PageContent
    },
    data() {
        return {
            loggedIn: false
        }
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