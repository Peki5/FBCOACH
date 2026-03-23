<template>
    <main v-if="$store.state.user">
      <div v-if="isDodajVisible">
        <div class="min-h-[80vh] flex items-center justify-center px-4">
          <div class="w-full max-w-md card">
            <button @click="toggleDodaj" class="btn-secondary mb-4">
              Povratak
            </button>
            <span class="form-title">Dodaj novi trening</span>
            <form @submit.prevent="dodajNoviTrening" class="space-y-5">
              <div>
                <label class="label">Datum</label>
                <input v-model="noviTrening.date" type="date" required class="input-field" />
              </div>
              <div>
                <label class="label">Opis</label>
                <textarea v-model="noviTrening.description" required class="input-field min-h-[80px]"></textarea>
              </div>
              <div>
                <label class="label">Tip</label>
                <select v-model="noviTrening.type" required class="input-field">
                  <option v-for="type in trainingTypes" :key="type" :value="type">{{ type }}</option>
                </select>
              </div>
              <button class="btn-primary w-full py-3">
                Dodaj
              </button>
            </form>
          </div>
        </div>
      </div>

      <div v-else class="container mt-6">
        <div class="card">
          <div class="flex items-center justify-between mb-6">
            <h1 class="page-title">Treninzi</h1>
            <button @click="toggleDodaj" class="btn-primary">
              + Dodaj trening
            </button>
          </div>

          <div v-if="treninzi.length > 0">
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
              <div v-for="trening in treninzi" :key="trening.idTraining">
                <div class="card-item">
                  <div class="font-semibold text-fbcoach-text mb-1">{{ formatDate(trening.date) }}</div>
                  <p class="text-fbcoach-text-muted text-sm mb-1">Opis: {{ trening.description }}</p>
                  <p class="text-fbcoach-text-muted text-sm">Tip: {{ trening.type }}</p>
                  <div class="flex gap-2 mt-4">
                    <button @click="navigateToEditPage(trening.idTraining)" class="btn-warning text-xs">Edit</button>
                    <button @click="obrisiTrening(trening.idTraining)" class="btn-danger text-xs">Delete</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5 text-center py-8">
            <p class="text-fbcoach-text-muted">Nema treninga za prikaz</p>
          </div>
        </div>
      </div>

    </main>

    <main v-else class="container">
      <div class="pt-16 mb-8 text-center">
        <h1 class="text-2xl font-medium text-fbcoach-text-muted">
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
        trainingTypes: ["STRENGTH", "EXPLOSIVE", "RUNNING", "TACTICAL"],
      };
    },
    async mounted() {
      if (this.$store.state.user) {
        try {
          const response = await RequestHandler.getRequest(
            SPRING_URL.concat(`/training?teamId=${this.$route.params.teamId}`)
          );

          if (Array.isArray(response)) {
            this.treninzi = response;
            this.treninzi.sort((a, b) => new Date(a.date) - new Date(b.date));
          }
        } catch {
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
            await RequestHandler.deleteRequest(SPRING_URL.concat(`/training/delete/${id}`));
            this.treninzi = this.treninzi.filter(training => training.idTraining !== id);
          } catch {
          }
        }
      },
      navigateToEditPage(id) {
        this.$router.push({ name: 'EditTraining', params: { id: id } });
      }
    },
  };
  </script>
