<template>
  <div>
    <PageHeader :loggedIn="loggedIn" @loginFlagChanged="loggedInSignal"></PageHeader>
    <PageContent v-if="loggedIn">
    </PageContent>
    <PagePlaceHolder v-else></PagePlaceHolder>
  </div>
</template>

<script>
import PageHeader from "@/components/header/PageHeader.vue";
import PageContent from "@/components/pageContent/PageContent.vue";
import PagePlaceHolder from "@/components/pageContent/PagePlaceHolder.vue";
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
      "Access-Control-Allow-Origin": "http://localhost:8000/",
      withCredentials: true,
      'Access-Control-Allow-Credentials': true,
    }).then(() => {
      this.loggedIn = true
    }).catch(() => {
      this.loggedIn = false
    })
  },
  methods: {
    loggedInSignal(flag) {
      this.loggedIn = flag;
      if (!flag) {
        this.clearCookie();
      }
    },
    clearCookie() {
      axios.get(urlConstants.signOutURL, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
      })
    }
  },
}
</script>

<style scoped>

</style>