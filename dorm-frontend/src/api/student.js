import axios from 'axios'
import { useAuthStore } from '../stores/auth'

const api = axios.create({
  baseURL: 'http://localhost:8080/dorm/student',
})

api.interceptors.request.use(config => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

export default {
  getAllStudents() {
    return api.get('/')
  },
  getStudentById(id) {
    return api.get(`/${id}`)
  },
  createStudent(data) {
    return api.post('/', data)
  },
  updateStudent(id, data) {
    return api.put(`/${id}`, data)
  },
  deleteStudent(id) {
    return api.delete(`/${id}`)
  }
}