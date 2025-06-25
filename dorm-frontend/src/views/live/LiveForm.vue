<template>
  <div class="live-form">
    <h2>{{ isEdit ? '编辑住宿记录' : '添加住宿记录' }}</h2>
    
    <el-form :model="form" :rules="rules" ref="liveForm" label-width="120px">
      <el-form-item label="学生" prop="studentId">
        <el-select v-model="form.studentId" placeholder="请选择学生">
          <el-option
            v-for="student in students"
            :key="student.id"
            :label="student.name"
            :value="student.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="宿舍" prop="dormitoryId">
        <el-select v-model="form.dormitoryId" placeholder="请选择宿舍">
          <el-option
            v-for="dormitory in dormitories"
            :key="dormitory.id"
            :label="`${dormitory.buildingName}-${dormitory.roomNumber}`"
            :value="dormitory.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入住日期" prop="checkInDate">
        <el-date-picker v-model="form.checkInDate" type="date" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="退宿日期" prop="checkOutDate">
        <el-date-picker v-model="form.checkOutDate" type="date" placeholder="选择日期"></el-date-picker>
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
import liveApi from '../../api/live'
import studentApi from '../../api/student'
import dormitoryApi from '../../api/dormitory'
import { useDormStore } from '../../stores/dorm'

const route = useRoute()
const router = useRouter()
const dormStore = useDormStore()
const liveForm = ref(null)
const isEdit = ref(false)
const liveId = ref(null)
const students = ref([])
const dormitories = ref([])

const form = ref({
  studentId: '',
  dormitoryId: '',
  checkInDate: '',
  checkOutDate: ''
})

const rules = {
  studentId: [{ required: true, message: '请选择学生', trigger: 'change' }],
  dormitoryId: [{ required: true, message: '请选择宿舍', trigger: 'change' }],
  checkInDate: [{ required: true, message: '请选择入住日期', trigger: 'change' }]
}

onMounted(async () => {
  await fetchStudents()
  await fetchDormitories()
  
  if (route.params.id) {
    isEdit.value = true
    liveId.value = route.params.id
    await fetchLive()
  }
})

const fetchStudents = async () => {
  try {
    // 实际项目中调用API
    // const response = await studentApi.getAllStudents()
    // students.value = response.data
    
    // 模拟数据
    students.value = [
      { id: 1, name: '张三', studentId: '2023001' },
      { id: 2, name: '李四', studentId: '2023002' }
    ]
  } catch (error) {
    ElMessage.error('获取学生列表失败')
  }
}

const fetchDormitories = async () => {
  try {
    // 实际项目中调用API
    // const response = await dormitoryApi.getAllDormitories()
    // dormitories.value = response.data
    
    // 模拟数据
    dormitories.value = [
      { id: 1, buildingId: 1, buildingName: '1号楼', roomNumber: '101' },
      { id: 2, buildingId: 1, buildingName: '1号楼', roomNumber: '102' }
    ]
  } catch (error) {
    ElMessage.error('获取宿舍列表失败')
  }
}

const fetchLive = async () => {
  try {
    // 实际项目中调用API
    // const response = await liveApi.getLiveById(liveId.value)
    // form.value = response.data
    
    // 模拟数据
    form.value = {
      studentId: 1,
      dormitoryId: 1,
      checkInDate: '2023-01-01',
      checkOutDate: ''
    }
  } catch (error) {
    ElMessage.error('获取住宿记录失败')
  }
}

const submitForm = () => {
  liveForm.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          // 实际项目中调用API
          // await liveApi.updateLive(liveId.value, form.value)
          
          // 模拟更新
          ElMessage.success('更新成功')
        } else {
          // 实际项目中调用API
          // await liveApi.createLive(form.value)
          
          // 模拟创建
          ElMessage.success('创建成功')
        }
        router.push('/lives')
      } catch (error) {
        ElMessage.error(error.message || '操作失败')
      }
    }
  })
}

const goBack = () => {
  router.push('/lives')
}
</script>

<style scoped>
.live-form {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}
</style>