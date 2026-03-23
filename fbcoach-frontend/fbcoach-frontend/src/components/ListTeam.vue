<template>
  <main v-if="$store.state.user">
    <div v-if="isDodajVisible">
      <div class="min-h-[80vh] flex items-center justify-center px-4">
        <div class="w-full max-w-md card">
          <button @click="toggleDodaj" class="btn-secondary mb-4">
            Povratak
          </button>
          <span class="form-title">Dodaj novi tim</span>
          <form @submit.prevent="dodajNoviTim" class="space-y-5">
            <div>
              <label class="label">Ime tima</label>
              <input v-model="noviTim.teamName" type="text" required class="input-field" />
            </div>
            <div>
              <label class="label">Sezona</label>
              <input v-model="noviTim.season" type="text" required class="input-field" />
            </div>
            <button class="btn-primary w-full py-3">
              Dodaj
            </button>
          </form>
        </div>
      </div>
    </div>

    <div v-else class="container mt-6">
      <div class="card">
        <div class="flex items-center justify-between mb-6">
          <h1 class="page-title">Timovi</h1>
          <button @click="toggleDodaj" class="btn-primary">
            + Dodaj tim
          </button>
        </div>

        <div v-if="timovi.length > 0">
          <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
            <div v-for="tim in timovi" :key="tim.idTeam">
              <div class="card-item hover:cursor-pointer">
                <div class="mb-3">
                  <div class="font-semibold text-lg text-fbcoach-text mb-1">{{ tim.teamName }}</div>
                  <p class="text-fbcoach-text-muted text-sm">
                    Sezona: {{ tim.season }}
                  </p>
                </div>
                <div class="flex flex-wrap gap-2 mt-4">
                  <button @click="navigateToPlayers(tim.idTeam)" class="btn-info text-xs">Squad</button>
                  <button @click="navigateToEditPage(tim.idTeam)" class="btn-warning text-xs">Edit</button>
                  <button @click="obrisiTim(tim.idTeam)" class="btn-danger text-xs">Delete</button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="mt-5 text-center py-8">
          <p class="text-fbcoach-text-muted">Nema timova za prikaz</p>
        </div>
      </div>
    </div>

  </main>

  <main v-else class="container">
    <div class="pt-16 mb-8 text-center">
      <h1 class="text-2xl font-medium text-fbcoach-text-muted">
        Molimo prijavite se u sustav!
      </h1>
    </div>
  </main>
</template>

<script>
import RequestHandler from "./../RequestHandler.js";
import { SPRING_URL } from "../constants";

export default {
  data() {
    return {
      timovi: [],
      isDodajVisible: false,
      noviTim: {
        teamName: "",
        season: "",
      },
    };
  },
  async mounted() {
    if (this.$store.state.user) {
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat("/team")
        );

        if (Array.isArray(response)) {
          this.timovi = response;
          this.timovi.sort((a, b) => (a.idTeam > b.idTeam ? 1 : -1));
        }
      } catch {
      }
    }
  },
  methods: {
    toggleDodaj() {
      this.isDodajVisible = !this.isDodajVisible;
    },
    async dodajNoviTim() {
      this.noviTim.coach = { id: this.$store.state.user.id };
      await RequestHandler.postRequest(
        SPRING_URL.concat("/team/add"),
        this.noviTim
      );
      this.$router.go();
    },
    async obrisiTim(id) {
      const confirmed = confirm("Jeste li sigurni?");

      if (confirmed) {
        try {
          await RequestHandler.deleteRequest(SPRING_URL.concat(`/team/delete/${id}`));
          this.timovi = this.timovi.filter(team => team.idTeam !== id);
        } catch {
        }
      }
    },
    navigateToEditPage(id) {
      this.$router.push({ name: 'EditTeam', params: { id: id } });
    },
    navigateToPlayers(id) {
      this.$router.push({ name: 'ListPlayers', params: { teamId: id } });
    }
  },
};
</script>
