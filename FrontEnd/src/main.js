// import './assets/main.css'
import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

import App from "./App.vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

import "@/assets/css/index.css";
import "@/assets/css/common.css";

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);

app.use(pinia);
app.use(router);
app.use(Antd);

app.mount("#app");
