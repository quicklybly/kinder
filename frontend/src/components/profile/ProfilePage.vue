<template>
  <div class="profile-container" v-if="isLoaded && profile !== {}">
    <v-card class="profile-header">
      <div class="header-top-part">
      </div>
      <div class="header-card-container">
        <v-avatar size="150" class="avatar">
          <v-img v-if="avatar != null" :src='avatarLink'></v-img>
          <v-img v-else src='@/assets/default-profile-pictures/2.jpg'></v-img>
        </v-avatar>
        <span class="header-card-text">
          {{ profile.user.firstName }} {{ profile.user.lastName }}
        </span>
        <div class="header-grow-component"></div>
        <div class="header-buttons-container">
          <v-btn v-if="store.id===profile.user.id" class="header-button">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn class="header-button" @click="friendsDialog = true">
            <v-icon>mdi-account-multiple</v-icon>
          </v-btn>
          <v-dialog
              v-model="friendsDialog"
          >
            <v-card>
              <UserCard v-for="user in profile.friends" :key="user.id" :user="user">
              </UserCard>
            </v-card>
          </v-dialog>
        </div>
      </div>
    </v-card>
    <v-card class="profile-content ma-2">
      <v-carousel v-if="imagesLoaded">
        <v-carousel-item v-for="image in imagesLinks" :key="image"
                         :src='image'
                         cover
        >
        </v-carousel-item>
      </v-carousel>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";
import {userStorage} from "@/dataObjects/UserStorage";
import UserCard from "@/components/pageContent/lists/UserCard.vue";

export default {
  name: "ProfilePage",
  components: {UserCard},
  props: {
    id: -1
  },
  data() {
    return {
      profile: Object,
      avatar: "",
      avatarLink: "",
      isLoaded: false,
      store: userStorage,
      friendsDialog: false,
      imagesLinks: [],
      imagesLoaded: false,
    }
  },
  async mounted() {
    await this.getProfile()
    this.getAllImages()
  },
  methods: {
    async getProfile() {
      await axios.get(urlConstants.profileBaseURL + "/" + this.id, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true
      }).then((resp) => {
        this.profile = resp.data
        return this.profile
      }).then((profile) => {
        if (profile.profilePicture == null) {
          this.avatar = null
          this.avatarLink = null
          this.isLoaded = true
          throw Error
        }
        return this.getImage(profile.profilePicture.id)
      }).then((avatar) => {
        this.avatar = avatar
        this.avatarLink = window.URL.createObjectURL(avatar)
        this.isLoaded = true
      }).catch((e) => {
        console.log(e)
      })
      // हमें भगवान पर भरोसा है
    },
    async getImage(id) {
      let tmpImage
      await axios.get(urlConstants.imageBaseURL + "/" + id, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        responseType: 'blob',
      }).then((resp) => {
        tmpImage = resp.data;
        return tmpImage
      }).catch((e) => {
        console.log(e)
      })
      return tmpImage
    },
    getAllImages() {
      this.imagesLinks = []
      this.imagesLoaded = false
      this.profile.images.forEach(async (image) => {
            this.imagesLinks.push(window.URL.createObjectURL(await this.getImage(image.id)))
          }
      )
      this.$nextTick(() => {
        this.imagesLoaded = true
      })
    }
  }
}
</script>

<style scoped>
.profile-container {
  display: flex;
  flex-direction: column;
}

.profile-header {
  display: flex;
  flex-direction: column;
  background-color: #a8a8a8;
  margin: 8px;
}

.profile-content {
  display: flex;
  height: max-content;
}

.avatar {
  z-index: 1;
  margin-top: -75px;
  margin-left: 12px;
}

.header-card-container {
  height: 90px;
  display: flex;
  flex-direction: row;
  justify-content: left;
}

.header-card-text {
  margin-left: 16px;
  margin-top: 8px;
}

.header-top-part {
  height: 90px;
  background-color: aquamarine;
}

.header-grow-component {
  display: flex;
  flex-direction: row;
  flex-grow: 1;
}

.header-button {
  margin-right: 8px;
}

.header-buttons-container {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>