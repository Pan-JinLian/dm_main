<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>登录</h2>
      <el-form :model="form" :rules="rules" ref="loginForm">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" :loading="loading">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
      <p>还没有账号？<router-link to="/register">立即注册</router-link></p>
        <p><router-link to="/login-select">← 返回登录选择</router-link></p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../../stores/auth'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import adminApi from '../../api/admin.js'

const authStore = useAuthStore()
const router = useRouter()
const loginForm = ref(null)
const loading = ref(false)

const form = ref({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const submitForm = async () => {
  try {
    loading.value = true
    await loginForm.value.validate()
    
    const response = await adminApi.login({
      name: form.value.username,
      password: form.value.password
    })
    
    if (response.data === "登录成功") {
      authStore.login('your-jwt-token', { username: form.value.username })
      router.push('/dashboard')
      ElMessage.success('登录成功')
    } else {
      ElMessage.error('用户名或密码错误')
    }
  } catch (error) {
    if (error.response) {
      if (error.response.status === 401) {
        ElMessage.error('用户名或密码错误')
      } else {
        ElMessage.error('登录失败: ' + (error.response.data.message || error.response.statusText))
      }
    } else {
      ElMessage.error('网络错误: ' + error.message)
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-card {
  width: 400px;
  padding: 20px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.el-button {
  width: 100%;
}

.login-footer {
  text-align: center;
  margin-top: 10px;
}

.login-footer p {
  margin: 5px 0;
}
</style>