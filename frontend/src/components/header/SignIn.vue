<template>
  <v-dialog
      v-model="signInActive"
      width="500"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn class="mr-2"
             v-bind="attrs"
             v-on="on" @click="signInActive = true">Sign in
      </v-btn>
    </template>
    <v-card>
      <v-card-title>
        Sign in
      </v-card-title>

      <v-card-text>
        <v-form v-model="loginForm">
          <v-container>
            <v-col>
              <v-text-field
                  v-model="username"
                  :rules="usernameRules"
                  label="Username"
                  required
              ></v-text-field>
              <v-text-field
                  v-model="password"
                  :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[rules.required, rules.min]"
                  :type="showPassword ? 'text' : 'password'"
                  name="input-10-1"
                  label="Password"
                  hint="At least 8 characters"
                  counter
                  @click:append="showPassword = !showPassword"
              ></v-text-field>
            </v-col>
          </v-container>
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn @click="submit">Sign in</v-btn>
      </v-card-actions>
      <v-alert
          color="red"
          v-if="error"
      >
        {{ error }}
      </v-alert>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";
import md5 from "js-md5";
import {userStorage} from "@/dataObjects/UserStorage.js";

export default {
  name: "SignIn",
  data() {
    return {
      signInActive: false,
      loginForm: null,
      showPassword: false,
      password: "",
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 8 || 'Min 8 characters',
      },
      username: "",
      usernameRules: [
        v => !!v || 'Username is required',
        v => v.length >= 4 || 'Username must be more than 3 characters',
      ],
      error: "",
      store: userStorage
    }
  },
  methods: {
    submit() {
      axios.post(urlConstants.signInURL, {
        username: this.username,
        password: md5(this.password)
      }, {
        "Access-Control-Allow-Origin": "http://localhost:8000/",
        withCredentials: true,
        'Access-Control-Allow-Credentials': true,
      }).then((resp) => {
        this.username = ""
        this.password = ""
        this.error = ""
        this.signInActive = false
        this.store.userMapper(resp.data)
        this.store.userLoggedIn = true
      }).catch(error => {
        this.error = error.response.data?.message
      })
    }
  }
}
</script>

<style scoped>

</style>