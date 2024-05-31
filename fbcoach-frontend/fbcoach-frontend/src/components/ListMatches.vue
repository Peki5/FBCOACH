<template>
    <div class="container antialiased text-black">
      <div v-if="isDodajVisible">
        <!-- Add Match Section -->
        <div class="antialiased text-black">
          <div class="flex items-center w-full">
            <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
              <button @click="toggleDodaj"
                class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                Povratak
              </button>
              <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj novu utakmicu</span>
              <form @submit.prevent="dodajNovuUtakmicu" class="mb-4">
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Datum</label>
                  <input v-model="novaUtakmica.date" type="date" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Protivnik</label>
                  <input v-model="novaUtakmica.opponent" type="text" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Lokacija</label>
                  <select v-model="novaUtakmica.location" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                    <option value="HOME">Doma</option>
                    <option value="AWAY">Gost</option>
                  </select>
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Rezultat</label>
                  <input v-model="novaUtakmica.result" type="text" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                </div>
                <button
                  class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                  Dodaj
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="toggleDodaj"
            class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
            Dodaj novu utakmicu
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Utakmice</span>
          <div v-if="matches.length > 0">
            <div v-for="match in matches" :key="match.idMatch" class="mb-4 p-4 border rounded bg-slate-200">
              <p class="text-gray-700">Datum: {{ formatDate(match.date) }}</p>
              <p class="text-gray-700">Protivnik: {{ match.opponent }}</p>
              <p class="text-gray-700">Lokacija: {{ match.location }}</p>
              <p class="text-gray-700">Rezultat: {{ match.result }}</p>
              <div class="flex space-x-2 mt-4">
                <button @click="navigateToDetails(match.idMatch)" class="bg-blue-500 hover:bg-blue-400 text-white text-sm font-semibold px-4 py-2 rounded">Detalji</button>
                <button @click="navigateToEdit(match.idMatch)" class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Uredi</button>
                <button @click="deleteMatch(match.idMatch)" class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">Obriši</button>
              </div>
            </div>
          </div>
          <div v-else class="mt-5">
            <p>Nema utakmica za prikaz.</p>
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
        console.log("Fetched matches:", response);  // Log the response
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
  