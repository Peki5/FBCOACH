<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <span class="form-title">Uredi izvještaj</span>
        <form @submit.prevent="editReport" class="space-y-5">
          <div>
            <label class="label">Datum</label>
            <input v-model="editedReport.date" type="date" required class="input-field" />
          </div>
          <div>
            <label class="label">Opis</label>
            <textarea v-model="editedReport.description" required class="input-field min-h-[80px]"></textarea>
          </div>
          <button type="submit" class="btn-primary w-full py-3">Spremi promjene</button>
        </form>
      </div>
    </div>
  </template>

  <script>
  import RequestHandler from "./../RequestHandler.js";
  import { SPRING_URL } from "../constants";

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
        this.editedReport.date = new Date(this.editedReport.date).toISOString().split('T')[0];
      } catch {
      }
    },
    methods: {
      async editReport() {
        const reportId = this.$route.params.id;
        try {
          await RequestHandler.putRequest(SPRING_URL.concat(`/report/edit/${reportId}`), this.editedReport);
          this.$router.push({ name: 'Reports' });
        } catch {
        }
      }
    }
  };
  </script>
