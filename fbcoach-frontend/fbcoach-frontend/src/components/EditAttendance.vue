<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Uredi Prisustvo</span>
        <form @submit.prevent="updateAttendance" class="space-y-5">
          <div>
            <label class="label">Datum</label>
            <input v-model="attendance.date" type="date" required class="input-field" />
          </div>
          <div>
            <label class="label">Prisutan</label>
            <select v-model="attendance.present" required class="input-field">
              <option v-for="present in presentTypes" :key="present" :value="present">{{ present }}</option>
            </select>
          </div>
          <div>
            <label class="label">Tip</label>
            <select v-model="attendance.type" required class="input-field">
              <option v-for="type in attendanceTypes" :key="type" :value="type">{{ type }}</option>
            </select>
          </div>
          <button class="btn-primary w-full py-3">
            Ažuriraj
          </button>
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
        attendance: {
          idAttendance: null,
          date: "",
          present: "",
          type: "",
          playerId: null,
        },
        attendanceTypes: ["MATCH", "TRAINING"],
        presentTypes: ["YES", "NO"]
      };
    },
    async mounted() {
      const idAttendance = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/attendance/`).concat(idAttendance)
        );
        response.date = new Date(response.date).toISOString().split('T')[0];
        console.log("Fetched attendance for edit:", response);
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
          this.$router.push({ name: 'ListAttendance', params: { playerId: this.attendance.playerId } });
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
