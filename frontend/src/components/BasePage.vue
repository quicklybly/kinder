<template>
  <div>
    <PageHeader></PageHeader>
    <router-view :key="$route.fullPath"/>
  </div>
</template>

<script>
import PageHeader from "@/components/header/PageHeader.vue";
import axios from "axios";
import urlConstants from "@/urlConstants";
import {userStorage} from "@/store/UserStorage.js";

export default {
  name: "BasePage",
  components: {
    PageHeader,
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