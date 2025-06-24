<template>
  <nav class="navbar">
    <div class="navbar-brand">
      <router-link to="/">宿舍管理系统</router-link>
    </div>
    <div class="navbar-menu">
      <template v-if="authStore.isAuthenticated">
        <router-link to="/buildings">楼栋管理</router-link>
        <router-link to="/dormitories">宿舍管理</router-link>
        <router-link to="/managers">管理员</router-link>
        <router-link to="/students">学生管理</router-link>
        <router-link to="/lives">住宿管理</router-link>
        <a @click="logout">退出</a>
      </template>
      <template v-else>
        <router-link to="/login">登录</router-link>
        <router-link to="/register">注册</router-link>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const logout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  padding: 1rem;
  background-color: #2c3e50;
  color: white;
}

.navbar-brand a {
  color: white;
  text-decoration: none;
  font-weight: bold;
  font-size: 1.2rem;
}

.navbar-menu {
  display: flex;
  gap: 1rem;
}

.navbar-menu a {
  color: white;
  text-decoration: none;
  cursor: pointer;
}

.navbar-menu a:hover {
  text-decoration: underline;
}
</style>