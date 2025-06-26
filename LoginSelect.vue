<template>
  <div class="login-select">
    <div class="login-container">
      <div class="login-header">
        <h2>宿舍管理系统</h2>
        <p>请选择登录方式</p>
      </div>
      
      <div class="login-options">
        <el-button 
          type="primary" 
          size="large" 
          class="login-btn admin-btn"
          @click="goToLogin('admin')"
        >
          <el-icon><Setting /></el-icon>
          管理员登录
        </el-button>

        <el-button 
          type="success" 
          size="large" 
          class="login-btn manager-btn"
          @click="goToLogin('manager')"
        >
          <el-icon><User /></el-icon>
          宿管登录
        </el-button>

        <el-button 
          type="info" 
          size="large" 
          class="login-btn student-btn"
          @click="goToLogin('student')"
        >
          <el-icon><UserFilled /></el-icon>
          学生登录
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { Setting, User, UserFilled } from '@element-plus/icons-vue'

const router = useRouter()

const goToLogin = (type) => {
  switch (type) {
    case 'admin':
      router.push('/login')
      break
    case 'manager':
      router.push('/manager/login')
      break
    case 'student':
      router.push('/student/login')
      break
  }
}
</script>

<style scoped>
.login-select {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-container {
  background: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  color: #333;
  margin: 0 0 10px 0;
  font-size: 24px;
  font-weight: 500;
}

.login-header p {
  color: #666;
  font-size: 14px;
  margin: 0;
}

.login-options {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.login-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
}

.login-btn .el-icon {
  margin-right: 8px;
}
</style> 