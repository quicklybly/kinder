<template>
  <div class="page-content-container">
    <LeftControlPanel class="left-control-panel" @openPanel="switchPanel">

    </LeftControlPanel>
    <div class="main-content">
      <friend-list v-if="currentPanel===1"></friend-list>
      <requests-list v-if="currentPanel===2"></requests-list>
    </div>
  </div>

</template>

<script>
import LeftControlPanel from "@/components/LeftControlPanel.vue";
import FriendList from "@/components/FriendList.vue";
import RequestsList from "@/components/RequestsList.vue";

export default {
  name: "PageContent",
  components: {RequestsList, FriendList, LeftControlPanel},
  data() {
    return {
      currentPanel: 1,
    }
  },
  methods: {
    switchPanel(panelNumber) {
      this.currentPanel = panelNumber
    }
  }
}
</script>

<style scoped>
.page-content-container {
  width: 100vw;
  height: 90vh;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.left-control-panel {

}

.main-content {
  display: flex;
  flex-grow: 1;
  width: max-content;
}
</style>