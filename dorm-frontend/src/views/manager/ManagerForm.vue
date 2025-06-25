<template>
  <div class="manager-form">
    <h2>{{ isEdit ? '编辑管理员' : '添加管理员' }}</h2>
    
    <el-form :model="form" :rules="rules" ref="managerForm" label-width="120px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" v-if="!isEdit">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword" v-if="!isEdit">
        <el-input v-model="form.confirmPassword" type="password"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import managerApi from '../../api/manager'

const route = useRoute()
const router = useRouter()
const managerForm = ref(null)
const isEdit = ref(false)
const managerId = ref(null)

const form = ref({
  username: '',
  password: '',
  confirmPassword: '',
  name: '',
  phone: '',
  email: ''
})

const validatePass = (rule, value, callback) => {
  if (!isEdit.value && value === '') {
    callback(new Error('请再次输入密码'))
  } else if (!isEdit.value && value !== form.value.password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: !isEdit.value, message: '请输入密码', trigger: 'blur' }],
  confirmPassword: [{ validator: validatePass, trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
  ]
}

onMounted(() => {
  if (route.params.id) {
    isEdit.value = true
    managerId.value = route.params.id
    fetchManager()
  }
})

const fetchManager = async () => {
  try {
    // 实际项目中调用API
    // const response = await managerApi.getManagerById(managerId.value)
    // form.value = response.data
    
    // 模拟数据
    form.value = {
      username: 'admin1',
      name: '张管理员',
      phone: '13800138001',
      email: 'admin1@example.com'
    }
  } catch (error) {
    ElMessage.error('获取管理员信息失败')
  }
}

const submitForm = () => {
  managerForm.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          // 实际项目中调用API
          // await managerApi.updateManager(managerId.value, form.value)
          
          // 模拟更新
          ElMessage.success('更新成功')
        } else {
          // 实际项目中调用API
          // await managerApi.createManager(form.value)
          
          // 模拟创建
          ElMessage.success('创建成功')
        }
        router.push('/managers')
      } catch (error) {
        ElMessage.error(error.message || '操作失败')
      }
    }
  })
}

const goBack = () => {
  router.push('/managers')
}
</script>

<style scoped>
.manager-form {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}
</style>