<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
        <div class="w-full max-w-md card">
            <span class="form-title">Prijava</span>
            <p class="text-fbcoach-danger text-center text-sm mb-4">{{ message }}</p>
            <form @submit.prevent="submit" class="space-y-5">
                <div>
                    <label class="label">Email</label>
                    <input v-model="user.email" type="email" required placeholder="example@fer.hr"
                    class="input-field">
                </div>
                <div>
                    <label class="label">Lozinka</label>
                    <input v-model="user.lozinka" type="password" required placeholder="******"
                    class="input-field">
                </div>
                <button class="btn-primary w-full py-3">
                    Prijavi se
                </button>
            </form>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { SPRING_URL } from '../constants'

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
