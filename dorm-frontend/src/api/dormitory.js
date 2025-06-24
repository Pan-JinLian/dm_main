import axios from 'axios'
import { useAuthStore } from '../stores/auth'

const api = axios.create({
  baseURL: 'http://localhost:8080/dorm/dormitory',
})

api.interceptors.request.use(config => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

export default {
  getAllDormitories() {
    return api.get('/')
  },
  getDormitoryById(id) {
    return api.get(`/${id}`)
  },
  addDormitory(data) {
    return api.post('/', data)
  },
  updateDormitory(data) {
    return api.put('/', data)
  },
  deleteDormitory(id) {
    return api.delete(`/${id}`)
  },
  getDormitoriesByBuildingId(buildingId) {
    return api.get(`/building/${buildingId}`)
  }
}