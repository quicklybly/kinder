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
          v-on:keyup.enter="search"
      />
    </v-card-text>
    <header class="d-flex justify-space-between align-center pr-5 pl-5">
      <span>
        {{ `Total users in search: ${totalUsers}` }}
      </span>
      <v-pagination :length="totalPages"
                    :total-visible="5"
                    @update:model-value="pageChanged"
                    class="pagination"
      />
    </header>
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
      totalPages: 0,
      currentPage: 0,
      controller: null,
      totalUsers: 0,
    }
  },
  mounted() {
    this.search()
  },
  watch: {
    totalPages() {
      this.search()
    },
    totalUsers() {
      this.search()
    }
  },
  methods: {
    search(pageSize = 10) {
      if (this.controller) {
        this.controller.abort()
      }
      this.controller = new AbortController()
      axios.get(urlConstants.searchURL, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        params: {
          pageSize: 10,
          pageNumber: this.currentPage,
          query: this.query,
        },
        signal: this.controller.signal
      }).then(resp => {
        this.friends = resp.data.content
        this.totalUsers = resp.data.totalElements
        this.totalPages = Math.ceil(resp.data.totalElements / pageSize)
      })
    },
    addFriend(id) {
      axios.get(urlConstants.searchURL + "/" + id, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
      }).then(() => this.search())
    },
    pageChanged(pageNumber) {
      this.currentPage = pageNumber - 1
      this.search()
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