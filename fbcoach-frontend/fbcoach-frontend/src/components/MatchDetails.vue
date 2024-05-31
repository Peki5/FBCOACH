<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-4xl md:mx-auto">
          <button @click="goBack"
            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Detalji Utakmice</span>
          <div class="mb-4">
            <p class="text-gray-700">Datum: {{ match.date }}</p>
            <p class="text-gray-700">Protivnik: {{ match.opponent }}</p>
            <p class="text-gray-700">Lokacija: {{ match.location }}</p>
            <p class="text-gray-700">Rezultat: {{ match.result }}</p>
          </div>
          <div class="mb-4">
            <button @click="toggleTaktika"
              class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
              Dodaj Taktiku
            </button>
            <button @click="toggleStatistika"
              class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded ml-2">
              Dodaj Statistiku
            </button>
          </div>
          <div v-if="isTaktikaVisible" class="mb-4">
            <form @submit.prevent="dodajTaktiku" class="mb-4">
              <div class="mb-4 md:w-full">
                <label class="block text-xs mb-1">Taktika</label>
                <select v-model="novaTaktika.tacticsId" required
                  class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                  <option v-for="tactic in tactics" :key="tactic.idTactics" :value="tactic.idTactics">{{ tactic.name }}</option>
                </select>
              </div>
              <div class="mb-4 md:w-full">
                <label class="block text-xs mb-1">Opis</label>
                <input v-model="novaTaktika.description" type="text" required
                  class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
              </div>
              <button
                class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                Dodaj Taktiku
              </button>
            </form>
          </div>
          <div v-if="isStatistikaVisible" class="mb-4">
            <form @submit.prevent="dodajStatistiku" class="mb-4">
              <div class="mb-4 md:w-full">
                <label class="block text-xs mb-1">Igrač</label>
                <select v-model="novaStatistika.playerId" required
                  class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                  <option v-for="player in players" :key="player.idPlayer" :value="player.idPlayer">{{ player.firstname }} {{ player.lastName }}</option>
                </select>
              </div>
              <div class="mb-4 md:w-full">
                <label class="block text-xs mb-1">Golovi</label>
                <input v-model="novaStatistika.goals" type="number" min="0" required
                  class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
              </div>
              <div class="mb-4 md:w-full">
                <label class="block text-xs mb-1">Asistencije</label>
                <input v-model="novaStatistika.assists" type="number" min="0" required
                  class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
              </div>
              <button
                class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                Dodaj Statistiku
              </button>
            </form>
          </div>
          <div class="mb-4">
            <h2 class="text-xl font-bold mb-2">Taktike</h2>
            <div v-if="tacticsApplications.length > 0">
              <div v-for="tacticsApplication in tacticsApplications" :key="tacticsApplication.idTacticsApplication" class="mb-4 p-4 border rounded bg-slate-200">
                <p class="text-gray-700">Opis: {{ tacticsApplication.description }}</p>
                <p class="text-gray-700">Taktika: {{ getTacticName(tacticsApplication.tacticsId) }}</p>
              </div>
            </div>
            <div v-else>
              <p>Nema taktičkih aplikacija za prikaz.</p>
            </div>
          </div>
          <div class="mb-4">
            <h2 class="text-xl font-bold mb-2">Statistika</h2>
            <div v-if="playerMatchStats.length > 0">
              <div class="mb-4">
                <h3 class="text-lg font-bold mb-2">Strijelci</h3>
                <ul class="list-disc list-inside">
                    <li v-for="stat in goalscorers" :key="stat.idPlayerMatchStats">
                  {{ getPlayerName(stat.playerId) }} - Golovi: {{ stat.goals }}
                </li>
                </ul>
              </div>
              <div>
                <h3 class="text-lg font-bold mb-2">Asistenti</h3>
                <ul class="list-disc list-inside">
                    <li v-for="stat in assistants" :key="stat.idPlayerMatchStats">
                    {{ getPlayerName(stat.playerId) }} - Asistencije: {{ stat.assists }}
                  </li>
                </ul>
              </div>
            </div>
            <div v-else>
              <p>Nema statistike za prikaz.</p>
            </div>
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
        match: {
          idMatch: null,
          date: "",
          opponent: "",
          location: "",
          result: "",
          teamId: null,
        },
        tacticsApplications: [],
        playerMatchStats: [],
        tactics: [],
        players: [],
        isTaktikaVisible: false,
        isStatistikaVisible: false,
        novaTaktika: {
          description: "",
          tacticsId: null,
          matchId: this.$route.params.id,
        },
        novaStatistika: {
          playerId: null,
          goals: 0,
          assists: 0,
          matchId: this.$route.params.id,
        }
      };
    },
    async mounted() {
      const idMatch = this.$route.params.id;
      try {
        const matchResponse = await RequestHandler.getRequest(
          SPRING_URL.concat(`/matches/`).concat(idMatch)
        );
        matchResponse.date = new Date(matchResponse.date).toISOString().split('T')[0];
        console.log("Fetched match details:", matchResponse);  // Log the response
        this.match = matchResponse;
  
        const tacticsResponse = await RequestHandler.getRequest(
          SPRING_URL.concat(`/tactics?teamId=`).concat(this.match.teamId)
        );
        console.log("Fetched tactics for team:", tacticsResponse);  // Log the response
        this.tactics = tacticsResponse;
  
        const playersResponse = await RequestHandler.getRequest(
          SPRING_URL.concat(`/players?teamId=`).concat(this.match.teamId)
        );
        console.log("Fetched players for team:", playersResponse);  // Log the response
        this.players = playersResponse;
  
        const tacticsApplicationsResponse = await RequestHandler.getRequest(
          SPRING_URL.concat(`/tacticsapplication/match/`).concat(idMatch)
        );
        console.log("Fetched tactics applications:", tacticsApplicationsResponse);  // Log the response
        this.tacticsApplications = tacticsApplicationsResponse;
  
        const playerMatchStatsResponse = await RequestHandler.getRequest(
          SPRING_URL.concat(`/playermatchstats/match/`).concat(idMatch)
        );
        console.log("Fetched player match stats:", playerMatchStatsResponse);  // Log the response
        this.playerMatchStats = playerMatchStatsResponse;
      } catch (error) {
        console.error("Error fetching match details:", error);
      }
    },
    methods: {
      async dodajTaktiku() {
        try {
          const response = await RequestHandler.postRequest(
            SPRING_URL.concat("/tacticsapplication/add"),
            this.novaTaktika
          );
          console.log("Added new tactics application:", response);  // Log the response
          this.tacticsApplications.push(response);  // Add the new tactics application to the list
          this.toggleTaktika();  // Hide the add tactics form
        } catch (error) {
          console.error("Error adding new tactics application:", error);
        }
      },
      async dodajStatistiku() {
        try {
          const response = await RequestHandler.postRequest(
            SPRING_URL.concat("/playermatchstats/add"),
            this.novaStatistika
          );
          console.log("Added new player match stats:", response);  // Log the response
          this.playerMatchStats.push(response);  // Add the new player match stats to the list
          this.toggleStatistika();  // Hide the add statistics form
        } catch (error) {
          console.error("Error adding new player match stats:", error);
        }
      },
      toggleTaktika() {
        this.isTaktikaVisible = !this.isTaktikaVisible;
      },
      toggleStatistika() {
        this.isStatistikaVisible = !this.isStatistikaVisible;
      },
      getTacticName(id) {
        const tactic = this.tactics.find(t => t.idTactics === id);
        return tactic ? tactic.name : "Unknown";
      },
      getPlayerName(id) {
        const player = this.players.find(p => p.idPlayer === id);
        return player ? `${player.firstname} ${player.lastName}` : "Unknown";
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  
  <style scoped>
  .grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 16px;
  }
  </style>
  