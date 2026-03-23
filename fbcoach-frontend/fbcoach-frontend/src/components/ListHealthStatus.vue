<template>
    <div class="container mt-6">
        <div v-if="isDodajVisible">
            <div class="flex items-center justify-center px-4">
                <div class="w-full max-w-md card">
                    <button @click="toggleDodaj" class="btn-secondary mb-4">
                        Povratak
                    </button>
                    <span class="form-title">Dodaj novi zdravstveni status</span>
                    <form @submit.prevent="dodajNoviStatus" class="space-y-5">
                        <div>
                            <label class="label">Datum</label>
                            <input v-model="noviStatus.date" type="date" required class="input-field" />
                        </div>
                        <div>
                            <label class="label">Status</label>
                            <input v-model="noviStatus.statusNotes" type="text" required class="input-field" />
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
                    <h1 class="page-title">Zdravstveni Statusi</h1>
                    <button @click="toggleDodaj" class="btn-primary">
                        + Dodaj status
                    </button>
                </div>
                <div v-if="statuses.length > 0">
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                        <div v-for="status in statuses" :key="status.idHealthStatus" class="card-item">
                            <p class="text-fbcoach-text-muted text-sm mb-1">{{ formatDate(status.date) }}</p>
                            <p class="text-fbcoach-text">{{ status.statusNotes }}</p>
                            <div class="flex gap-2 mt-3">
                                <button @click="navigateToEdit(status.idHealthStatus)" class="btn-warning text-xs">Uredi</button>
                                <button @click="deleteStatus(status.idHealthStatus)" class="btn-danger text-xs">Obriši</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-else class="mt-5 text-center py-8">
                    <p class="text-fbcoach-text-muted">Nema zdravstvenih statusa za prikaz.</p>
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
            this.statuses = response.map(item => ({
                idHealthStatus: item.idHealthStatus,
                date: item.date,
                statusNotes: item.statusNotes
            }));
        } catch {
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
                } catch {
                }
            }
        },
        async dodajNoviStatus() {
            try {
                this.noviStatus.playerId = this.$route.params.playerId;

                const response = await RequestHandler.postRequest(
                    SPRING_URL.concat("/healthstatuses/add"),
                    this.noviStatus
                );

                if (response) {
                    this.statuses.push(response);
                    this.toggleDodaj();
                }
            } catch {
            }
        },
        toggleDodaj() {
            this.isDodajVisible = !this.isDodajVisible;
        },
    },
};
</script>
