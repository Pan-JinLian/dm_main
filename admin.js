import axios from 'axios'
import { useAuthStore } from '../stores/auth'

const api = axios.create({
  baseURL: 'http://localhost:8080/dorm/admin',
})

api.interceptors.request.use(config => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

export default {
  login(data) {
    return api.post('/login', data)
  },
  
  register(data) {
    return api.post('/register', data)
  },
  
  updateAdmin(data) {
    return api.put('/', data)
  },
  
  deleteAdmin(name) {
    return api.delete(`/${name}`)
  },
  
  getAllAdmins() {
    return api.get('/')
  },
  
  getAdminByName(name) {
    return api.get(`/${name}`)
  }
}