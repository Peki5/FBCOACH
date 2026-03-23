<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Uredi Utakmicu</span>
        <form @submit.prevent="updateMatch" class="space-y-5">
          <div>
            <label class="label">Datum</label>
            <input v-model="match.date" type="date" required class="input-field" />
          </div>
          <div>
            <label class="label">Protivnik</label>
            <input v-model="match.opponent" type="text" required class="input-field" />
          </div>
          <div>
            <label class="label">Lokacija</label>
            <select v-model="match.location" required class="input-field">
              <option value="HOME">Doma</option>
              <option value="AWAY">Gost</option>
            </select>
          </div>
          <div>
            <label class="label">Rezultat</label>
            <input v-model="match.result" type="text" required class="input-field" />
          </div>
          <button class="btn-primary w-full py-3">
            Ažuriraj
          </button>
        </form>
      </div>
    </div>
  </template>

  <script>
  import RequestHandler from "../RequestHandler.js";
  import { SPRING_URL } from "../constants.js";

  export default {
    data() {
      return {
        match: {
          idMatch: null,
          date: "",
          opponent: "",
          location: "",
          result: "",
          teamId: null,
        },
      };
    },
    async mounted() {
      const idMatch = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/matches/`).concat(idMatch)
        );
        response.date = new Date(response.date).toISOString().split('T')[0];
        this.match = response;
      } catch {
      }
    },
    methods: {
      async updateMatch() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/matches/edit/${this.match.idMatch}`),
            this.match
          );
          this.$router.push({ name: 'ListMatches', params: { teamId: this.match.teamId } });
        } catch {
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
