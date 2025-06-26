<template>
  <div class="student-login">
    <div class="login-container">
      <div class="login-header">
        <h2>学生系统登录</h2>
        <p>学生宿舍管理入口</p>
      </div>
      
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="login-form">
        <el-form-item prop="studentId">
          <el-input
            v-model="loginForm.studentId"
            placeholder="请输入学号"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            size="large" 
            class="login-btn"
            :loading="loading"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <router-link to="/login-select" class="back-link">← 返回登录选择</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import studentApi from '../../api/student'

const router = useRouter()
const authStore = useAuthStore()
const loginFormRef = ref()
const loading = ref(false)

const loginForm = reactive({
  studentId: '',
  password: ''
})

const rules = {
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate((valid) => {
    if (valid) {
      doLogin()
    }
  })
}

const doLogin = async () => {
  loading.value = true
  
  try {
    // 使用api服务调用登录接口
    const response = await studentApi.login(loginForm.studentId, loginForm.password)
    
    const { token, user } = response.data
    
    // 设置登录状态
    authStore.setToken(token)
    authStore.setUser(user)
    
    ElMessage.success(`欢迎您，${user.name}！`)
    
    // 跳转到学生工作台
    router.push('/student/dashboard')
    
  } catch (error) {
    if (error.response && error.response.status === 401) {
      ElMessage.error('学号或密码错误')
    } else {
      ElMessage.error('登录失败，请稍后重试')
      console.error('登录错误:', error)
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.student-login {
  min-height: 100vh;
  background: linear-gradient(135deg, #409EFF 0%, #66B2FF 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-container {
  background: white;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  color: #409EFF;
  margin-bottom: 8px;
}

.login-header p {
  color: #666;
  font-size: 14px;
}

.login-form {
  margin-bottom: 20px;
}

.login-btn {
  width: 100%;
}

.login-tips {
  text-align: center;
  color: #999;
  font-size: 12px;
  border-top: 1px solid #eee;
  padding-top: 15px;
}

.login-tips p {
  margin: 5px 0;
}

.login-footer {
  text-align: center;
  margin-top: 15px;
}

.back-link {
  color: #409EFF;
  text-decoration: none;
  font-size: 14px;
}

.back-link:hover {
  text-decoration: underline;
}
</style> 