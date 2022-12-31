<template>
  <v-app>
    <v-main>
      <BasePage></BasePage>
    </v-main>
  </v-app>
</template>

<script>
import BasePage from "@/components/BasePage";

export default {
  name: 'App',

  components: {
    BasePage,
  },

  data: () => ({
    //
  }),
}
</script>
