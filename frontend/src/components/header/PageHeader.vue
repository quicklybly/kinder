<template>
  <div class="header">
    <div class="left">
      <div v-if="store.userLoggedIn" class="user_info">
        <v-avatar class="user_avatar ">
          <v-img v-if="avatar != null" :src='avatarLink'></v-img>
          <v-img v-else src='@/assets/default-profile-pictures/2.jpg'></v-img>
        </v-avatar>
        <span v-if="store.userLoggedIn">{{ store.username }}</span>
      </div>
    </div>
    <div class="login-controls">
      <div>
        <template v-if="store.userLoggedIn">
          <v-btn @click="store.userLoggedIn = false">Log out</v-btn>
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
import {userStorage} from "@/dataObjects/UserStorage.js";

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

  },
  methods: {
    registrationSuccess(username, pass) {
      this.$refs.signUpComponent.$data.signUpActive = false
      this.$refs.signInComponent.$data.signInActive = true
      this.$refs.signInComponent.$data.username = username
      this.$refs.signInComponent.$data.password = pass
    },
  }
}

</script>

<style scoped>
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