<template>
  <main v-if="$store.state.user">
    <div v-if="isDodajVisible">
      <!-- Add Team Section -->
      <div class="antialiased text-black">
        <div class="flex items-center w-full">
          <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
            <button @click="toggleDodaj"
              class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
              Povratak
            </button>
            <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj novi tim</span>
            <form @submit.prevent="dodajNoviTim" class="mb-4">
              <div class="mb-4 md:w-full">
                <label class="block text-xs mb-1">Ime tima</label>
                <input v-model="noviTim.teamName" type="text" required
                  class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
              </div>
              <div class="mb-4 md:w-full">
                <label class="block text-xs mb-1">Sezona</label>
                <input v-model="noviTim.season" type="text" required
                  class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
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

    <!-- List of Teams Section -->
    <div v-else class="container mt-4 text-black bg-white rounded">
      <div class="pt-4 mb-8 relative">
        <div class="">
          <h1 class="text-2xl py-2 px-1">Timovi</h1>
          <button @click="toggleDodaj"
            class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
            Dodaj tim
          </button>
        </div>

        <div v-if="timovi.length > 0">
          <div class="mt-5 pb-4 grid grid-cols-3 gap-4">
            <div v-for="tim in timovi" :key="tim.idTeam">
              <div class="w-full h-full rounded overflow-hidden shadow-lg hover:cursor-pointer border bg-slate-200 p-2">
                <div class="px-6 py-4">
                  <div class="font-bold text-xl mb-2">{{ tim.teamName }}</div>
                  <p class="text-gray-700 text-base">
                    Sezona: {{ tim.season }}
                  </p>
                  <button @click="obrisiTim(tim.idTeam)"
                    class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">
                    Delete
                  </button>
                  <button @click="navigateToEditPage(tim.idTeam)" class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Edit</button>
                  <button @click="navigateToTrainings(tim.idTeam)" class="bg-blue-500 hover:bg-blue-400 text-white text-sm font-semibold px-4 py-2 rounded">Trainings</button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="mt-5">
          <p>Nema timova za prikaz</p>
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
      timovi: [],
      isDodajVisible: false,
      noviTim: {
        teamName: "",
        season: "",
      },
    };
  },
  async mounted() {
    if (this.$store.state.user) {
      try {
        const response = await RequestHandler.getRequest(
          SPRING_URL.concat("/team")
        );
        console.log("Fetched teams:", response);  // Log the response

        if (Array.isArray(response)) {
          this.timovi = response;
          this.timovi.sort((a, b) => (a.idTeam > b.idTeam ? 1 : -1));
        } else {
          console.warn("Unexpected data format:", response);
        }
      } catch (error) {
        console.error("Error fetching teams:", error);
      }
    }
  },
  methods: {
    toggleDodaj() {
      this.isDodajVisible = !this.isDodajVisible;
    },
    async dodajNoviTim() {
      this.noviTim.coach = { id: this.$store.state.user.id };
      await RequestHandler.postRequest(
        SPRING_URL.concat("/team/add"),
        this.noviTim
      );
      this.$router.go();
    },
    async obrisiTim(id) {
      const confirmed = confirm("Jeste li sigurni?");
      
      if (confirmed) {
        try {
          const response = await RequestHandler.deleteRequest(SPRING_URL.concat(`/team/delete/${id}`));
          console.log("Team deleted successfully:", id);  
          this.timovi = this.timovi.filter(team => team.idTeam !== id);
        } catch (error) {
          console.error("Error deleting team:", error);
        }
      }
    },
    navigateToEditPage(id) {
      this.$router.push({ name: 'EditTeam', params: { id: id } });
    },
    navigateToTrainings(id) {
      this.$router.push({ name: 'ListTraining', params: { teamId: id } });
    }
  },
};
</script>
