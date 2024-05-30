<template>
    <main v-if="$store.state.user">
        <div v-if="isDodajVisible">
            <!-- Add Existing Player Section -->
            <div class="antialiased text-black">
                <div class="flex items-center w-full">
                    <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
                        <button @click="toggleDodaj" class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                            Povratak
                        </button>
                        <span class="block w-full text-xl font-bold mt-4 mb-4">Dodaj postojećeg igrača</span>
                        <div v-if="allPlayers.length > 0">
                            <div class="mt-5 pb-4">
                                <div v-for="player in allPlayers" :key="player.idPlayer" class="mb-4 p-4 border rounded bg-slate-200">
                                    <div class="flex justify-between">
                                        <div>
                                            <div class="font-bold text-xl">{{ player.firstname }} {{ player.lastName }}</div>
                                            <p class="text-gray-700">Pozicija: {{ player.position }}</p>
                                        </div>
                                        <div class="flex items-center space-x-2">
                                            <button @click="addExistingPlayer(player.idPlayer)" class="bg-green-500 hover:bg-green-400 text-white text-sm font-semibold px-4 py-2 rounded">Dodaj</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div v-else class="mt-5">
                            <p>Nema igrača za prikaz</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div v-else-if="isAddPlayerVisible">
            <!-- Add New Player Section -->
            <div class="antialiased text-black">
                <div class="flex items-center w-full">
                    <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
                        <button @click="toggleAddPlayer" class="bg-gray-500 hover:bg-gray-400 text-white text-sm font-semibold px-4 py-2 rounded">
                            Povratak
                        </button>
                        <span class="block w-full text-xl font-bold mt-4 mb-4">Kreiraj novog igrača</span>
                        <form @submit.prevent="dodajNovogIgraca" class="mb-4">
                            <div class="mb-4 md:w-full">
                                <label class="block text-s mb-1">Ime</label>
                                <input v-model="noviIgrac.firstname" type="text" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                            </div>
                            <div class="mb-4 md:w-full">
                                <label class="block text-s mb-1">Prezime</label>
                                <input v-model="noviIgrac.lastName" type="text" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                            </div>
                            <div class="mb-4 md:w-full">
                                <label class="block text-s mb-1">Datum rođenja</label>
                                <input v-model="noviIgrac.dateOfBirth" type="date" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                            </div>
                            <div class="mb-4 md:w-full">
                                <label class="block text-xs mb-1">Pozicija</label>
                                <select v-model="noviIgrac.position" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300">
                                    <option v-for="pos in position" :key="pos" :value="pos">{{ pos }}</option>
                                </select>
                            </div>
                            <div class="mb-4 md:w-full">
                                <label class="block text-s mb-1">Visina (cm)</label>
                                <input v-model="noviIgrac.height" type="number" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                            </div>
                            <div class="mb-4 md:w-full">
                                <label class="block text-s mb-1">Težina (kg)</label>
                                <input v-model="noviIgrac.weight" type="number" required class="w-full border rounded p-2 outline-none focus:outline focus:outline-slate-300" />
                            </div>
                            <button class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                                Dodaj
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- List of Players Section -->
        <div v-else class="container mt-4 text-black bg-white rounded">
            <div class="pt-4 pb-2 mb-8 relative">
                <div class="">
                    <h1 class="text-2xl py-2 px-1">Igrači</h1>
                    <button @click="toggleDodaj" class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                        Dodaj postojećeg igrača
                    </button>
                    <button @click="toggleAddPlayer" class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded ml-2">
                        Kreiraj novog igrača
                    </button>
                    <button @click="navigateToMatches" class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded ml-2">
                        Utakmice
                    </button>
                    <button @click="navigateToTrainings" class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded ml-2">
                        Treninzi
                    </button>
                    <button @click="navigateToTactics" class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded ml-2">
                        Taktike
                    </button>
                </div>
                <div v-if="igraci.length > 0">
                    <div class="mt-5 pb-4">
                        <div v-for="igrac in igraci" :key="igrac.idPlayer" class="mb-4 p-4 border rounded bg-slate-200">
                            <div class="flex justify-between">
                                <div>
                                    <div class="font-bold text-xl">{{ igrac.firstname }} {{ igrac.lastName }}</div>
                                    <p class="text-gray-700">Pozicija: {{ igrac.position }}</p>
                                </div>
                                <div class="flex items-center space-x-2">
                                    <button @click="navigateToDetails(igrac.idPlayer)" class="bg-blue-500 hover:bg-blue-400 text-white text-sm font-semibold px-4 py-2 rounded">Detalji</button>
                                    <button @click="removeFromTeam(igrac.idPlayer, teamId)" class="bg-red-500 hover:bg-red-400 text-white text-sm font-semibold px-4 py-2 rounded">Ukloni iz tima</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-else class="mt-5">
                    <p>Nema igrača za prikaz</p>
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
            igraci: [],
            allPlayers: [], // Add allPlayers array to hold the list of all players
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
            teamId: this.$route.params.teamId // added teamId to data
        };
    },
    async mounted() {
        if (this.$store.state.user) {
            try {
                const response = await RequestHandler.getRequest(
                    SPRING_URL.concat("/players?teamId=").concat(this.teamId)
                );
                console.log("Fetched players:", response);  // Log the response

                if (Array.isArray(response)) {
                    this.igraci = response;
                    this.igraci.sort((a, b) => (a.idPlayer > b.idPlayer ? 1 : -1));
                } else {
                    console.warn("Unexpected data format:", response);
                }
            } catch (error) {
                console.error("Error fetching players:", error);
            }
        }
    },
    methods: {
        async toggleDodaj() {
            this.isDodajVisible = !this.isDodajVisible;
            if (this.isDodajVisible) {
                try {
                    const response = await RequestHandler.getRequest(
                        SPRING_URL.concat("/players/all")
                    );
                    console.log("Fetched all players:", response);  // Log the response

                    if (Array.isArray(response)) {
                        this.allPlayers = response;
                        this.allPlayers.sort((a, b) => (a.idPlayer > b.idPlayer ? 1 : -1));
                    } else {
                        console.warn("Unexpected data format:", response);
                    }
                } catch (error) {
                    console.error("Error fetching all players:", error);
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
            } catch (error) {
                console.error("Error adding new player:", error);
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
            } catch (error) {
                console.error("Error adding existing player:", error);
            }
        },
        async removeFromTeam(playerId, teamId) {
            const confirmed = confirm("Jeste li sigurni?");
            if (confirmed) {
                try {
                    const response = await RequestHandler.deleteRequest(SPRING_URL.concat(`/players/remove?playerId=${playerId}&teamId=${teamId}`));
                    console.log("Player removed from team successfully:", playerId);  
                    this.igraci = this.igraci.filter(player => player.idPlayer !== playerId);
                } catch (error) {
                    console.error("Error removing player from team:", error);
                }
            }
        },
        navigateToDetails(id) {
            this.$router.push({ name: 'PlayerDetails', params: { id: id } });
        },
        navigateToAddPlayer() {
            this.$router.push({ name: 'AddPlayer' });
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
