<template>
  <div class="panel-container">
    <v-btn class="custom-button" @click="$emit('openPanel', 1)">
      My friends
    </v-btn>
    <v-btn class="custom-button" @click="$emit('openPanel', 2)">
      Friends requests
    </v-btn>
    <v-btn class="custom-button" @click="$emit('openPanel', 3)">
      Search friends
    </v-btn>
  </div>
</template>

<script>

export default {
  name: "LeftControlPanel",
  data() {
    return {}
  }
}
</script>

<style scoped>
.panel-container {
  display: flex;
  justify-content: flex-start;
  background-color: #a8a8a8;
  flex-direction: column;
  align-items: center;
}

.custom-button {
  height: 50px;
  width: 90%;
  margin: 5px;

}
</style>