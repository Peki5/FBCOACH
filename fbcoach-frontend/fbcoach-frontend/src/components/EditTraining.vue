<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="goBack" class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Uredi trening</span>
          <form @submit.prevent="updateTraining" class="mb-4">
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Datum</label>
              <input v-model="training.date" type="date" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Opis</label>
              <textarea v-model="training.description" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300"></textarea>
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Tip</label>
              <select v-model="training.type" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                <option v-for="type in trainingTypes" :key="type" :value="type">{{ type }}</option>
              </select>
            </div>
            <button class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
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
        training: {
          idTraining: null,
          date: "",
          description: "",
          type: "",
          teamId: null
        },
        trainingTypes: ["STRENGTH", "EXPLOSIVE", "RUNNING", "TACTICAL"] // Training types
      };
    },
    async mounted() {
      const idTraining = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/training/${idTraining}`)
        );
        
        // Convert date format from server to 'YYYY-MM-DD'
        response.date = new Date(response.date).toISOString().split('T')[0];
  
        console.log("Fetched training for edit:", response);  // Log the response
        this.training = response;
      } catch (error) {
        console.error("Error fetching training for edit:", error);
      }
    },
    methods: {
      async updateTraining() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/training/edit/${this.training.idTraining}`),
            this.training
          );
          this.$router.push({ name: 'ListTraining', params: { teamId: this.training.teamId } });
        } catch (error) {
          console.error("Error updating training:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  