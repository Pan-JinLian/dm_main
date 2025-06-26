<template>
  <div class="register-container">
    <el-card class="register-card">
      <h2>注册</h2>
      <el-form :model="form" :rules="rules" ref="registerForm">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" placeholder="确认密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" :loading="loading">注册</el-button>
        </el-form-item>
      </el-form>
      <p>已有账号？<router-link to="/login">立即登录</router-link></p>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import adminApi from '../../api/admin.js'

const router = useRouter()
const registerForm = ref(null)
const loading = ref(false)

const form = ref({
  username: '',
  password: '',
  confirmPassword: ''
})

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.value.password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  confirmPassword: [{ validator: validatePass, trigger: 'blur' }]
}

const submitForm = async () => {
  try {
    loading.value = true
    await registerForm.value.validate()
    
    const response = await adminApi.register({
      name: form.value.username,
      password: form.value.password
    })
    
    if (response.data === "注册成功") {
      ElMessage.success('注册成功')
      router.push('/login')
    } else {
      ElMessage.error(response.data || '注册失败')
    }
  } catch (error) {
    if (error.response) {
      if (error.response.status === 409) {
        ElMessage.error('用户名已存在')
      } else {
        ElMessage.error('注册失败: ' + (error.response.data.message || error.response.statusText))
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
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.register-card {
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
</style>