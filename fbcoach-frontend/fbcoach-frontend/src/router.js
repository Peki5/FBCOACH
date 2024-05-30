import { createRouter, createWebHistory } from 'vue-router'
import store from './store.js'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import ListTeam from './components/ListTeam.vue'
import Reports from './components/Reports.vue'
import EditReport from './components/EditReport.vue' // Import EditReport component
import EditTeam from './components/EditTeam.vue' // Import EditTeam component
import ListTraining from './components/ListTraining.vue' // Import ListTraining component
import EditTraining from './components/EditTraining.vue' // Import EditTraining component

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
    path: '/edit-report/:id', // Add route for editing report
    name: 'EditReport',
    component: EditReport
  },
  {
    path: '/edit-team/:id', // Add route for editing team
    name: 'EditTeam',
    component: EditTeam
  },
  {
    path: '/list-training/:teamId', // Add route for listing trainings
    name: 'ListTraining',
    component: ListTraining
  },
  {
    path: '/edit-training/:id', // Add route for editing training
    name: 'EditTraining',
    component: EditTraining
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
