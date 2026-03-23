<template>
    <div class="container mt-6">
      <div v-if="isDodajVisible">
        <div class="flex items-center justify-center px-4">
          <div class="w-full max-w-md card">
            <button @click="toggleDodaj" class="btn-secondary mb-4">
              Povratak
            </button>
            <span class="form-title">Dodaj novu taktiku</span>
            <form @submit.prevent="dodajNovuTaktiku" class="space-y-5">
              <div>
                <label class="label">Ime</label>
                <input v-model="novaTaktika.name" type="text" required class="input-field" />
              </div>
              <div>
                <label class="label">Opis</label>
                <textarea v-model="novaTaktika.description" required class="input-field min-h-[80px]"></textarea>
              </div>
              <button class="btn-primary w-full py-3">
                Dodaj
              </button>
            </form>
          </div>
        </div>
      </div>
      <div v-else>
        <div class="card">
          <div class="flex items-center justify-between mb-6">
            <h1 class="page-title">Taktike</h1>
            <button @click="toggleDodaj" class="btn-primary">
              + Dodaj taktiku
            </button>
          </div>
          <div v-if="tactics.length > 0">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              <div v-for="tactic in tactics" :key="tactic.idTactics" class="card-item">
                <p class="text-fbcoach-text font-semibold mb-1">{{ tactic.name }}</p>
                <p class="text-fbcoach-text-muted text-sm">{{ tactic.description }}</p>
                <div class="flex gap-2 mt-3">
                  <button @click="navigateToEdit(tactic.idTactics)" class="btn-warning text-xs">Uredi</button>
                  <button @click="deleteTactic(tactic.idTactics)" class="btn-danger text-xs">Obriši</button>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5 text-center py-8">
            <p class="text-fbcoach-text-muted">Nema taktika za prikaz.</p>
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
        console.log("Fetched tactics:", response);
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
