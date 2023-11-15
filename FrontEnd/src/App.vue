<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import TheFooter from "@/components/layout/TheFooter.vue";
</script>

<template>
  <div>
    <the-heading-navbar></the-heading-navbar>
    <router-view></router-view>
    <the-footer></the-footer>
  </div>
</template>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

html,
body {
  height: 100%;
  background: #ffffff;
}

#container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 100%;
}

#loginBox {
  width: 300px;
  text-align: center;
  background-color: #ffffff;
}

.input-form-box {
  border: 0px solid #ff0000;
  display: flex;
  margin-bottom: 5px;
}

.input-form-box > span {
  display: block;
  text-align: left;
  padding-top: 5px;
  margin-right: 10px;
  min-width: 65px;
}

.button-login-box {
  margin: 10px 0;
}

#loginBoxTitle {
  color: #000000;
  font-weight: bold;
  font-size: 32px;
  text-transform: uppercase;
  padding: 5px;
  margin-bottom: 20px;
  background: linear-gradient(to right, #270a09, #8ca6ce);
  -webkit-text-fill-color: transparent;
}

#inputBox {
  margin: 10px;
}

#inputBox button {
  padding: 3px 5px;
}
</style>
