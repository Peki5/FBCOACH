<template>
    <div class="container antialiased text-black">
        <div v-if="isDodajVisible">
            <!-- Add Health Status Section -->
            <div class="antialiased text-black">
                <div class="flex items-center w-full">
                    <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
                        <button @click="toggleDodaj"
                            class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                            Povratak
                        </button>
                        <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj novi zdravstveni status</span>
                        <form @submit.prevent="dodajNoviStatus" class="mb-4">
                            <div class="mb-4 md:w-full">
                                <label class="block text-xs mb-1">Datum</label>
                                <input v-model="noviStatus.date" type="date" required
                                    class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                            </div>
                            <div class="mb-4 md:w-full">
                                <label class="block text-xs mb-1">Status</label>
                                <input v-model="noviStatus.statusNotes" type="text" required
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
        <div v-else class="flex items-center w-full">
            <div class="w-full bg-white rounded shadow-lg p-8 m-4">
                <button @click="toggleDodaj"
                    class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                    Dodaj novi zdravstveni status
                </button>
                <span class="block w-full text-xl font-bold mt-4 mb-4">Zdravstveni Statusi</span>
                <div v-if="statuses.length > 0">
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                        <div v-for="status in statuses" :key="status.idHealthStatus"
                            class="p-4 border rounded bg-slate-200">
                            <p class="text-gray-700">Datum: {{ formatDate(status.date) }}</p>
                            <p class="text-gray-700">StatusNotes: {{ status.statusNotes }}</p>
                            <div class="flex space-x-2 mt-4">
                                <button @click="navigateToEdit(status.idHealthStatus)"
                                    class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Uredi</button>
                                <button @click="deleteStatus(status.idHealthStatus)"
                                    class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">Obriši</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-else class="mt-5">
                    <p>Nema zdravstvenih statusa za prikaz.</p>
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
            statuses: [],
            isDodajVisible: false,
            noviStatus: {
                date: "",
                statusNotes: "",
                playerId: this.$route.params.playerId,
            },
        };
    },
    async mounted() {
        const playerId = this.$route.params.playerId;
        try {
            const response = await RequestHandler.getRequest(
                SPRING_URL.concat(`/healthstatuses/player/`).concat(playerId)
            );
            console.log("Fetched health statuses:", response);  // Log the response
            this.statuses = response.map(item => ({
                idHealthStatus: item.idHealthStatus,
                date: item.date,
                statusNotes: item.statusNotes
            }));
        } catch (error) {
            console.error("Error fetching health statuses:", error);
        }
    },
    methods: {
        formatDate(date) {
            const options = { year: 'numeric', month: 'long', day: 'numeric' };
            return new Date(date).toLocaleDateString(undefined, options);
        },
        navigateToEdit(id) {
            this.$router.push({ name: 'EditHealthStatus', params: { id: id } });
        },
        async deleteStatus(id) {
            const confirmed = confirm("Jeste li sigurni da želite obrisati ovaj zdravstveni status?");
            if (confirmed) {
                try {
                    await RequestHandler.deleteRequest(SPRING_URL.concat(`/healthstatuses/delete/`).concat(id));
                    this.statuses = this.statuses.filter(status => status.idHealthStatus !== id);
                } catch (error) {
                    console.error("Error deleting health status:", error);
                }
            }
        },
        async dodajNoviStatus() {
            try {
                // Postavljanje playerId unutar objekta noviStatus
                this.noviStatus.playerId = this.$route.params.playerId;

                // Slanje zahtjeva za dodavanje novog zdravstvenog statusa
                const response = await RequestHandler.postRequest(
                    SPRING_URL.concat("/healthstatuses/add"),
                    this.noviStatus
                );

                // Provjera uspješnosti zahtjeva
                if (response) {
                    console.log("Added new health status:", response);  // Logiranje odgovora
                    this.statuses.push(response);  // Dodavanje novog statusa u listu
                    this.toggleDodaj();  // Sakrivanje forme za dodavanje novog statusa
                }
            } catch (error) {
                console.error("Error adding new health status:", error);
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

<style scoped>
.grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 16px;
}
</style>
