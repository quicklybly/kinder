<template>
  <div class="profile-container" v-if="isLoaded && profile !== {}">
    <v-card class="profile-header">
      <router-link to="/">
        <v-icon class="home-icon">mdi-home</v-icon>
      </router-link>
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
          <v-btn v-if="store.id===profile.user.id" class="header-button" @click="editProfileDialog = true">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-dialog
              v-model="editProfileDialog"
              width="500px"
          >
            <v-card>
              <v-card-title>
                Edit profile
              </v-card-title>

              <v-card-text>
                <v-form v-model="editProfileForm">
                  <v-container>
                    <v-col>
                      <v-text-field
                          v-model="username"
                          :rules="usernameRules"
                          label="Username"
                          required
                      ></v-text-field>
                      <v-text-field
                          v-model="firstName"
                          label="First name"
                          :rules=nameRules
                          required
                      ></v-text-field>
                      <v-text-field
                          v-model="lastName"
                          label="Last name"
                          :rules=nameRules
                          required
                      ></v-text-field>
                      <v-file-input
                          v-model="editProfileAvatar"
                          accept="image/jpeg"
                          placeholder="Pick an avatar"
                          append-icon="mdi-camera"
                          prepend-icon=""
                          label="Avatar"
                      ></v-file-input>
                    </v-col>
                  </v-container>
                </v-form>
              </v-card-text>
              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="editSubmit">Edit</v-btn>
              </v-card-actions>
              <v-alert
                  color="red"
                  v-if="error"
              >
                {{ error }}
              </v-alert>
            </v-card>
          </v-dialog>

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
    <v-card class="profile-content">
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
      editProfileDialog: false,
      editProfileForm: null,
      username: "",
      usernameRules: [
        v => !!v || 'Username is required',
        v => v.length >= 4 || 'Username must be more than 3 characters',
      ],
      firstName: "",
      lastName: "",
      nameRules: [
        value => !!value || 'Required',
        value => value.length >= 2 || 'Min 2 characters',
      ],
      error: "",
      editProfileAvatar: "",
    }
  },
  async mounted() {
    await this.getProfile()
    this.getAllImages()
    this.username = userStorage.username
    this.firstName = userStorage.firstName
    this.lastName = userStorage.lastName
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
    },
    editSubmit() {
      const body = new FormData()
      body.append("user.username", this.username)
      body.append("user.firstName", this.firstName)
      body.append("user.lastName", this.lastName)
      if (this.editProfileAvatar !== "") {
        body.append("profilePicture", this.editProfileAvatar[0])
      }
      axios.put(urlConstants.profileBaseURL + "/" + this.profile.user.id, body, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
        "Content-Type": "multipart/form-data",
      }).then(() => {
        this.editProfileDialog = false
        this.store.username = this.username
        this.store.firstName = this.firstName
        this.store.lastName = this.lastName
        this.getProfile()
      }).catch((e) => {
        this.error = e.response.data?.message
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
  width: 60vw;
  margin: auto;
  align-content: center;
  align-items: center;
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
  font-size: large;
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

.home-icon {
  position: absolute;
  left: 10px;
  top: 10px;
  z-index: 2;
}
</style>