<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="goBack"
            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Uredi Utakmicu</span>
          <form @submit.prevent="updateMatch" class="mb-4">
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Datum</label>
              <input v-model="match.date" type="date" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Protivnik</label>
              <input v-model="match.opponent" type="text" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Lokacija</label>
              <select v-model="match.location" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                <option value="HOME">Doma</option>
                <option value="AWAY">Gost</option>
              </select>
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Rezultat</label>
              <input v-model="match.result" type="text" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <button
              class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
              Ažuriraj
            </button>
          </form>
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
        console.log("Fetched match for edit:", response);  // Log the response
        this.match = response;
      } catch (error) {
        console.error("Error fetching match for edit:", error);
      }
    },
    methods: {
      async updateMatch() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/matches/edit/${this.match.idMatch}`),
            this.match
          );
          this.$router.push({ name: 'ListMatches', params: { teamId: this.match.teamId } }); // Redirect to the list of matches
        } catch (error) {
          console.error("Error updating match:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  