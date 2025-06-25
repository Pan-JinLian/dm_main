<template>
  <div class="dormitory-form">
    <h2>{{ isEdit ? '编辑宿舍' : '添加宿舍' }}</h2>
    
    <el-form :model="form" :rules="rules" ref="dormitoryForm" label-width="120px">
      <el-form-item label="所属楼栋" prop="buildingId">
        <el-select v-model="form.buildingId" placeholder="请选择楼栋">
          <el-option
            v-for="building in buildings"
            :key="building.id"
            :label="building.name"
            :value="building.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="房间号" prop="roomNumber">
        <el-input v-model="form.roomNumber"></el-input>
      </el-form-item>
      <el-form-item label="楼层" prop="floor">
        <el-input-number v-model="form.floor" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="容量" prop="capacity">
        <el-input-number v-model="form.capacity" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" type="textarea"></el-input>
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
import dormitoryApi from '../../api/dormitory'
import buildingApi from '../../api/building'

const route = useRoute()
const router = useRouter()
const dormitoryForm = ref(null)
const isEdit = ref(false)
const dormitoryId = ref(null)
const buildings = ref([])

const form = ref({
  id: null,
  buildingId: '',
  roomNumber: '',
  floor: 1,
  capacity: 4,
  description: ''
})

const rules = {
  buildingId: [{ required: true, message: '请选择楼栋', trigger: 'change' }],
  roomNumber: [{ required: true, message: '请输入房间号', trigger: 'blur' }],
  floor: [{ required: true, message: '请输入楼层', trigger: 'blur' }],
  capacity: [{ required: true, message: '请输入容量', trigger: 'blur' }]
}

onMounted(async () => {
  await fetchBuildings()
  
  if (route.params.id) {
    isEdit.value = true
    dormitoryId.value = route.params.id
    await fetchDormitory()
  }
})

const fetchBuildings = async () => {
  try {
    const response = await buildingApi.getAllBuildings()
    buildings.value = response.data
  } catch (error) {
    ElMessage.error('获取楼栋列表失败')
  }
}

const fetchDormitory = async () => {
  try {
    const response = await dormitoryApi.getDormitoryById(dormitoryId.value)
    form.value = response.data
  } catch (error) {
    ElMessage.error('获取宿舍信息失败')
  }
}

const submitForm = () => {
  dormitoryForm.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await dormitoryApi.updateDormitory(form.value)
          ElMessage.success('更新成功')
        } else {
          await dormitoryApi.addDormitory(form.value)
          ElMessage.success('创建成功')
        }
        router.push('/dormitories')
      } catch (error) {
        ElMessage.error(error.response?.data?.message || '操作失败')
      }
    }
  })
}

const goBack = () => {
  router.push('/dormitories')
}
</script>

<style scoped>
.dormitory-form {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}
</style>