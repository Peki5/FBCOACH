<template>
    <div class="container mt-6">
      <div v-if="isDodajVisible">
        <div class="flex items-center justify-center px-4">
          <div class="w-full max-w-md card">
            <button @click="toggleDodaj" class="btn-secondary mb-4">
              Povratak
            </button>
            <span class="form-title">Dodaj novo prisustvo</span>
            <form @submit.prevent="dodajNovoPrisustvo" class="space-y-5">
              <div>
                <label class="label">Datum</label>
                <input v-model="novoPrisustvo.date" type="date" required class="input-field" />
              </div>
              <div>
                <label class="label">Prisutnost</label>
                <select v-model="novoPrisustvo.present" required class="input-field">
                  <option v-for="present in presentTypes" :key="present" :value="present">{{ present }}</option>
                </select>
              </div>
              <div>
                <label class="label">Tip</label>
                <select v-model="novoPrisustvo.type" required class="input-field">
                  <option v-for="type in attendanceTypes" :key="type" :value="type">{{ type }}</option>
                </select>
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
            <h1 class="page-title">Prisustva</h1>
            <button @click="toggleDodaj" class="btn-primary">
              + Dodaj prisustvo
            </button>
          </div>
          <div v-if="attendances.length > 0">
            <div class="space-y-3">
              <div v-for="attendance in attendances" :key="attendance.idAttendance" class="card-item">
                <div class="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-3">
                  <div class="space-y-1">
                    <p class="text-fbcoach-text-muted text-sm">{{ formatDate(attendance.date) }}</p>
                    <p class="text-fbcoach-text">Prisutan: <span :class="attendance.present === 'YES' ? 'text-fbcoach-primary' : 'text-fbcoach-danger'">{{ attendance.present }}</span></p>
                    <p class="text-fbcoach-text-muted text-sm">Tip: {{ attendance.type }}</p>
                  </div>
                  <div class="flex gap-2">
                    <button @click="navigateToEdit(attendance.idAttendance)" class="btn-warning text-xs">Uredi</button>
                    <button @click="deleteAttendance(attendance.idAttendance)" class="btn-danger text-xs">Obriši</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="mt-5 text-center py-8">
            <p class="text-fbcoach-text-muted">Nema prisustava za prikaz.</p>
          </div>
        </div>
      </div>
    </div>
  </template>

  <script>
  import RequestHandler from "./../RequestHandler.js";
  import {SPRING_URL} from "../constants";

  export default {
    data() {
      return {
        attendances: [],
        isDodajVisible: false,
        novoPrisustvo: {
          date: "",
          present: "",
          type: "",
          playerId: this.$route.params.playerId,
        },
        attendanceTypes: ["MATCH", "TRAINING"],
        presentTypes: ["YES", "NO"]
      };
    },
    async mounted() {
      const playerId = this.$route.params.playerId;
      try {
        this.attendances = await RequestHandler.getRequest(
            SPRING_URL.concat(`/attendance/player/`).concat(playerId)
        );
      } catch {
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
          } catch {
          }
        }
      },
      async dodajNovoPrisustvo() {
        try {
          await RequestHandler.postRequest(SPRING_URL.concat("/attendance/add"), this.novoPrisustvo);
          this.$router.go();
        } catch {
        }
      },
      toggleDodaj() {
        this.isDodajVisible = !this.isDodajVisible;
      },
    },
  };
  </script>
