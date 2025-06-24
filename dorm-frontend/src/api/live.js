import axios from 'axios'
import { useAuthStore } from '../stores/auth'

const api = axios.create({
  baseURL: 'http://localhost:8080/dorm/live',
})

api.interceptors.request.use(config => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

export default {
  getAllLives() {
    return api.get('/')
  },
  getLiveById(id) {
    return api.get(`/${id}`)
  },
  createLive(data) {
    return api.post('/', data)
  },
  updateLive(id, data) {
    return api.put(`/${id}`, data)
  },
  deleteLive(id) {
    return api.delete(`/${id}`)
  },
  getLivesByStudent(studentId) {
    return api.get(`/student/${studentId}`)
  },
  getLivesByDormitory(dormitoryId) {
    return api.get(`/dormitory/${dormitoryId}`)
  }
}