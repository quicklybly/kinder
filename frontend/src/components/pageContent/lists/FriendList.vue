<template>
  <v-card class="scroll w-100">
    <header class="d-flex justify-space-between align-center pr-5 pl-5" v-if="totalFriends > 0">
      <span>
        {{ `Total friends: ${totalFriends}` }}
      </span>
      <v-pagination :length="totalPages"
                    :total-visible="5"
                    @update:model-value="pageChanged"
                    class="pagination"
      />
    </header>
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
      totalPages: 0,
      currentPage: 0,
      totalFriends: 0,
    }
  },
  mounted() {
    this.getFriends()
  },
  watch: {
    totalPages() {
      this.getFriends()
    },
    totalFriends() {
      this.getFriends()
    }
  },
  methods: {
    getFriends(pageNumber = this.currentPage, pageSize = 10) {
      axios.get(urlConstants.friendsURL, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        params: {
          pageSize: pageSize,
          pageNumber: pageNumber
        }
      }).then(resp => {
        this.items = resp.data.content
        this.totalFriends = resp.data.totalElements
        this.totalPages = Math.ceil(resp.data.totalElements / pageSize)
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
    },
    pageChanged(pageNumber) {
      this.currentPage = pageNumber - 1
      this.getFriends()
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