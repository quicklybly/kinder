<template>
  <PageContent v-if="store.userLoggedIn">
  </PageContent>
  <PagePlaceHolder v-else></PagePlaceHolder>
</template>

<script>
import {userStorage} from "@/dataObjects/UserStorage";
import PageContent from "@/components/pageContent/PageContent.vue";
import PagePlaceHolder from "@/components/pageContent/PagePlaceHolder.vue";

export default {
  name: "MainView",
  components: {PagePlaceHolder, PageContent},
  data() {
    return {
      store: userStorage
    }
  }
}
</script>

<style scoped>

</style>