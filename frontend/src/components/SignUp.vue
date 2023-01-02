<template>
  <v-dialog
      v-model="signUpActive"
      width="500"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn class="mr-2"
             v-bind="attrs"
             v-on="on" @click="signUpActive = true">Sign up
      </v-btn>
    </template>
    <v-card>
      <v-card-title>
        Sign up
      </v-card-title>

      <v-card-text>
        <v-form v-model="registrationForm">
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
        <v-btn @click="submit">Sign up</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";
import urlConstants from "@/urlConstants";
import md5 from "js-md5";

export default {
  name: "SignUp",
  data() {
    return {
      signUpActive: false,
      registrationForm: null,
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
      firstName: "",
      lastName: "",
      nameRules: [
        value => !!value || 'Required',
        value => value.length >= 2 || 'Min 2 characters',
      ],
    }
  },
  methods: {
    submit() {
      this.signUpActive = false
      axios.post(urlConstants.signUpURL, {
        username: this.username,
        password: md5(this.password),
        firstName: this.firstName,
        lastName: this.lastName,
      }).then(resp => this.$emit("idChanged", resp.id)
      ).catch(error => console.log(error))
    },
  },
}
</script>

<style scoped>

</style>