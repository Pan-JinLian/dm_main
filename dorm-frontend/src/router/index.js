import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/auth/Login.vue'
import Register from '../views/auth/Register.vue'
import Dashboard from '../views/DashBoard.vue'
import BuildingList from '../views/building/BuildingList.vue'
import BuildingForm from '../views/building/BuildingForm.vue'
import DormitoryList from '../views/dormitory/DormitoryList.vue'
import DormitoryForm from '../views/dormitory/DormitoryForm.vue'
import ManagerList from '../views/manager/ManagerList.vue'
import ManagerForm from '../views/manager/ManagerForm.vue'
import LiveList from '../views/live/LiveList.vue'
import LiveForm from '../views/live/LiveForm.vue'
import StudentList from '../views/student/StudentList.vue'
import StudentForm from '../views/student/StudentForm.vue'
import { useAuthStore } from '@/stores/auth';

const routes = [
  { 
    path: '/login', 
    component: Login 
  },
  { 
    path: '/register', 
    component: Register 
  },
  { 
    path: '/', 
    component: Dashboard, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/buildings', 
    component: BuildingList, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/buildings/add', 
    component: BuildingForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/buildings/edit/:id', 
    component: BuildingForm,
    meta: { requiresAuth: true } 
  },
  { 
    path: '/dormitories', 
    component: DormitoryList, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/dormitories/add', 
    component: DormitoryForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/dormitories/edit/:id', 
    component: DormitoryForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/managers', 
    component: ManagerList, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/managers/add', 
    component: ManagerForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/managers/edit/:id', 
    component: ManagerForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/lives', 
    component: LiveList, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/lives/add', 
    component: LiveForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/lives/edit/:id', 
    component: LiveForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/students', 
    component: StudentList, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/students/add', 
    component: StudentForm, 
    meta: { requiresAuth: true } 
  },
  { 
    path: '/students/edit/:id', 
    component: StudentForm, 
    meta: { requiresAuth: true } 
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 导航守卫
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})

export default router