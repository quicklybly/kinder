<template>
  <v-card class="scroll w-100">
    <user-card v-for="item in items" :key="item.id" :user="item">
      <v-btn @click="deleteFriend(item.id)">
        remove friend
      </v-btn>
    </user-card>
    <!--todo put this at the bottom + bug with type switch + add v-if + it's buggy with fast click-->
    <v-pagination :length="totalPages"
                  :total-visible="5"
                  @next="nextPage"
                  @prev="prevPage"
                  @update:model-value="pageChanged"
                  class="pagination"
    />
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
    }
  },
  mounted() {
    this.getFriends()
  },
  watch: {
    totalPages() {
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
    nextPage() {
      this.currentPage++
      this.getFriends()
    },
    prevPage() {
      this.currentPage--
      this.getFriends()
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