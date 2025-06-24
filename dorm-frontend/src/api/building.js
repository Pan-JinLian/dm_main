import axios from 'axios'
import { useAuthStore } from '../stores/auth'

const api = axios.create({
  baseURL: 'http://localhost:8080/dorm/building',
})

api.interceptors.request.use(config => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

export default {
  getAll() {
    return api.get('/')
  },
  getById(id) {
    return api.get(`/${id}`)
  },
  create(building) {
    return api.post('/', building)
  },
  update(building) {
    return api.put('/', building)
  },
  delete(id) {
    return api.delete(`/${id}`)
  },
  getByManagerId(managerId) {
    return api.get(`/manager/${managerId}`)
  }
}