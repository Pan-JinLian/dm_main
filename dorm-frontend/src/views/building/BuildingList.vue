<template>
  <div class="building-list">
    <div class="header">
      <h2>楼栋列表</h2>
      <el-button type="primary" @click="goToAddBuilding">添加楼栋</el-button>
    </div>
    
    <el-table :data="buildings" border style="width: 100%">
      <el-table-column prop="id" label="楼号" width="120"></el-table-column>
      <el-table-column prop="name" label="楼栋名称"></el-table-column>
      <el-table-column prop="type" label="类型" width="100"></el-table-column>
      <el-table-column prop="floors" label="层数" width="80"></el-table-column>
      <el-table-column prop="rooms" label="房间数" width="100"></el-table-column>
      <el-table-column prop="dormitoryManagerId" label="宿管号"></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="editBuilding(scope.row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteBuilding(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import buildingApi from '../../api/building'

const router = useRouter()
const buildings = ref([])

onMounted(async () => {
  await fetchBuildings()
})

const fetchBuildings = async () => {
  try {
    const response = await buildingApi.getAll()
    buildings.value = response.data
  } catch (error) {
    ElMessage.error('获取楼栋列表失败')
  }
}

const goToAddBuilding = () => {
  router.push('/buildings/add')
}

const editBuilding = (id) => {
  router.push(`/buildings/edit/${id}`)
}

const deleteBuilding = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该楼栋吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await buildingApi.delete(id)
    await fetchBuildings()
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}
</script>

<style scoped>
.building-list {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style>