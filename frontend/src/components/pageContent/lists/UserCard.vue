<template>
  <v-card class="user-card" elevation="5">
    <router-link :to="{name: 'ProfileView', params: {id: user.id}}">
      <v-avatar size="70" class="ml-3">
        <v-img v-if="avatar != null" :src='avatarLink'></v-img>
        <v-img v-else src='@/assets/default-profile-pictures/2.jpg'></v-img>
      </v-avatar>
    </router-link>
    <v-divider vertical class="mx-3"></v-divider>
    <div>
      <span>
        {{ `${user.firstName} ${user.lastName}` }}
      </span>
    </div>
    <div class="d-flex flex-grow-1"></div>
    <div class="pr-4">
      <slot>
      </slot>
    </div>

  </v-card>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";

export default {
  name: "UserCard",
  props: {
    user: Object,
  },
  data() {
    return {
      avatar: null,
      avatarLink: null,
    }
  },
  mounted() {
    this.getAvatar()
  },
  methods: {
    async getAvatar() {
      await axios.get(urlConstants.profileBaseURL + "/" + this.user.id + "/avatar", {
            "Access-Control-All ow-Origin": "http://localhost:8000/",
            withCredentials: true,
            'Access-Control-Allow-Credentials': true
          }
      ).then((avatarDTO) => {
        this.avatarLink = null
        this.avatar = null
        if (avatarDTO === undefined) {
          throw Error
        }
        return this.getImage(avatarDTO.data.id)
      }).then((image) => {
        this.avatar = image
        this.avatarLink = window.URL.createObjectURL(image)
      }).catch((e) => {
        console.log(e)
      })
    },
    async getImage(id) {
      let tmpImage
      await axios.get(urlConstants.imageBaseURL + "/" + id, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        responseType: 'blob',
      }).then((resp) => {
        tmpImage = resp.data;
        return tmpImage
      }).catch((e) => {
        console.log(e)
      })
      return tmpImage
    }
  }
}
</script>

<style scoped>
.user-card {
  display: flex;
  flex-direction: row;
  height: 100px;
  margin: 15px 10px;
  align-items: center;
}

</style>