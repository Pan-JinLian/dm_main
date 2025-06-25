<template>
  <div class="building-form">
    <h2>{{ isEdit ? '编辑楼栋' : '添加楼栋' }}</h2>
    
    <el-form :model="form" :rules="rules" ref="buildingForm" label-width="120px">
      <el-form-item label="楼号" prop="id">
        <el-input v-model="form.id" :disabled="isEdit"></el-input>
      </el-form-item>
      <el-form-item label="楼栋名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="楼类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择楼类型">
          <el-option label="男生" value="男生"></el-option>
          <el-option label="女生" value="女生"></el-option>
          <el-option label="混合" value="混合"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="总层数" prop="floors">
        <el-input-number v-model="form.floors" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="总房间数" prop="rooms">
        <el-input-number v-model="form.rooms" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="宿管号" prop="dormitoryManagerId">
        <el-input v-model="form.dormitoryManagerId"></el-input>
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
import buildingApi from '../../api/building'

const route = useRoute()
const router = useRouter()
const buildingForm = ref(null)
const isEdit = ref(false)

const form = ref({
  id: '',
  name: '',
  type: '',
  floors: 1,
  rooms: 1,
  dormitoryManagerId: ''
})

const rules = {
  id: [{ required: true, message: '请输入楼号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入楼栋名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择楼类型', trigger: 'change' }],
  floors: [{ required: true, message: '请输入总层数', trigger: 'blur' }],
  rooms: [{ required: true, message: '请输入总房间数', trigger: 'blur' }]
}

onMounted(() => {
  if (route.params.id) {
    isEdit.value = true
    fetchBuilding(route.params.id)
  }
})

const fetchBuilding = async (id) => {
  try {
    const response = await buildingApi.getById(id)
    form.value = response.data
  } catch (error) {
    ElMessage.error('获取楼栋信息失败')
  }
}

const submitForm = () => {
  buildingForm.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await buildingApi.update(form.value)
          ElMessage.success('更新成功')
        } else {
          await buildingApi.create(form.value)
          ElMessage.success('创建成功')
        }
        router.push('/buildings')
      } catch (error) {
        ElMessage.error(error.message || '操作失败')
      }
    }
  })
}

const goBack = () => {
  router.push('/buildings')
}
</script>

<style scoped>
.building-form {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}
</style>