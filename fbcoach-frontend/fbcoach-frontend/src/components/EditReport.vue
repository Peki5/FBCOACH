<template>
    <div class="container mx-auto">
      <div class="bg-white rounded-lg shadow-md p-8 mt-8">
        <h1 class="text-2xl font-bold mb-4">Edit Report</h1>
        <form @submit.prevent="editReport">
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2">Datum</label>
            <input v-model="editedReport.date" type="date" required class="border rounded w-full px-3 py-2 placeholder-gray-400 focus:outline-none focus:ring focus:border-blue-300" />
          </div>
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2">Opis</label>
            <textarea v-model="editedReport.description" required class="border rounded w-full px-3 py-2 placeholder-gray-400 focus:outline-none focus:ring focus:border-blue-300"></textarea>
          </div>
          <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Save Changes</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import RequestHandler from "./../RequestHandler.js";
  import { SPRING_URL } from "./../constants.js";
  
  export default {
    data() {
      return {
        editedReport: {
          id: null,
          date: "",
          description: ""
        }
      };
    },
    async mounted() {
      const reportId = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(SPRING_URL.concat(`/report/${reportId}`));
        this.editedReport = response;
        // Convert ISO date to string format for input field
        this.editedReport.date = new Date(this.editedReport.date).toISOString().split('T')[0];
      } catch (error) {
        console.error("Error fetching report:", error);
      }
    },
    methods: {
      async editReport() {
        const reportId = this.$route.params.id;
        try {
          const response = await RequestHandler.putRequest(SPRING_URL.concat(`/report/edit/${reportId}`), this.editedReport);
          console.log("Report edited successfully:", response);
          // Optionally, navigate back to the list of reports
          this.$router.push({ name: 'Reports' });
        } catch (error) {
          console.error("Error editing report:", error);
        }
      }
    }
  };
  </script>
  