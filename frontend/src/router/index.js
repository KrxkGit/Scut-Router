import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import MainInfo from '../views/MainInfo.vue'
import ACInfo from '../views/ACInfo.vue'
import WiredLogin from '../views/WiredLogin.vue'
import WirelessLogin from '../views/WirelessLogin.vue'
import SyncTime from "../views/SyncTime.vue"
import NetworkInfo from "../views/NetworkInfo.vue"

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/MainInfo',
    name: 'main-info',
    component: MainInfo
  },
  {
    path: '/ACInfo',
    name: 'ac-info',
    component: ACInfo
  },
  {
    path: '/WiredLogin',
    name: 'wired-login',
    component: WiredLogin
  },
  {
    path: '/WirelessLogin',
    name: 'wireless-login',
    component: WirelessLogin
  },
  {
    path: '/SyncTime',
    name: 'sync-time',
    component: SyncTime
  },
  {
    path: '/NetworkInfo',
    name: 'network-info',
    component: NetworkInfo
  }
]

const router = createRouter({
  routes,
  history: createWebHistory(),
  scrollBehavior() {
    return { top: 0 }
  }
})

export default router
