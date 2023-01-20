<template>
  <v-card
      overflow-y: auto
      class="mx-auto w-100 scroll"
  >
    <v-card-text>
      <v-text-field
          v-model="query"
          density="compact"
          variant="solo"
          label="Search for friend"
          append-inner-icon="mdi-magnify"
          single-line
          hide-details
          clearable
          @click:append-inner="search"
      ></v-text-field>
    </v-card-text>
    <user-card v-for="friend in friends" :key="friend.id" :user="friend">
      <v-btn @click="addFriend(friend.id)">
        Add
      </v-btn>
    </user-card>
  </v-card>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";
import UserCard from "@/components/pageContent/lists/UserCard.vue";

export default {
  name: "SearchForFriend",
  components: {UserCard},
  data() {
    return {
      friends: [],
      query: "",
    }
  },
  methods: {
    search() {
      axios.get(urlConstants.searchURL, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        params: {query: this.query}
      }).then(resp => {
        this.friends = resp.data
      })
    },
    addFriend(id) {
      axios.get(urlConstants.searchURL + "/" + id, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
      }).then(() => this.search())
    },
  },
}
</script>

<style scoped>
.scroll {
  overflow-y: auto;
  height: 90vh
}

</style>