<template>
  <div>
    <PageHeader></PageHeader>
    <PageContent v-if="store.userLoggedIn">
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
import {userStorage} from "@/dataObjects/UserStorage.js";

export default {
  name: "BasePage",
  components: {
    PagePlaceHolder,
    PageHeader,
    PageContent
  },
  data() {
    return {
      store: userStorage
    }
  },
  mounted() {
    axios.get(urlConstants.existsURL, {
      "Access-Control-Allow-Origin": "http://localhost:8000/",
      withCredentials: true,
      'Access-Control-Allow-Credentials': true,
    }).then((resp) => {
      this.store.userMapper(resp.data)
      this.store.userLoggedIn = true
    }).catch(() => {
      this.store.userLoggedIn = false
    })
  },
  watch: {
    "store.userLoggedIn"(flag) {
      if (!flag) {
        this.clearCookie();
      }
    }
  },
  methods: {
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