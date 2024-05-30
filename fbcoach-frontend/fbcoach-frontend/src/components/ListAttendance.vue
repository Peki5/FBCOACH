<template>
    <div class="container antialiased text-black">
      <div v-if="isDodajVisible">
        <!-- Add Attendance Section -->
        <div class="antialiased text-black">
          <div class="flex items-center w-full">
            <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
              <button @click="toggleDodaj"
                class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                Povratak
              </button>
              <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj novo prisustvo</span>
              <form @submit.prevent="dodajNovoPrisustvo" class="mb-4">
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Datum</label>
                  <input v-model="novoPrisustvo.date" type="date" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Prisutan</label>
                  <select v-model="novoPrisustvo.isPresent" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                    <option :value="true">Da</option>
                    <option :value="false">Ne</option>
                  </select>
                </div>
                <div class="mb-4 md:w-full">
                  <label class="block text-xs mb-1">Tip</label>
                  <select v-model="novoPrisustvo.type" required
                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                    <option v-for="type in attendanceTypes" :key="type" :value="type">{{ type }}</option>
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
      <div v-else class="flex items-center w-full">
        <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
          <button @click="toggleDodaj"
            class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
            Dodaj novo prisustvo
          </button>
          <span class="block w-full text-xl font-bold mt-4 mb-4">Prisustva</span>
          <div v-if="attendances.length > 0">
            <div v-for="attendance in attendances" :key="attendance.idAttendance" class="mb-4 p-4 border rounded bg-slate-200">
              <p class="text-gray-700">Datum: {{ formatDate(attendance.date) }}</p>
              <p class="text-gray-700">Prisutan: {{ attendance.isPresent ? 'Da' : 'Ne' }}</p>
              <p class="text-gray-700">Tip: {{ attendance.type }}</p>
              <div class="flex space-x-2 mt-4">
                <button @click="navigateToEdit(attendance.idAttendance)" class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Uredi</button>
                <button @click="deleteAttendance(attendance.idAttendance)" class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">Obriši</button>
              </div>
            </div>
          </div>
          <div v-else class="mt-5">
            <p>Nema prisustava za prikaz.</p>
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
        attendances: [],
        isDodajVisible: false,
        novoPrisustvo: {
          date: "",
          isPresent: "",
          type: "",
          playerId: this.$route.params.playerId,
        },
        attendanceTypes: ["MATCH", "TRAINING"], // Example attendance types
      };
    },
    async mounted() {
      const playerId = this.$route.params.playerId;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/attendance/player/`).concat(playerId)
        );
        console.log("Fetched attendances:", response);  // Log the response
        this.attendances = response;
      } catch (error) {
        console.error("Error fetching attendances:", error);
      }
    },
    methods: {
      formatDate(date) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(date).toLocaleDateString(undefined, options);
      },
      navigateToEdit(id) {
        this.$router.push({ name: 'EditAttendance', params: { id: id } });
      },
      async deleteAttendance(id) {
        const confirmed = confirm("Jeste li sigurni da želite obrisati ovo prisustvo?");
        if (confirmed) {
          try {
            await RequestHandler.deleteRequest(SPRING_URL.concat(`/attendance/delete/`).concat(id));
            this.attendances = this.attendances.filter(attendance => attendance.idAttendance !== id);
          } catch (error) {
            console.error("Error deleting attendance:", error);
          }
        }
      },
      async dodajNovoPrisustvo() {
        try {
          await RequestHandler.postRequest(SPRING_URL.concat("/attendance/add"), this.novoPrisustvo);
          this.$router.go();
        } catch (error) {
          console.error("Error adding new attendance:", error);
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
  