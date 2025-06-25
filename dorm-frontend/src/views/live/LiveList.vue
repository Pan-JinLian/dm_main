<template>
  <div class="live-list">
    <div class="header">
      <h2>住宿记录</h2>
      <el-button type="primary" @click="goToAddLive">添加住宿记录</el-button>
    </div>
    
    <el-table :data="lives" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名"></el-table-column>
      <el-table-column prop="dormitoryInfo" label="宿舍信息"></el-table-column>
      <el-table-column prop="checkInDate" label="入住日期"></el-table-column>
      <el-table-column prop="checkOutDate" label="退宿日期"></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="editLive(scope.row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteLive(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import liveApi from '../../api/live'
import { useDormStore } from '../../stores/dorm'

const router = useRouter()
const dormStore = useDormStore()
const lives = ref([])

onMounted(async () => {
  await fetchLives()
})

const fetchLives = async () => {
  try {
    // 实际项目中调用API
    // const response = await liveApi.getAllLives()
    // lives.value = response.data
    // dormStore.setLives(response.data)
    
    // 模拟数据
    lives.value = [
      { 
        id: 1, 
        studentId: 1, 
        studentName: '张三', 
        dormitoryId: 1, 
        dormitoryInfo: '1号楼-101', 
        checkInDate: '2023-01-01', 
        checkOutDate: '' 
      },
      { 
        id: 2, 
        studentId: 2, 
        studentName: '李四', 
        dormitoryId: 1, 
        dormitoryInfo: '1号楼-101', 
        checkInDate: '2023-01-01', 
        checkOutDate: '' 
      }
    ]
    dormStore.setLives(lives.value)
  } catch (error) {
    ElMessage.error('获取住宿记录失败')
  }
}

const goToAddLive = () => {
  router.push('/lives/add')
}

const editLive = (id) => {
  router.push(`/lives/edit/${id}`)
}

const deleteLive = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该住宿记录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    // 实际项目中调用API
    // await liveApi.deleteLive(id)
    // await fetchLives()
    
    // 模拟删除
    lives.value = lives.value.filter(l => l.id !== id)
    dormStore.setLives(lives.value)
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}
</script>

<style scoped>
.live-list {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style>