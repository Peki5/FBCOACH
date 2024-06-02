<template>
    <main v-if="$store.state.user">
      <div v-if="isDodajVisible">
        <!-- Add Training Section -->
        <div class="antialiased text-black">
          <div class="flex items-center w-full">
            <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
              <button @click="toggleDodaj"
                class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                Povratak
              </button>
              <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj novi trening</span>
              <form @submit.prevent="dodajNoviTrening" class="mb-4">
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Datum</label>
                  <input v-model="noviTrening.date" type="date" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Opis</label>
                  <textarea v-model="noviTrening.description" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300"></textarea>
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Tip</label>
                  <select v-model="noviTrening.type" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                    <option v-for="type in trainingTypes" :key="type" :value="type">{{ type }}</option>
                  </select>
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
  
      <!-- List of Trainings Section -->
      <div v-else class="container mt-4 text-black bg-white rounded">
        <div class="pt-4 mb-8 relative">
          <div class="">
            <h1 class="text-2xl py-2 px-1">Treninzi</h1>
            <button @click="toggleDodaj"
              class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
              Dodaj trening
            </button>
          </div>
  
          <div v-if="treninzi.length > 0">
            <div class="mt-5 pb-4 grid grid-cols-3 gap-4">
              <div v-for="trening in treninzi" :key="trening.idTraining">
                <div class="w-full h-full rounded overflow-hidden shadow-lg hover:cursor-pointer border bg-slate-200 p-2">
                  <div class="px-6 py-4">
                    <div class="font-bold text-xl mb-2">Datum: {{ formatDate(trening.date) }}</div>
                    <p class="text-gray-700 text-base">
                      Opis: {{ trening.description }}
                    </p>
                    <p class="text-gray-700 text-base">
                      Tip: {{ trening.type }}
                    </p>
                    <button @click="obrisiTrening(trening.idTraining)"
                      class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">
                      Delete
                    </button>
                    <button @click="navigateToEditPage(trening.idTraining)" class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Edit</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5">
            <p>Nema treninga za prikaz</p>
          </div>
        </div>
      </div>
  
    </main>
  
    <!-- Not Logged In Section -->
    <main v-else class="container text-white">
      <div class="pt-8 mb-8 relative">
        <h1 class="text-2xl text-center py-2 px-1">
          Molimo prijavite se u sustav!
        </h1>
      </div>
    </main>
  </template>
  
  <script>
  import RequestHandler from "./../RequestHandler.js";
  import { SPRING_URL } from "./../constants.js";
  
  export default {
    data() {
      return {
        treninzi: [],
        isDodajVisible: false,
        noviTrening: {
          date: "",
          description: "",
          type: "",
          teamId: this.$route.params.teamId
        },
        trainingTypes: ["STRENGTH", "EXPLOSIVE", "RUNNING", "TACTICAL"], // Training types
      };
    },
    async mounted() {
      if (this.$store.state.user) {
        try {
          const response = await RequestHandler.getRequest(
            SPRING_URL.concat(`/training?teamId=${this.$route.params.teamId}`)
          );
          console.log("Fetched trainings:", response); 
  
          if (Array.isArray(response)) {
            this.treninzi = response;
            this.treninzi.sort((a, b) => (a.idTraining > b.idTraining ? 1 : -1));
          } else {
            console.warn("Unexpected data format:", response);
          }
        } catch (error) {
          console.error("Error fetching trainings:", error);
        }
      }
    },
    methods: {
      formatDate(dateString) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString('hr-HR', options);
      },
      toggleDodaj() {
        this.isDodajVisible = !this.isDodajVisible;
      },
      async dodajNoviTrening() {
        await RequestHandler.postRequest(
          SPRING_URL.concat("/training/add"),
          this.noviTrening
        );
        this.$router.go();
      },
      async obrisiTrening(id) {
        const confirmed = confirm("Jeste li sigurni?");
        
        if (confirmed) {
          try {
            const response = await RequestHandler.deleteRequest(SPRING_URL.concat(`/training/delete/${id}`));
            console.log("Training deleted successfully:", id);  
            this.treninzi = this.treninzi.filter(training => training.idTraining !== id);
          } catch (error) {
            console.error("Error deleting training:", error);
          }
        }
      },
      navigateToEditPage(id) {
        this.$router.push({ name: 'EditTraining', params: { id: id } });
      }
    },
  };
  </script>
  