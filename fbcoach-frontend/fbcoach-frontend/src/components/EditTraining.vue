<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-md card">
        <button @click="goBack" class="btn-secondary mb-4">
          Povratak
        </button>
        <span class="form-title">Uredi trening</span>
        <form @submit.prevent="updateTraining" class="space-y-5">
          <div>
            <label class="label">Datum</label>
            <input v-model="training.date" type="date" required class="input-field" />
          </div>
          <div>
            <label class="label">Opis</label>
            <textarea v-model="training.description" required class="input-field min-h-[80px]"></textarea>
          </div>
          <div>
            <label class="label">Tip</label>
            <select v-model="training.type" required class="input-field">
              <option v-for="type in trainingTypes" :key="type" :value="type">{{ type }}</option>
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
        training: {
          idTraining: null,
          date: "",
          description: "",
          type: "",
          teamId: null
        },
        trainingTypes: ["STRENGTH", "EXPLOSIVE", "RUNNING", "TACTICAL"]
      };
    },
    async mounted() {
      const idTraining = this.$route.params.id;
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat(`/training/${idTraining}`)
        );

        response.date = new Date(response.date).toISOString().split('T')[0];

        this.training = response;
      } catch {
      }
    },
    methods: {
      async updateTraining() {
        try {
          await RequestHandler.putRequest(
            SPRING_URL.concat(`/training/edit/${this.training.idTraining}`),
            this.training
          );
          this.$router.push({ name: 'ListTraining', params: { teamId: this.training.teamId } });
        } catch {
        }
      },
      goBack() {
        this.$router.go(-1);
      },
    },
  };
  </script>
