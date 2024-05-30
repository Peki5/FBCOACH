<template>
    <div class="container mt-4 text-black bg-white rounded">
        <div class="pt-4 pb-2 mb-8 relative">
            <h1 class="text-2xl py-2 px-1">Detalji Igrača</h1>
            <div v-if="player">
                <div class="mb-4 p-4 border rounded bg-slate-200">
                    <div class="font-bold text-xl mb-2">{{ player.firstname }} {{ player.lastName }}</div>
                    <p class="text-gray-700">Datum rođenja: {{ formatDate(player.dateOfBirth) }}</p>
                    <p class="text-gray-700">Pozicija: {{ player.position }}</p>
                    <p class="text-gray-700">Visina: {{ player.height }} cm</p>
                    <p class="text-gray-700">Težina: {{ player.weight }} kg</p>
                    <div class="flex space-x-2 mt-4">
                        <button @click="navigateToEdit(player.idPlayer)" class="bg-yellow-500 hover:bg-yellow-400 text-white text-sm font-semibold px-4 py-2 rounded">Uredi</button>
                        <button @click="deletePlayer(player.idPlayer)" class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">Obriši</button>
                        <button @click="navigateToHealthStatus(player.idPlayer)" class="bg-blue-500 hover:bg-blue-400 text-white text-sm font-semibold px-4 py-2 rounded">Zdravstveni status</button>
                        <button @click="navigateToAttendance(player.idPlayer)" class="bg-green-500 hover:bg-green-400 text-white text-sm font-semibold px-4 py-2 rounded">Prisustvo</button>
                    </div>
                </div>
            </div>
            <div v-else class="mt-5">
                <p>Podaci o igraču nisu pronađeni.</p>
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
            player: null,
        };
    },
    async mounted() {
        const playerId = this.$route.params.id;
        try {
            const response = await RequestHandler.getRequest(SPRING_URL.concat("/players/").concat(playerId));
            console.log("Fetched player details:", response);
            this.player = response;
        } catch (error) {
            console.error("Error fetching player details:", error);
        }
    },
    methods: {
        formatDate(date) {
            const options = { year: 'numeric', month: 'long', day: 'numeric' };
            return new Date(date).toLocaleDateString(undefined, options);
        },
        navigateToEdit(id) {
            this.$router.push({ name: 'EditPlayer', params: { id: id } });
        },
        async deletePlayer(id) {
            const confirmed = confirm("Jeste li sigurni da želite obrisati igrača?");
            if (confirmed) {
                try {
                    await RequestHandler.deleteRequest(SPRING_URL.concat("/players/delete/").concat(id));
                    this.$router.go(-1);
                } catch (error) {
                    console.error("Error deleting player:", error);
                }
            }
        },
        navigateToHealthStatus(id) {
            this.$router.push({ name: 'HealthStatus', params: { playerId: id } });
        },
        navigateToAttendance(id) {
            this.$router.push({ name: 'Attendance', params: { playerId: id } });
        }
    }
};
</script>

<style scoped>
.container {
    padding: 20px;
}
</style>
