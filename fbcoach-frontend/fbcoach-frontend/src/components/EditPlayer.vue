<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Uredi igrača</span>
        <form @submit.prevent="updatePlayer" class="space-y-5">
          <div>
            <label class="label">Ime</label>
            <input v-model="player.firstname" type="text" required class="input-field" />
          </div>
          <div>
            <label class="label">Prezime</label>
            <input v-model="player.lastName" type="text" required class="input-field" />
          </div>
          <div>
            <label class="label">Datum rođenja</label>
            <input v-model="player.dateOfBirth" type="date" required class="input-field" />
          </div>
          <div>
            <label class="label">Pozicija</label>
            <select v-model="player.position" required class="input-field">
              <option v-for="pos in positions" :key="pos" :value="pos">{{ pos }}</option>
            </select>
          </div>
          <div>
            <label class="label">Visina (cm)</label>
            <input v-model="player.height" type="number" required class="input-field" />
          </div>
          <div>
            <label class="label">Težina (kg)</label>
            <input v-model="player.weight" type="number" required class="input-field" />
          </div>
          <button class="btn-primary w-full py-3">
            Ažuriraj
          </button>
        </form>
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
        console.log("Fetched player for edit:", response);
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
          this.$router.push({ name: 'ListPlayers', params: { teamId: this.player.teamIds[0] } });
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
