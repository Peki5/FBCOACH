<template>
    <div class="container antialiased text-black">
      <div class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="goBack"
            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
            Povratak
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Uredi Prisustvo</span>
          <form @submit.prevent="updateAttendance" class="mb-4">
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Datum</label>
              <input v-model="attendance.date" type="date" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Prisutan</label>
              <select v-model="attendance.isPresent" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                <option :value="true">Da</option>
                <option :value="false">Ne</option>
              </select>
            </div>
            <div class="mb-4 md:w-full">
              <label class="block text-xs mb-1">Tip</label>
              <select v-model="attendance.type" required
                class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                <option v-for="type in attendanceTypes" :key="type" :value="type">{{ type }}</option>
              </select>
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
        attendance: {
          idAttendance: null,
          date: "",
          isPresent: "",
          type: "",
          playerId: null,
        },
        attendanceTypes: ["MATCH", "TRAINING"],
      };
    },
    async mounted() {
      const idAttendance = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/attendance/`).concat(idAttendance)
        );
        response.date = new Date(response.date).toISOString().split('T')[0];
        console.log("Fetched attendance for edit:", response);  // Log the response
        this.attendance = response;
      } catch (error) {
        console.error("Error fetching attendance for edit:", error);
      }
    },
    methods: {
      async updateAttendance() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/attendance/edit/${this.attendance.idAttendance}`),
            this.attendance
          );
          this.$router.push({ name: 'ListAttendance', params: { playerId: this.attendance.playerId } }); // Redirect to the list of attendances
        } catch (error) {
          console.error("Error updating attendance:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  