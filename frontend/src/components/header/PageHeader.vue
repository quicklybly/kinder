<template>
  <div class="header">
    <div class="left">
      <div v-if="store.userLoggedIn" class="user_info">
        <router-link :to="{name: 'ProfileView', params: {id: store.id}}">
          <v-avatar class="user_avatar">
            <v-img v-if="avatar != null" :src='avatarLink'></v-img>
            <v-img v-else src='@/assets/default-profile-pictures/2.jpg'></v-img>
          </v-avatar>
        </router-link>
        <span v-if="store.userLoggedIn">{{ store.username }}</span>
      </div>
    </div>
    <div class="login-controls">
      <div>
        <template v-if="store.userLoggedIn">
          <router-link to="/">
            <v-btn @click="store.userLoggedIn = false">Log out</v-btn>
          </router-link>
        </template>
        <template v-else>
          <SignIn ref="signInComponent"></SignIn>
          <SignUp ref="signUpComponent" @registrationSuccess="registrationSuccess"></SignUp>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
import SignIn from "@/components/header/SignIn.vue";
import SignUp from "@/components/header/SignUp.vue";
import {userStorage} from "@/store/UserStorage.js";
import axios from "axios";
import urlConstants from "@/urlConstants";
import {getImage} from "@/helpers/http";

export default {
  name: "PageHeader",
  components: {SignUp, SignIn},
  data() {
    return {
      store: userStorage,
      avatar: null,
      avatarLink: null,
    }
  },
  mounted() {
    this.getAvatar()
  },
  watch: {
    "store.id"() {
      if (this.store.id !== -1)
        this.getAvatar()
    },
  },
  methods: {
    registrationSuccess(username, pass) {
      this.$refs.signUpComponent.$data.signUpActive = false
      this.$refs.signInComponent.$data.signInActive = true
      this.$refs.signInComponent.$data.username = username
      this.$refs.signInComponent.$data.password = pass
    },
    async getAvatar() {
      await axios.get(urlConstants.profileBaseURL + "/" + this.store.id + "/avatar", {
            "Access-Control-All ow-Origin": "http://localhost:8000/",
            withCredentials: true,
            'Access-Control-Allow-Credentials': true
          }
      ).then((avatarDTO) => {
        if (!avatarDTO.data) {
          throw Error
        }
        this.avatarLink = null
        this.avatar = null
        return getImage(avatarDTO.data.id)
      }).then((image) => {
        this.avatar = image
        this.avatarLink = window.URL.createObjectURL(image)
      }).catch((e) => {
        console.log(e)
      })
    },
  }
}

</script>

<style scoped>
a {
  text-decoration: none;
  color: black;
}

.header {
  height: 10vh;
  width: 100vw;
  position: sticky;
  top: 0;
  display: flex;
  flex-direction: row;
  background-color: dimgray;
  justify-content: space-between;
}

.left {
  display: flex;
  flex-direction: row;
  padding-left: 40px;
}

.login-controls {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-right: 20px;
}

.user_info {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.user_avatar {
  min-height: 50px;
  min-width: 50px;
  border-radius: 50px;
  margin-right: 8px;
}
</style>