import { createRouter, createWebHistory } from 'vue-router'
import store from './store.js'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import ListTeam from './components/ListTeam.vue'
import Reports from './components/Reports.vue'
import EditReport from './components/EditReport.vue'
import EditTeam from './components/EditTeam.vue'
import ListTraining from './components/ListTraining.vue'
import EditTraining from './components/EditTraining.vue'
import ListPlayers from './components/ListPlayers.vue'
import PlayerDetails from './components/PlayerDetails.vue'
import EditPlayer from './components/EditPlayer.vue'
import ListAttendance from './components/ListAttendance.vue'
import EditAttendance from './components/EditAttendance.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: ListTeam
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/reports',
    name: 'Reports',
    component: Reports
  },
  {
    path: '/edit-report/:id',
    name: 'EditReport',
    component: EditReport
  },
  {
    path: '/edit-team/:id',
    name: 'EditTeam',
    component: EditTeam
  },
  {
    path: '/list-training/:teamId',
    name: 'ListTraining',
    component: ListTraining
  },
  {
    path: '/edit-training/:id',
    name: 'EditTraining',
    component: EditTraining
  },
  {
    path: '/list-players/:teamId', 
    name: 'ListPlayers',
    component: ListPlayers
  },
  {
    path: '/player-details/:id',
    name: 'PlayerDetails',
    component: PlayerDetails
  },
  {
    path: '/edit-player/:id',
    name: 'EditPlayer',
    component: EditPlayer
  },
  {
    path: '/list-attendance/:playerId',
    name: 'ListAttendance',
    component: ListAttendance
  },
  {
    path: '/edit-attendance/:id',
    name: 'EditAttendance',
    component: EditAttendance
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from) => {
  if (store.state.user && (to.name === 'Login' || to.name === 'Register')) {
    return { name: 'home' }
  }
})

export default router
