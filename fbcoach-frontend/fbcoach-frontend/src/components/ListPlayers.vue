<template>
    <main v-if="$store.state.user">
        <div v-if="isDodajVisible">
            <div class="min-h-[80vh] flex items-center justify-center px-4">
                <div class="w-full max-w-md card">
                    <button @click="toggleDodaj" class="btn-secondary mb-4">
                        Povratak
                    </button>
                    <span class="form-title">Dodaj postojećeg igrača</span>
                    <div v-if="allPlayers.length > 0">
                        <div class="space-y-3">
                            <div v-for="player in allPlayers" :key="player.idPlayer" class="card-item">
                                <div class="flex justify-between items-center">
                                    <div>
                                        <div class="font-semibold text-fbcoach-text">{{ player.firstname }} {{ player.lastName }}</div>
                                        <p class="text-fbcoach-text-muted text-sm">Pozicija: {{ player.position }}</p>
                                    </div>
                                    <button @click="addExistingPlayer(player.idPlayer)" class="btn-primary text-xs">Dodaj</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-else class="mt-5 text-center py-4">
                        <p class="text-fbcoach-text-muted">Nema igrača za prikaz</p>
                    </div>
                </div>
            </div>
        </div>
        <div v-else-if="isAddPlayerVisible">
            <div class="min-h-[80vh] flex items-center justify-center px-4">
                <div class="w-full max-w-md card">
                    <button @click="toggleAddPlayer" class="btn-secondary mb-4">
                        Povratak
                    </button>
                    <span class="form-title">Kreiraj novog igrača</span>
                    <form @submit.prevent="dodajNovogIgraca" class="space-y-5">
                        <div>
                            <label class="label">Ime</label>
                            <input v-model="noviIgrac.firstname" type="text" required class="input-field" />
                        </div>
                        <div>
                            <label class="label">Prezime</label>
                            <input v-model="noviIgrac.lastName" type="text" required class="input-field" />
                        </div>
                        <div>
                            <label class="label">Datum rođenja</label>
                            <input v-model="noviIgrac.dateOfBirth" type="date" required class="input-field" />
                        </div>
                        <div>
                            <label class="label">Pozicija</label>
                            <select v-model="noviIgrac.position" required class="input-field">
                                <option v-for="pos in position" :key="pos" :value="pos">{{ pos }}</option>
                            </select>
                        </div>
                        <div>
                            <label class="label">Visina (cm)</label>
                            <input v-model="noviIgrac.height" type="number" required class="input-field" />
                        </div>
                        <div>
                            <label class="label">Težina (kg)</label>
                            <input v-model="noviIgrac.weight" type="number" required class="input-field" />
                        </div>
                        <button class="btn-primary w-full py-3">
                            Dodaj
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <div v-else class="container mt-6">
            <div class="card">
                <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6">
                    <h1 class="page-title">Igrači</h1>
                    <div class="flex flex-wrap gap-2">
                        <button @click="toggleDodaj" class="btn-primary text-xs">Dodaj postojećeg</button>
                        <button @click="toggleAddPlayer" class="btn-primary text-xs">Kreiraj novog</button>
                        <button @click="navigateToMatches" class="btn-info text-xs">Utakmice</button>
                        <button @click="navigateToTrainings" class="btn-info text-xs">Treninzi</button>
                        <button @click="navigateToTactics" class="btn-info text-xs">Taktike</button>
                    </div>
                </div>
                <div v-if="igraci.length > 0">
                    <div class="space-y-3">
                        <div v-for="igrac in igraci" :key="igrac.idPlayer" class="card-item">
                            <div class="flex justify-between items-center">
                                <div>
                                    <div class="font-semibold text-lg text-fbcoach-text">{{ igrac.firstname }} {{ igrac.lastName }}</div>
                                    <p class="text-fbcoach-text-muted text-sm">Pozicija: {{ igrac.position }}</p>
                                </div>
                                <div class="flex items-center gap-2">
                                    <button @click="navigateToDetails(igrac.idPlayer)" class="btn-info text-xs">Detalji</button>
                                    <button @click="removeFromTeam(igrac.idPlayer, teamId)" class="btn-danger text-xs">Ukloni</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-else class="mt-5 text-center py-8">
                    <p class="text-fbcoach-text-muted">Nema igrača za prikaz</p>
                </div>
            </div>
        </div>
    </main>
    <main v-else class="container">
        <div class="pt-16 mb-8 text-center">
            <h1 class="text-2xl font-medium text-fbcoach-text-muted">
                Molimo prijavite se u sustav!
            </h1>
        </div>
    </main>
</template>

<script>
import RequestHandler from "./../RequestHandler.js";
import { SPRING_URL } from "../constants";

export default {
    data() {
        return {
            igraci: [],
            allPlayers: [],
            isDodajVisible: false,
            isAddPlayerVisible: false,
            noviIgrac: {
                firstname: "",
                lastName: "",
                dateOfBirth: "",
                position: "",
                height: 0,
                weight: 0,
            },
            position: ["GOALKEEPER", "DEFENDER", "MIDFIELDER", "ATTACKER"],
            teamId: this.$route.params.teamId
        };
    },
    async mounted() {
        if (this.$store.state.user) {
            try {
                const response = await RequestHandler.getRequest(
                    SPRING_URL.concat("/players?teamId=").concat(this.teamId)
                );

                if (Array.isArray(response)) {
                    this.igraci = response;
                    this.igraci.sort((a, b) => (a.idPlayer > b.idPlayer ? 1 : -1));
                }
            } catch {
            }
        }
    },
    methods: {
        async toggleDodaj() {
            this.isDodajVisible = !this.isDodajVisible;
            if (this.isDodajVisible) {
                try {
                    const response = await RequestHandler.getRequest(
                        SPRING_URL.concat("/players/all?teamId=").concat(this.teamId)
                    );

                    if (Array.isArray(response)) {
                        this.allPlayers = response;
                        this.allPlayers.sort((a, b) => (a.idPlayer > b.idPlayer ? 1 : -1));
                    }
                } catch {
                }
            }
        },
        toggleAddPlayer() {
            this.isAddPlayerVisible = !this.isAddPlayerVisible;
        },
        async dodajNovogIgraca() {
            try {
                await RequestHandler.postRequest(SPRING_URL.concat("/players/add"), this.noviIgrac);
                this.$router.go();
            } catch {
            }
        },
        async addExistingPlayer(playerId) {
            const dataIgrac = {
                idPlayer: playerId,
                teamId: this.teamId
            };
            try {
                await RequestHandler.postRequest(SPRING_URL.concat("/players/addToTeam"), dataIgrac);
                this.$router.go();
            } catch {
            }
        },
        async removeFromTeam(playerId, teamId) {
            const confirmed = confirm("Jeste li sigurni?");
            if (confirmed) {
                try {
                    await RequestHandler.deleteRequest(SPRING_URL.concat(`/players/remove?playerId=${playerId}&teamId=${teamId}`));
                    this.igraci = this.igraci.filter(player => player.idPlayer !== playerId);
                } catch {
                }
            }
        },
        navigateToDetails(id) {
            this.$router.push({ name: 'PlayerDetails', params: { id: id } });
        },
        navigateToMatches() {
            this.$router.push({ name: 'ListMatches', params: { teamId: this.teamId } });
        },
        navigateToTrainings() {
            this.$router.push({ name: 'ListTraining', params: { teamId: this.teamId } });
        },
        navigateToTactics() {
            this.$router.push({ name: 'ListTactics', params: { teamId: this.teamId } });
        }
    }
};
</script>
