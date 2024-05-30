import { createApp } from 'vue'
import './style.css'
import App from './App.vue';
import store from './store.js';
import router from './router.js';

const user = JSON.parse(sessionStorage.getItem("user"));

if (user) {
  store.state.user = user;
}

const app = createApp(App);
app.use(router);
app.use(store);
app.mount('#app');
