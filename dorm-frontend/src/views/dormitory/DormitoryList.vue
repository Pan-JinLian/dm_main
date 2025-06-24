<template>
  <div class="dormitory-list">
    <div class="header">
      <h2>宿舍列表</h2>
      <el-button type="primary" @click="goToAddDormitory">添加宿舍</el-button>
    </div>
    
    <el-table :data="dormitories" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="buildingName" label="所属楼栋"></el-table-column>
      <el-table-column prop="floor" label="楼层"></el-table-column>
      <el-table-column prop="maxNumber" label="最大容量"></el-table-column>
      <el-table-column prop="livedNumber" label="已住人数"></el-table-column>
      <el-table-column label="剩余床位">
        <template #default="scope">
          {{ scope.row.maxNumber - scope.row.livedNumber }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="editDormitory(scope.row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteDormitory(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import dormitoryApi from '../../api/dormitory'

const router = useRouter()
const dormitories = ref([])

onMounted(async () => {
  await fetchDormitories()
})

const fetchDormitories = async () => {
  try {
    const response = await dormitoryApi.getAllDormitories()
    dormitories.value = response.data.map(dorm => ({
      ...dorm,
      buildingName: dorm.building?.name || '未知楼栋'
    }))
  } catch (error) {
    ElMessage.error('获取宿舍列表失败')
  }
}

const goToAddDormitory = () => {
  router.push('/dormitories/add')
}

const editDormitory = (id) => {
  router.push(`/dormitories/edit/${id}`)
}

const deleteDormitory = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该宿舍吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await dormitoryApi.deleteDormitory(id)
    await fetchDormitories()
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.response?.data?.message || '删除失败')
    }
  }
}
</script>

<style scoped>
.dormitory-list {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style>