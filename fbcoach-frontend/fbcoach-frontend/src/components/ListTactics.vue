<template>
    <div class="container antialiased text-black">
      <div v-if="isDodajVisible">
        <!-- Add Tactics Section -->
        <div class="antialiased text-black">
          <div class="flex items-center w-full">
            <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
              <button @click="toggleDodaj"
                class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                Povratak
              </button>
              <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj novu taktiku</span>
              <form @submit.prevent="dodajNovuTaktiku" class="mb-4">
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Ime</label>
                  <input v-model="novaTaktika.name" type="text" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Opis</label>
                  <textarea v-model="novaTaktika.description" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300"></textarea>
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
      <div v-else class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="toggleDodaj"
            class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
            Dodaj novu taktiku
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Taktike</span>
          <div v-if="tactics.length > 0">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              <div v-for="tactic in tactics" :key="tactic.idTactics"
                class="mb-4 p-4 border rounded bg-slate-200">
                <p class="text-gray-700">Ime: {{ tactic.name }}</p>
                <p class="text-gray-700">Opis: {{ tactic.description }}</p>
                <div class="flex space-x-2 mt-4">
                  <button @click="navigateToEdit(tactic.idTactics)"
                    class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Uredi</button>
                  <button @click="deleteTactic(tactic.idTactics)"
                    class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">Obriši</button>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5">
            <p>Nema taktika za prikaz.</p>
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
        tactics: [],
        isDodajVisible: false,
        novaTaktika: {
          name: "",
          description: "",
          teamId: this.$route.params.teamId,
        },
      };
    },
    async mounted() {
      const teamId = this.$route.params.teamId;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/tactics?teamId=`).concat(teamId)
        );
        console.log("Fetched tactics:", response);  // Log the response
        this.tactics = response;
      } catch (error) {
        console.error("Error fetching tactics:", error);
      }
    },
    methods: {
      navigateToEdit(id) {
        this.$router.push({ name: 'EditTactics', params: { id: id } });
      },
      async deleteTactic(id) {
        const confirmed = confirm("Jeste li sigurni da želite obrisati ovu taktiku?");
        if (confirmed) {
          try {
            await RequestHandler.deleteRequest(SPRING_URL.concat(`/tactics/delete/`).concat(id));
            this.tactics = this.tactics.filter(tactic => tactic.idTactics !== id);
          } catch (error) {
            console.error("Error deleting tactic:", error);
          }
        }
      },
      async dodajNovuTaktiku() {
        try {
          await RequestHandler.postRequest(SPRING_URL.concat("/tactics/add"), this.novaTaktika);
          this.$router.go();
        } catch (error) {
          console.error("Error adding new tactic:", error);
        }
      },
      toggleDodaj() {
        this.isDodajVisible = !this.isDodajVisible;
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
  