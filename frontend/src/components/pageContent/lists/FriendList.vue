<template>
  <v-card class="scroll w-100">
    <user-card v-for="item in items" :key="item.id" :user="item">
      <v-btn @click="deleteFriend(item.id)">
        remove friend
      </v-btn>
    </user-card>
  </v-card>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";
import UserCard from "@/components/pageContent/lists/UserCard.vue";

export default {
  name: "FriendList",
  components: {UserCard},
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
    },
    deleteFriend(id) {
      axios.delete(urlConstants.friendsURL + "/" + id, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true
      }).then(() => this.getFriends())
    }
  }
}
</script>

<style scoped>
.scroll {
  overflow-y: auto;
  height: 90vh
}

</style>