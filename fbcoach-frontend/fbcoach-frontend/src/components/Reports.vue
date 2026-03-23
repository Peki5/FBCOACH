<template>
    <main v-if="$store.state.user">
      <div v-if="isAddVisible">
        <div class="min-h-[80vh] flex items-center justify-center px-4">
          <div class="w-full max-w-md card">
            <button @click="toggleAdd" class="btn-secondary mb-4">
              Povratak
            </button>
            <span class="form-title">Dodaj novi izvještaj</span>
            <form @submit.prevent="addNewReport" class="space-y-5">
              <div>
                <label class="label">Datum</label>
                <input v-model="newReport.date" type="date" required class="input-field" />
              </div>
              <div>
                <label class="label">Opis</label>
                <textarea v-model="newReport.description" required class="input-field min-h-[80px]"></textarea>
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
            <h1 class="page-title">Izvještaji</h1>
            <button @click="toggleAdd" class="btn-primary">
              + Dodaj izvještaj
            </button>
          </div>

          <div v-if="reports.length > 0">
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
              <div v-for="report in reports" :key="report.idReport">
                <div class="card-item">
                  <div class="font-semibold text-fbcoach-text mb-1">{{ formatDate(report.date) }}</div>
                  <p class="text-fbcoach-text-muted text-sm">{{ report.description }}</p>
                  <div class="flex gap-2 mt-4">
                    <button @click="navigateToEditPage(report.idReport)" class="btn-warning text-xs">Edit</button>
                    <button @click="deleteReport(report.idReport)" class="btn-danger text-xs">Delete</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5 text-center py-8">
            <p class="text-fbcoach-text-muted">Nema izvještaja za prikaz</p>
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
  import { SPRING_URL } from "../constants";

  export default {
    data() {
      return {
        reports: [],
        isAddVisible: false,
        newReport: {
          date: "",
          description: "",
        },
      };
    },
    async mounted() {
      if (this.$store.state.user) {
        try {
          const response = await RequestHandler.getRequest(SPRING_URL.concat("/report"));

          if (Array.isArray(response)) {
            this.reports = response;
            this.reports.sort((a, b) => (a.idReport > b.idReport ? 1 : -1));
          }
        } catch {
        }
      }
    },
    methods: {
      toggleAdd() {
        this.isAddVisible = !this.isAddVisible;
      },
      async addNewReport() {
        try {
          const response = await RequestHandler.postRequest(SPRING_URL.concat("/report/add"), this.newReport);
          this.reports.push(response);
          this.reports.sort((a, b) => (a.idReport > b.idReport ? 1 : -1));
          this.newReport = { date: "", description: "" };
          this.isAddVisible = false;
        } catch {
        }
      },
      async deleteReport(id) {
        const confirmed = confirm("Jeste li sigurni?");
        if (confirmed) {
          try {
            await RequestHandler.deleteRequest(SPRING_URL.concat(`/report/delete/${id}`));
            this.reports = this.reports.filter(report => report.idReport !== id);
          } catch {
          }
        }
      },
      formatDate(date) {
        return new Date(date).toLocaleDateString("hr-HR");
      },
      navigateToEditPage(id) {
        this.$router.push({ name: 'EditReport', params: { id: id } });
      }
    },
  };
  </script>
