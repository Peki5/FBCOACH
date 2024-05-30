<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="goBack"
            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Uredi tim</span>
          <form @submit.prevent="updateTim" class="mb-4">
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Ime tima</label>
              <input v-model="tim.teamName" type="text" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Sezona</label>
              <input v-model="tim.season" type="text" required
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
        tim: {
          idTeam: null,
          teamName: "",
          season: "",
        },
      };
    },
    async mounted() {
      const idTeam = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/team/${idTeam}`)
        );
        console.log("Fetched team for edit:", response);  // Log the response
        this.tim = response;
      } catch (error) {
        console.error("Error fetching team for edit:", error);
      }
    },
    methods: {
      async updateTim() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/team/edit/${this.tim.idTeam}`),
            this.tim
          );
          this.$router.push({ name: 'home' });
        } catch (error) {
          console.error("Error updating team:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  