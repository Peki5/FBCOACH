<template>
    <div class="container mt-6">
        <div class="card">
            <h1 class="page-title mb-6">Detalji Igrača</h1>
            <div v-if="player">
                <div class="card-item">
                    <div class="font-semibold text-xl text-fbcoach-text mb-3">{{ player.firstname }} {{ player.lastName }}</div>
                    <div class="space-y-1.5">
                        <p class="text-fbcoach-text-muted">Datum rođenja: {{ formatDate(player.dateOfBirth) }}</p>
                        <p class="text-fbcoach-text-muted">Pozicija: {{ player.position }}</p>
                        <p class="text-fbcoach-text-muted">Visina: {{ player.height }} cm</p>
                        <p class="text-fbcoach-text-muted">Težina: {{ player.weight }} kg</p>
                    </div>
                    <div class="flex flex-wrap gap-2 mt-5">
                        <button @click="navigateToEdit(player.idPlayer)" class="btn-warning text-xs">Uredi</button>
                        <button @click="deletePlayer(player.idPlayer)" class="btn-danger text-xs">Obriši</button>
                        <button @click="navigateToHealthStatus(player.idPlayer)" class="btn-info text-xs">Zdravstveni status</button>
                        <button @click="navigateToAttendance(player.idPlayer)" class="btn-primary text-xs">Prisustvo</button>
                    </div>
                </div>
            </div>
            <div v-else class="mt-5 text-center py-8">
                <p class="text-fbcoach-text-muted">Podaci o igraču nisu pronađeni.</p>
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
            player: null,
        };
    },
    async mounted() {
        const playerId = this.$route.params.id;
        try {
          this.player = await RequestHandler.getRequest(SPRING_URL.concat("/players/").concat(playerId));
        } catch {
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
                } catch {
                }
            }
        },
        navigateToHealthStatus(id) {
            this.$router.push({ name: 'ListHealthStatus', params: { playerId: id } });
        },
        navigateToAttendance(id) {
            this.$router.push({ name: 'ListAttendance', params: { playerId: id } });
        }
    }
};
</script>
