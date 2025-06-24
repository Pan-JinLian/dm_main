import axios from 'axios'
import { useAuthStore } from '../stores/auth'

const api = axios.create({
  baseURL: 'http://localhost:8080/dorm/dormitory-manager',
})

api.interceptors.request.use(config => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

export default {
  getAllManagers() {
    return api.get('/')
  },
  getManagerById(id) {
    return api.get(`/${id}`)
  },
  createManager(data) {
    return api.post('/', data)
  },
  updateManager(id, data) {
    return api.put(`/${id}`, data)
  },
  deleteManager(id) {
    return api.delete(`/${id}`)
  }
}