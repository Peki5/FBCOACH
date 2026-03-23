<template>
    <div class="min-h-[80vh] flex items-center justify-center px-4">
        <div class="w-full max-w-md card">
            <span class="form-title">Registracija</span>
            <form @submit.prevent="submit" class="space-y-5">
                <div>
                    <label class="label">Ime</label>
                    <input v-model="user.ime" type="text" required placeholder="Ivo"
                        class="input-field">
                </div>
                <div>
                    <label class="label">Prezime</label>
                    <input v-model="user.prezime" type="text" required placeholder="Ivić"
                        class="input-field">
                </div>
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
                <button class="btn-primary w-full py-3">Registriraj se</button>
            </form>
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
                ime: '',
                prezime: '',
                email: '',
                lozinka: ''
            }
        }
    },
    methods: {
        async submit() {
            await axios.post(SPRING_URL.concat('/auth/register'), this.user)
                .then(response => {
                    this.$router.push("Login")
                })
                .catch(e => {
                    this.message = "Provjerite unesene podatke!"
                })
        }
    }
}
</script>
