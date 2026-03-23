<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Uredi Zdravstveni Status</span>
        <form @submit.prevent="updateStatus" class="space-y-5">
          <div>
            <label class="label">Datum</label>
            <input v-model="status.date" type="date" required class="input-field" />
          </div>
          <div>
            <label class="label">Status</label>
            <input v-model="status.statusNotes" type="text" required class="input-field" />
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
        status: {
          idHealthStatus: null,
          date: "",
          statusNotes: "",
          playerId: null,
        },
      };
    },
    async mounted() {
      const idHealthStatus = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/healthstatuses/`).concat(idHealthStatus)
        );
        response.date = new Date(response.date).toISOString().split('T')[0];
        this.status = response;
      } catch {
      }
    },
    methods: {
      async updateStatus() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/healthstatuses/edit/${this.status.idHealthStatus}`),
            this.status
          );
          this.$router.push({ name: 'ListHealthStatus', params: { playerId: this.status.playerId } });
        } catch {
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
