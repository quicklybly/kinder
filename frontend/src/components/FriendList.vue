<template>
  <v-card>
  <div v-for="item in items" :key="item.username">
    {{item}}
  </div>
  </v-card>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";

export default {
  name: "FriendList",
  data() {
    return {
      items: [],
    }
  },
  mounted() {
    this.getFriends()
  },
  methods: {
    getFriends() {
      axios.get(urlConstants.friendsURL, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true
      }).then(resp => {
        this.items = resp.data
      }).catch(ex => {
        console.log(ex)
      })
    }
  }
}
</script>

<style scoped>

</style>