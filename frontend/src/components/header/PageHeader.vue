<template>
  <div class="header">
    <div class="left">
      <div v-if="store.userLoggedIn" class="user_info">
        <!--TODO fix fucking image-->
        <v-img min-height="50" min-width="50" class="mr-2" style="border-radius: 50px"
               src='@/assets/default-profile-pictures/1.jpg'>
        </v-img>
        <span v-if="store.userLoggedIn">{{ store.username }}</span>
        <!--TODO edit profile button-->
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
      store: userStorage
    }
  },
  methods: {
    registrationSuccess(username, pass) {
      this.$refs.signUpComponent.$data.signUpActive = false
      this.$refs.signInComponent.$data.signInActive = true
      this.$refs.signInComponent.$data.username = username
      this.$refs.signInComponent.$data.password = pass
    }
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
</style>