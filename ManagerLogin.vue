<template>
  <div class="manager-login">
    <div class="login-container">
      <div class="login-header">
        <h2>宿管系统登录</h2>
        <p>宿舍管理员专用入口</p>
      </div>
      
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入宿管工号"
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
      
      <div class="login-tips">
        <p>请使用您的宿管工号和密码登录</p>
        <p>系统将从数据库验证您的身份</p>
      </div>
      
      <div class="login-footer">
        <router-link to="/login-select" class="back-link">← 返回登录选择</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import managerApi from '../../api/manager'

const router = useRouter()
const authStore = useAuthStore()
const loginFormRef = ref()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入宿管工号', trigger: 'blur' }
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
    // 调用宿管登录API
    const response = await managerApi.login({
      username: loginForm.username,
      password: loginForm.password
    })
    
    if (response.data && response.data.success) {
      // 登录成功，获取宿管信息
      const managerInfo = response.data.data
      
      // 设置登录状态
      authStore.setToken('manager_token_' + managerInfo.id)
      authStore.setUser({
        id: managerInfo.id,
        username: loginForm.username,
        name: managerInfo.name,
        role: 'manager',
        building: managerInfo.buildingName || '未分配楼栋'
      })
      
      ElMessage.success(`欢迎您，${managerInfo.name}！`)
      
      // 跳转到宿管工作台
      router.push('/manager/dashboard')
      
    } else {
      ElMessage.error(response.data?.message || '登录失败')
    }
    
  } catch (error) {
    console.error('宿管登录错误:', error)
    if (error.response) {
      if (error.response.status === 401) {
        ElMessage.error('用户名或密码错误')
      } else if (error.response.status === 404) {
        ElMessage.error('宿管账号不存在')
      } else {
        ElMessage.error('登录失败: ' + (error.response.data?.message || error.response.statusText))
      }
    } else {
      ElMessage.error('网络错误，请检查网络连接')
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.manager-login {
  min-height: 100vh;
  background: linear-gradient(135deg, #67C23A 0%, #85CE61 100%);
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
  color: #67C23A;
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
  color: #67C23A;
  text-decoration: none;
  font-size: 14px;
}

.back-link:hover {
  text-decoration: underline;
}
</style> 