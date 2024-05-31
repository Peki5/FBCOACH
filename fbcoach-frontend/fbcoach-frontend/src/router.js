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
import ListHealthStatus from './components/ListHealthStatus.vue';
import EditHealthStatus from './components/EditHealthStatus.vue';
import ListTactics from './components/ListTactics.vue' // Import ListTactics component
import EditTactics from './components/EditTactics.vue' // Import EditTactics component
import ListMatches from './components/ListMatches.vue' // Import ListMatches component
import MatchDetails from './components/MatchDetails.vue' // Import MatchDetails component
import EditMatch from './components/EditMatch.vue' // Import EditMatch component

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
    path: '/list-health-status/:playerId',
    name: 'ListHealthStatus',
    component: ListHealthStatus
  },
  {
    path: '/edit-health-status/:id',
    name: 'EditHealthStatus',
    component: EditHealthStatus
  },
  {
    path: '/list-tactics/:teamId', // Add route for listing tactics
    name: 'ListTactics',
    component: ListTactics
  },
  {
    path: '/edit-tactics/:id', // Add route for editing tactics
    name: 'EditTactics',
    component: EditTactics
  },
  {
    path: '/list-matches/:teamId', // Add route for listing matches
    name: 'ListMatches',
    component: ListMatches
  },
  {
    path: '/match-details/:id', // Add route for match details
    name: 'MatchDetails',
    component: MatchDetails
  },
  {
    path: '/edit-match/:id', // Add route for editing match
    name: 'EditMatch',
    component: EditMatch
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
