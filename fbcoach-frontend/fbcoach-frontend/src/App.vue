<template>
  <div class="flex flex-col min-h-screen font-Roboto bg-fbcoach-secondary">
    <Navbar />
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'

export default {
  components: {
    Navbar
  },
  created() {
    if (sessionStorage.getItem("user")) {
      this.$store.state.user = JSON.parse(sessionStorage.getItem("user"))
    }
  }
}
</script>
