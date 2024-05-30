<template>
    <main v-if="$store.state.user">
      <div v-if="isAddVisible">
        <!-- Add Report Section -->
        <div class="antialiased text-black">
          <div class="flex items-center w-full">
            <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
              <button @click="toggleAdd" class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                Povratak
              </button>
              <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj novi izvještaj</span>
              <form @submit.prevent="addNewReport" class="mb-4">
                <div class="mb-4 md:w-full">
                  <label class="block text-s mb-1">Datum</label>
                  <input v-model="newReport.date" type="date" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-s mb-1">Opis</label>
                  <textarea v-model="newReport.description" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300"></textarea>
                </div>
                <button class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                  Dodaj
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
  
      <!-- List of Reports Section -->
      <div v-else class="container mt-4 text-black bg-white rounded">
        <div class="pt-4 pb-2 mb-8 relative">
          <div>
            <h1 class="text-2xl py-2 px-1">Izvještaji</h1>
            <button @click="toggleAdd" class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
              Dodaj izvještaj
            </button>
          </div>
  
          <div v-if="reports.length > 0">
            <div class="mt-5 pb-4 grid grid-cols-3 gap-4">
              <div v-for="report in reports" :key="report.idReport">
                <div class="w-full h-full rounded overflow-hidden shadow-lg hover:cursor-pointer border bg-slate-200 p-2">
                  <div class="px-6 py-4">
                    <div class="font-bold text-xl mb-2">Datum: {{ formatDate(report.date) }}</div>
                    <p class="text-gray-700 text-base">
                      <span class="font-semibold">Opis:</span> {{ report.description }}
                    </p>
                    <button @click="deleteReport(report.idReport)" class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">
                      Delete
                    </button>
                    <button @click="navigateToEditPage(report.idReport)" class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Edit</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5">
            <p>Nema izvještaja za prikaz</p>
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
          console.log("Fetched reports:", response);
  
          if (Array.isArray(response)) {
            this.reports = response;
            this.reports.sort((a, b) => (a.idReport > b.idReport ? 1 : -1));
          } else {
            console.warn("Unexpected data format:", response);
          }
        } catch (error) {
          console.error("Error fetching reports:", error);
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
          console.log("Report added successfully:", response);
          this.reports.push(response);
          this.reports.sort((a, b) => (a.idReport > b.idReport ? 1 : -1));
          this.newReport = { date: "", description: "" };
          this.isAddVisible = false;
        } catch (error) {
          console.error("Error adding report:", error);
        }
      },
      async deleteReport(id) {
        const confirmed = confirm("Jeste li sigurni?");
        if (confirmed) {
          try {
            const response = await RequestHandler.deleteRequest(SPRING_URL.concat(`/report/delete/${id}`));
            console.log("Report deleted successfully:", id);
            this.reports = this.reports.filter(report => report.idReport !== id);
          } catch (error) {
            console.error("Error deleting report:", error);
          }
        }
      },
      formatDate(date) {
        // Format date as desired
        return new Date(date).toLocaleDateString("hr-HR");
      },
      navigateToEditPage(id) {
      this.$router.push({ name: 'EditReport', params: { id: id } });
    }
    },
  };
  </script>
  