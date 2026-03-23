<template>
    <div class="container mt-6">
      <div class="card max-w-4xl mx-auto">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Detalji Utakmice</span>

        <div class="card-item mb-6">
          <div class="grid grid-cols-2 gap-3">
            <p class="text-fbcoach-text-muted">Datum: <span class="text-fbcoach-text">{{ match.date }}</span></p>
            <p class="text-fbcoach-text-muted">Protivnik: <span class="text-fbcoach-text font-semibold">{{ match.opponent }}</span></p>
            <p class="text-fbcoach-text-muted">Lokacija: <span class="text-fbcoach-text">{{ match.location === 'HOME' ? 'Doma' : 'Gost' }}</span></p>
            <p class="text-fbcoach-text-muted">Rezultat: <span class="text-fbcoach-primary font-semibold">{{ match.result }}</span></p>
          </div>
        </div>

        <div class="flex gap-2 mb-6">
          <button @click="toggleTaktika" class="btn-primary text-xs">
            Dodaj Taktiku
          </button>
          <button @click="toggleStatistika" class="btn-primary text-xs">
            Dodaj Statistiku
          </button>
        </div>

        <div v-if="isTaktikaVisible" class="card-item mb-6">
          <form @submit.prevent="dodajTaktiku" class="space-y-4">
            <div>
              <label class="label">Taktika</label>
              <select v-model="novaTaktika.tacticsId" required class="input-field">
                <option v-for="tactic in tactics" :key="tactic.idTactics" :value="tactic.idTactics">{{ tactic.name }}</option>
              </select>
            </div>
            <div>
              <label class="label">Opis</label>
              <input v-model="novaTaktika.description" type="text" required class="input-field" />
            </div>
            <button class="btn-primary">
              Dodaj Taktiku
            </button>
          </form>
        </div>

        <div v-if="isStatistikaVisible" class="card-item mb-6">
          <form @submit.prevent="dodajStatistiku" class="space-y-4">
            <div>
              <label class="label">Igrač</label>
              <select v-model="novaStatistika.playerId" required class="input-field">
                <option v-for="player in players" :key="player.idPlayer" :value="player.idPlayer">{{ player.firstname }} {{ player.lastName }}</option>
              </select>
            </div>
            <div>
              <label class="label">Golovi</label>
              <input v-model="novaStatistika.goals" type="number" min="0" required class="input-field" />
            </div>
            <div>
              <label class="label">Asistencije</label>
              <input v-model="novaStatistika.assists" type="number" min="0" required class="input-field" />
            </div>
            <button class="btn-primary">
              Dodaj Statistiku
            </button>
          </form>
        </div>

        <div class="mb-6">
          <h2 class="section-title mb-3">Taktike</h2>
          <div v-if="tacticsApplications.length > 0" class="space-y-3">
            <div v-for="tacticsApplication in tacticsApplications" :key="tacticsApplication.idTacticsApplication" class="card-item">
              <p class="text-fbcoach-text-muted text-sm">Taktika: <span class="text-fbcoach-text">{{ getTacticName(tacticsApplication.tacticsId) }}</span></p>
              <p class="text-fbcoach-text-muted text-sm">Opis: <span class="text-fbcoach-text">{{ tacticsApplication.description }}</span></p>
            </div>
          </div>
          <div v-else class="text-center py-4">
            <p class="text-fbcoach-text-muted text-sm">Nema taktičkih aplikacija za prikaz.</p>
          </div>
        </div>

        <div>
          <h2 class="section-title mb-3">Statistika</h2>
          <div v-if="playerMatchStats.length > 0">
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div class="card-item">
                <h3 class="font-semibold text-fbcoach-text mb-2">Strijelci</h3>
                <ul class="space-y-1">
                  <li v-for="stat in goalscorers" :key="stat.idPlayerMatchStats" class="text-fbcoach-text-muted text-sm">
                    <span class="text-fbcoach-text">{{ getPlayerName(stat.playerId) }}</span> - {{ stat.goals }} gol(ova)
                  </li>
                </ul>
              </div>
              <div class="card-item">
                <h3 class="font-semibold text-fbcoach-text mb-2">Asistenti</h3>
                <ul class="space-y-1">
                  <li v-for="stat in assistants" :key="stat.idPlayerMatchStats" class="text-fbcoach-text-muted text-sm">
                    <span class="text-fbcoach-text">{{ getPlayerName(stat.playerId) }}</span> - {{ stat.assists }} asist.
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div v-else class="text-center py-4">
            <p class="text-fbcoach-text-muted text-sm">Nema statistike za prikaz.</p>
          </div>
        </div>
      </div>
    </div>
  </template>

  <script>
  import RequestHandler from "./../RequestHandler.js";
  import {SPRING_URL} from "../constants";

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
        this.match = matchResponse;

        this.tactics = await RequestHandler.getRequest(
            SPRING_URL.concat(`/tactics?teamId=`).concat(this.match.teamId)
        );

        this.players = await RequestHandler.getRequest(
            SPRING_URL.concat(`/players?teamId=`).concat(this.match.teamId)
        );

        this.tacticsApplications = await RequestHandler.getRequest(
            SPRING_URL.concat(`/tacticsapplication/match/`).concat(idMatch)
        );

        this.playerMatchStats = await RequestHandler.getRequest(
            SPRING_URL.concat(`/playermatchstats/match/`).concat(idMatch)
        );
      } catch {
      }
    },
    computed: {
      goalscorers() {
        return this.playerMatchStats.filter(stat => stat.goals > 0);
      },
      assistants() {
        return this.playerMatchStats.filter(stat => stat.assists > 0);
      },
    },
    methods: {
      async dodajTaktiku() {
        try {
          const response = await RequestHandler.postRequest(
            SPRING_URL.concat("/tacticsapplication/add"),
            this.novaTaktika
          );
          this.tacticsApplications.push(response);
          this.novaTaktika = {
            description: "",
            tacticsId: null,
            matchId: this.$route.params.id,
          };
          this.toggleTaktika();
        } catch {
        }
      },
      async dodajStatistiku() {
        try {
          const response = await RequestHandler.postRequest(
            SPRING_URL.concat("/playermatchstats/add"),
            this.novaStatistika
          );
          this.playerMatchStats.push(response);
          this.toggleStatistika();
        } catch {
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
