<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="goBack"
            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Uredi Zdravstveni Status</span>
          <form @submit.prevent="updateStatus" class="mb-4">
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Datum</label>
              <input v-model="status.date" type="date" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Status</label>
              <input v-model="status.statusNotes" type="text" required
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
        console.log("Fetched health status for edit:", response);  // Log the response
        this.status = response;
      } catch (error) {
        console.error("Error fetching health status for edit:", error);
      }
    },
    methods: {
      async updateStatus() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/healthstatuses/edit/${this.status.idHealthStatus}`),
            this.status
          );
          this.$router.push({ name: 'ListHealthStatus', params: { playerId: this.status.playerId } }); // Redirect to the list of health statuses
        } catch (error) {
          console.error("Error updating health status:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  