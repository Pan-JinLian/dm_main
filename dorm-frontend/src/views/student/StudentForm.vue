<template>
  <div class="student-form">
    <h2>{{ isEdit ? '编辑学生' : '添加学生' }}</h2>
    
    <el-form :model="form" :rules="rules" ref="studentForm" label-width="120px">
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="form.studentId"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="form.gender">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="学院" prop="college">
        <el-input v-model="form.college"></el-input>
      </el-form-item>
      <el-form-item label="专业" prop="major">
        <el-input v-model="form.major"></el-input>
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
import studentApi from '../../api/student'

const route = useRoute()
const router = useRouter()
const studentForm = ref(null)
const isEdit = ref(false)
const studentId = ref(null)

const form = ref({
  studentId: '',
  name: '',
  gender: '男',
  college: '',
  major: '',
  phone: '',
  email: ''
})

const rules = {
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  college: [{ required: true, message: '请输入学院', trigger: 'blur' }],
  major: [{ required: true, message: '请输入专业', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
  ]
}

onMounted(() => {
  if (route.params.id) {
    isEdit.value = true
    studentId.value = route.params.id
    fetchStudent()
  }
})

const fetchStudent = async () => {
  try {
    // 实际项目中调用API
    // const response = await studentApi.getStudentById(studentId.value)
    // form.value = response.data
    
    // 模拟数据
    form.value = {
      studentId: '2023001',
      name: '张三',
      gender: '男',
      college: '计算机学院',
      major: '计算机科学与技术',
      phone: '13800138001',
      email: 'student1@example.com'
    }
  } catch (error) {
    ElMessage.error('获取学生信息失败')
  }
}

const submitForm = () => {
  studentForm.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          // 实际项目中调用API
          // await studentApi.updateStudent(studentId.value, form.value)
          
          // 模拟更新
          ElMessage.success('更新成功')
        } else {
          // 实际项目中调用API
          // await studentApi.createStudent(form.value)
          
          // 模拟创建
          ElMessage.success('创建成功')
        }
        router.push('/students')
      } catch (error) {
        ElMessage.error(error.message || '操作失败')
      }
    }
  })
}

const goBack = () => {
  router.push('/students')
}
</script>

<style scoped>
.student-form {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}
</style>