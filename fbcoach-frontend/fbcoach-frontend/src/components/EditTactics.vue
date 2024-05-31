<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="goBack"
            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Uredi Taktiku</span>
          <form @submit.prevent="updateTactic" class="mb-4">
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Ime</label>
              <input v-model="tactic.name" type="text" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Opis</label>
              <textarea v-model="tactic.description" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300"></textarea>
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
        tactic: {
          idTactics: null,
          name: "",
          description: "",
          teamId: null,
        },
      };
    },
    async mounted() {
      const idTactics = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/tactics/`).concat(idTactics)
        );
        console.log("Fetched tactic for edit:", response);  // Log the response
        this.tactic = response;
      } catch (error) {
        console.error("Error fetching tactic for edit:", error);
      }
    },
    methods: {
      async updateTactic() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/tactics/edit/${this.tactic.idTactics}`),
            this.tactic
          );
          this.$router.push({ name: 'ListTactics', params: { teamId: this.tactic.teamId } }); // Redirect to the list of tactics
        } catch (error) {
          console.error("Error updating tactic:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  