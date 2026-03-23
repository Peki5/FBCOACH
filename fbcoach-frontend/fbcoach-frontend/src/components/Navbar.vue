<template>
  <header class="sticky top-0 z-50 bg-fbcoach-surface/95 backdrop-blur-sm border-b border-fbcoach-border/50 shadow-lg">
    <nav class="container flex flex-col sm:flex-row items-center gap-4 text-fbcoach-text py-4">
      <RouterLink :to="{ name: 'home' }">
        <div class="flex items-center gap-3">
          <div class="w-8 h-8 bg-fbcoach-primary rounded-lg flex items-center justify-center">
            <span class="text-white font-bold text-sm">FB</span>
          </div>
          <p class="text-xl font-semibold tracking-wide">FBCOACH</p>
        </div>
      </RouterLink>

      <div v-if="$store.state.user" class="flex gap-8 flex-1 justify-end items-center">
        <RouterLink :to="{ name: 'home' }">
          <p class="text-fbcoach-text-muted hover:text-fbcoach-primary font-medium transition-colors">Timovi</p>
        </RouterLink>
        <RouterLink :to="{ name: 'Reports' }">
          <p class="text-fbcoach-text-muted hover:text-fbcoach-primary font-medium transition-colors">Izvještaji</p>
        </RouterLink>
        <div class="flex items-center gap-4 pl-4 border-l border-fbcoach-border">
          <p class="text-sm text-fbcoach-text-muted">{{ $store.state.user.ime }} {{ $store.state.user.prezime }}</p>
          <button class="text-sm text-fbcoach-text-muted hover:text-fbcoach-danger font-medium transition-colors" @click="logout">Odjavi se</button>
        </div>
      </div>

      <div v-else class="flex gap-6 flex-1 justify-end items-center">
        <RouterLink :to="{ name: 'Login' }">
          <p class="text-fbcoach-text-muted hover:text-fbcoach-primary font-medium transition-colors">Prijava</p>
        </RouterLink>
        <RouterLink :to="{ name: 'Register' }">
          <button class="btn-primary">Registracija</button>
        </RouterLink>
      </div>
    </nav>
  </header>
</template>

<script>
import { RouterLink } from 'vue-router';

export default {
  methods: {
    async logout() {
      if (confirm("Odjava iz sustava?")) {
        this.$store.state.user = undefined
        sessionStorage.clear()
        this.$router.push("/login")
      }
    }
  }
}
</script>
