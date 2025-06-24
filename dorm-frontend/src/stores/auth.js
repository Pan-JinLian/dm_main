import { defineStore } from 'pinia'
import { ref } from 'vue'

//认证管理
export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))
  
  const isAuthenticated = ref(!!token.value)
  
  function login(newToken, userData) {
    token.value = newToken
    user.value = userData
    isAuthenticated.value = true
    localStorage.setItem('token', newToken)
    localStorage.setItem('user', JSON.stringify(userData))
  }
  
  function logout() {
    token.value = ''
    user.value = {}
    isAuthenticated.value = false
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }
  
  return { token, user, isAuthenticated, login, logout }
})