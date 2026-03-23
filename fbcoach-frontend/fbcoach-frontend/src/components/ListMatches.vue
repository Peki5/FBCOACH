<template>
    <div class="container mt-6">
      <div v-if="isDodajVisible">
        <div class="flex items-center justify-center px-4">
          <div class="w-full max-w-md card">
            <button @click="toggleDodaj" class="btn-secondary mb-4">
              Povratak
            </button>
            <span class="form-title">Dodaj novu utakmicu</span>
            <form @submit.prevent="dodajNovuUtakmicu" class="space-y-5">
              <div>
                <label class="label">Datum</label>
                <input v-model="novaUtakmica.date" type="date" required class="input-field" />
              </div>
              <div>
                <label class="label">Protivnik</label>
                <input v-model="novaUtakmica.opponent" type="text" required class="input-field" />
              </div>
              <div>
                <label class="label">Lokacija</label>
                <select v-model="novaUtakmica.location" required class="input-field">
                  <option value="HOME">Doma</option>
                  <option value="AWAY">Gost</option>
                </select>
              </div>
              <div>
                <label class="label">Rezultat</label>
                <input v-model="novaUtakmica.result" type="text" required class="input-field" />
              </div>
              <button class="btn-primary w-full py-3">
                Dodaj
              </button>
            </form>
          </div>
        </div>
      </div>
      <div v-else>
        <div class="card">
          <div class="flex items-center justify-between mb-6">
            <h1 class="page-title">Utakmice</h1>
            <button @click="toggleDodaj" class="btn-primary">
              + Dodaj utakmicu
            </button>
          </div>
          <div v-if="matches.length > 0">
            <div class="space-y-3">
              <div v-for="match in matches" :key="match.idMatch" class="card-item">
                <div class="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-3">
                  <div class="space-y-1">
                    <p class="text-fbcoach-text-muted text-sm">{{ formatDate(match.date) }}</p>
                    <p class="text-fbcoach-text font-semibold">vs {{ match.opponent }}</p>
                    <div class="flex gap-4">
                      <p class="text-fbcoach-text-muted text-sm">{{ match.location === 'HOME' ? 'Doma' : 'Gost' }}</p>
                      <p class="text-fbcoach-primary font-semibold text-sm">{{ match.result }}</p>
                    </div>
                  </div>
                  <div class="flex gap-2">
                    <button @click="navigateToDetails(match.idMatch)" class="btn-info text-xs">Detalji</button>
                    <button @click="navigateToEdit(match.idMatch)" class="btn-warning text-xs">Uredi</button>
                    <button @click="deleteMatch(match.idMatch)" class="btn-danger text-xs">Obriši</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5 text-center py-8">
            <p class="text-fbcoach-text-muted">Nema utakmica za prikaz.</p>
          </div>
        </div>
      </div>
    </div>
  </template>

  <script>
  import RequestHandler from "./../RequestHandler.js";
  import { SPRING_URL } from "./../constants.js";

  export default {
    data() {
      return {
        matches: [],
        isDodajVisible: false,
        novaUtakmica: {
          date: "",
          opponent: "",
          location: "",
          result: "",
          teamId: this.$route.params.teamId,
        },
      };
    },
    async mounted() {
      const teamId = this.$route.params.teamId;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/matches?teamId=`).concat(teamId)
        );
        console.log("Fetched matches:", response);
        this.matches = response;
      } catch (error) {
        console.error("Error fetching matches:", error);
      }
    },
    methods: {
      formatDate(date) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(date).toLocaleDateString(undefined, options);
      },
      navigateToDetails(id) {
        this.$router.push({ name: 'MatchDetails', params: { id: id } });
      },
      navigateToEdit(id) {
        this.$router.push({ name: 'EditMatch', params: { id: id } });
      },
      async deleteMatch(id) {
        const confirmed = confirm("Jeste li sigurni da želite obrisati ovu utakmicu?");
        if (confirmed) {
          try {
            await RequestHandler.deleteRequest(SPRING_URL.concat(`/matches/delete/`).concat(id));
            this.matches = this.matches.filter(match => match.idMatch !== id);
          } catch (error) {
            console.error("Error deleting match:", error);
          }
        }
      },
      async dodajNovuUtakmicu() {
        try {
          await RequestHandler.postRequest(SPRING_URL.concat("/matches/add"), this.novaUtakmica);
          this.$router.go();
        } catch (error) {
          console.error("Error adding new match:", error);
        }
      },
      toggleDodaj() {
        this.isDodajVisible = !this.isDodajVisible;
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
