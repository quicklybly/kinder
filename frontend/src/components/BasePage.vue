<template>
  <div>
    <PageHeader :userId="userId" @idChanged="idChanged"></PageHeader>
    <PageContent>
    </PageContent>
  </div>
</template>

<script>
import PageHeader from "@/components/PageHeader";
import PageContent from "@/components/PageContent";

export default {
  name: "BasePage",
  components: {
    PageHeader,
    PageContent
  },
  data() {
    return {
      userId: null
    }
  },
  methods: {
    idChanged(id) {
      this.userId = id;
    }
  },
  watch: {
    userId() {
      console.log(this.userId)
    }
  }
}
</script>

<style scoped>

</style>