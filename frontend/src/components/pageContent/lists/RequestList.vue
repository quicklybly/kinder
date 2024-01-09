<template>
  <v-card class="w-100 scroll">
    <div class="select-request-type-menu">
      <v-select
          v-model="requestType"
          :items="requestTypeEnum"
          @update:menu="_ => currentPage = 0"
      >
      </v-select>
      <header v-if="totalRequests > 0" class="d-flex justify-space-between align-center pr-5 pl-5">
        <span>
          {{ `Total requests ${requestType}: ${totalRequests}` }}
        </span>
        <v-pagination :length="totalPages"
                      :total-visible="5"
                      @update:model-value="pageChanged"
                      class="pagination"
        />
      </header>
      <user-card v-for="item in items" :key="item.id" :user="item">
        <div v-if="requestType===this.requestTypeEnum[1]">
          <div>
            <v-btn @click="answerRequest(item.id, 'ACCEPT')" class="mr-3">
              Accept
            </v-btn>
            <v-btn @click="answerRequest(item.id, 'DECLINE')">
              Decline
            </v-btn>
          </div>
        </div>
        <div v-else>
          <v-btn @click="answerRequest(item.id, 'DECLINE')">
            Cancel request
          </v-btn>
        </div>
      </user-card>
    </div>
  </v-card>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";
import UserCard from "@/components/pageContent/lists/UserCard.vue";

const requestTypeEnumNotReactive = [
  "sent",
  "received"
]
export default {
  name: "RequestsList",
  components: {UserCard},
  data() {
    return {
      requestTypeEnum: requestTypeEnumNotReactive,
      requestType: requestTypeEnumNotReactive[0],
      items: [],
      totalPages: 0,
      currentPage: 0,
      totalRequests: 0,
    }
  },
  mounted() {
    this.getRequest()
  },
  watch: {
    requestType() {
      this.getRequest()
    },
    totalPages() {
      this.getRequest()
    },
    totalRequests() {
      this.getRequest()
    },
  },
  methods: {
    getRequest(pageNumber = this.currentPage, pageSize = 10) {
      axios.get(urlConstants.requestsURL, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        params: {
          type: this.requestType,
          pageSize: pageSize,
          pageNumber: pageNumber
        }
      }).then(resp => {
        this.items = resp.data.content
        this.totalRequests = resp.data.totalElements
        this.totalPages = Math.ceil(resp.data.totalElements / pageSize)
      }).catch(ex => {
        console.log(ex)
      })
    },
    answerRequest(userId, answer) {
      axios.put(urlConstants.requestsURL + "/" + userId, {},
          {
            "Access-Control-Allow-Origin": "http://localhost:8000/",
            withCredentials: true,
            'Access-Control-Allow-Credentials': true,
            params: {answer: answer}
          }).then(() => this.getRequest())
    },
    pageChanged(pageNumber) {
      this.currentPage = pageNumber - 1
      this.getRequest()
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