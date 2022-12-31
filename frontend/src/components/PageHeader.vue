<template>
  <div class="header">
    <div class="left">
      <div v-if="userId" class="user_info">
        <!--TODO fix fucking image-->
        <v-img min-height="50" min-width="50" class="mr-2" style="border-radius: 50px" src='@/assets/default-profile-pictures/1.jpg'>
        </v-img>
        <span>User name</span>
        <!--TODO edit profile button-->
      </div>
    </div>
    <div class="login-controls">
      <div>
        <template v-if="userId">
          <v-btn @click="idChanged(null)">Log out</v-btn>
        </template>
        <template v-else>
          <SignIn @idChanged="idChanged"></SignIn>
          <SignUp @idChanged="idChanged"></SignUp>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
import SignIn from "@/components/SignIn";
import SignUp from "@/components/SignUp";

export default {
  name: "PageHeader",
  components: {SignUp, SignIn},
  props: {
    userId: Number,
  },
  data() {
    return {}
  },
  methods: {
    idChanged(id) {
      this.$emit("idChanged", id)
    }
  }
}

</script>

<style scoped>
.header {
  height: 80px;
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