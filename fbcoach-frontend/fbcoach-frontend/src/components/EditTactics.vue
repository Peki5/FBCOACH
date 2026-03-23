<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Uredi Taktiku</span>
        <form @submit.prevent="updateTactic" class="space-y-5">
          <div>
            <label class="label">Ime</label>
            <input v-model="tactic.name" type="text" required class="input-field" />
          </div>
          <div>
            <label class="label">Opis</label>
            <textarea v-model="tactic.description" required class="input-field min-h-[80px]"></textarea>
          </div>
          <button class="btn-primary w-full py-3">
            Ažuriraj
          </button>
        </form>
      </div>
    </div>
  </template>

  <script>
  import RequestHandler from "../RequestHandler.js";
  import {SPRING_URL} from "../constants.js";

  export default {
    data() {
      return {
        tactic: {
          idTactics: null,
          name: "",
          description: "",
          teamId: null,
        },
      };
    },
    async mounted() {
      const idTactics = this.$route.params.id;
      try {
        this.tactic = await RequestHandler.getRequest(
            SPRING_URL.concat(`/tactics/`).concat(idTactics)
        );
      } catch {
      }
    },
    methods: {
      async updateTactic() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/tactics/edit/${this.tactic.idTactics}`),
            this.tactic
          );
          this.$router.push({ name: 'ListTactics', params: { teamId: this.tactic.teamId } });
        } catch {
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
