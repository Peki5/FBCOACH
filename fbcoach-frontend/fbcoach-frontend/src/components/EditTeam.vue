<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Uredi tim</span>
        <form @submit.prevent="updateTim" class="space-y-5">
          <div>
            <label class="label">Ime tima</label>
            <input v-model="tim.teamName" type="text" required class="input-field" />
          </div>
          <div>
            <label class="label">Sezona</label>
            <input v-model="tim.season" type="text" required class="input-field" />
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
        tim: {
          idTeam: null,
          teamName: "",
          season: "",
        },
      };
    },
    async mounted() {
      const idTeam = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/team/${idTeam}`)
        );
        console.log("Fetched team for edit:", response);
        this.tim = response;
      } catch (error) {
        console.error("Error fetching team for edit:", error);
      }
    },
    methods: {
      async updateTim() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/team/edit/${this.tim.idTeam}`),
            this.tim
          );
          this.$router.push({ name: 'home' });
        } catch (error) {
          console.error("Error updating team:", error);
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
