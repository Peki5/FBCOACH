<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="goBack"
            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Uredi igrača</span>
          <form @submit.prevent="updatePlayer" class="mb-4">
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Ime</label>
              <input v-model="player.firstname" type="text" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Prezime</label>
              <input v-model="player.lastName" type="text" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Datum rođenja</label>
              <input v-model="player.dateOfBirth" type="date" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Pozicija</label>
              <select v-model="player.position" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                <option v-for="pos in positions" :key="pos" :value="pos">{{ pos }}</option>
              </select>
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Visina (cm)</label>
              <input v-model="player.height" type="number" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Težina (kg)</label>
              <input v-model="player.weight" type="number" required
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
        player: {
          idPlayer: null,
          firstname: "",
          lastName: "",
          dateOfBirth: "",
          position: "",
          height: 0,
          weight: 0,
        },
        positions: ["GOALKEEPER", "DEFENDER", "MIDFIELDER", "ATTACKER"],
      };
    },
    async mounted() {
      const idPlayer = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/players/${idPlayer}`)
        );
        response.dateOfBirth = new Date(response.dateOfBirth).toISOString().split('T')[0];
        console.log("Fetched player for edit:", response);  // Log the response
        this.player = response;
      } catch (error) {
        console.error("Error fetching player for edit:", error);
      }
    },
    methods: {
      async updatePlayer() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/players/edit/${this.player.idPlayer}`),
            this.player
          );
          this.$router.push({ name: 'ListPlayers', params: { teamId: this.player.teamIds[0] } }); // Redirect to the list of players
        } catch (error) {
          console.error("Error updating player:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  