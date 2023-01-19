<template>
  <v-card class="user-card" elevation="5">
    <v-avatar size="70" class="ml-3">
      <v-img src='@/assets/default-profile-pictures/2.jpg'></v-img>
    </v-avatar>
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
export default {
  name: "UserCard",
  props: {
    user: Object
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