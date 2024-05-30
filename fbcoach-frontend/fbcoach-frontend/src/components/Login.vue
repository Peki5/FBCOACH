<template>
    <div class="antialiased text-black">
        <div class="flex items-center  w-full">
            <div class="w-full bg-white rounded shadow-lg p-8 m-4 md:max-w-sm md:mx-auto">
                <span class="block w-full text-xl font-bold mb-4">Prijava</span>
                <p class="text-red-500 text-center">{{ message }}</p>
                <form @submit.prevent="submit" class="mb-4">
                    <div class="mb-4 md:w-full">
                        <label class="block text-xs mb-1">Email</label>
                        <input v-model="user.email" type="email" required placeholder="example@fer.hr" 
                        class="w-full border rounded p-2 outline-none focus:shadow-outline">
                    </div>
                    <div class="mb-6 md:w-full">
                        <label class="block text-xs mb-1">Lozinka</label>
                        <input v-model="user.lozinka" type="password" required placeholder="******"
                        class="w-full border rounded p-2 outline-none focus:shadow-outline">
                    </div>
                    <button class="bg-fbcoach-primary hover:bg-fbcoach-secondary text-white text-sm font-semibold px-4 py-2 rounded">
                        Prijavi se
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { SPRING_URL } from './../constants.js'

export default {
    data() {
        return {
            user: {
                email: '',
                lozinka: ''
            },
            message: ''
        }
    },
    methods: {
        async submit() {
            await axios.post(SPRING_URL.concat('/auth/login'), this.user)
            .then(response => {
                this.$store.state.user = response.data;
                sessionStorage.setItem("user", JSON.stringify(response.data));
                this.$router.push("/")
            })
            .catch(e => {
                this.message = "Krivi email ili lozinka!"
            })
        }
    }
}
</script>