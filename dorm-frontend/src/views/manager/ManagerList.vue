<template>
  <div class="manager-list">
    <div class="header">
      <h2>管理员列表</h2>
      <el-button type="primary" @click="goToAddManager">添加管理员</el-button>
    </div>
    
    <el-table :data="managers" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="editManager(scope.row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteManager(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import managerApi from '../../api/manager'
import { useDormStore } from '../../stores/dorm'

const router = useRouter()
const dormStore = useDormStore()
const managers = ref([])

onMounted(async () => {
  await fetchManagers()
})

const fetchManagers = async () => {
  try {
    // 实际项目中调用API
    // const response = await managerApi.getAllManagers()
    // managers.value = response.data
    // dormStore.setManagers(response.data)
    
    // 模拟数据
    managers.value = [
      { id: 1, username: 'admin1', name: '张管理员', phone: '13800138001', email: 'admin1@example.com' },
      { id: 2, username: 'admin2', name: '李管理员', phone: '13800138002', email: 'admin2@example.com' }
    ]
    dormStore.setManagers(managers.value)
  } catch (error) {
    ElMessage.error('获取管理员列表失败')
  }
}

const goToAddManager = () => {
  router.push('/managers/add')
}

const editManager = (id) => {
  router.push(`/managers/edit/${id}`)
}

const deleteManager = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该管理员吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    // 实际项目中调用API
    // await managerApi.deleteManager(id)
    // await fetchManagers()
    
    // 模拟删除
    managers.value = managers.value.filter(m => m.id !== id)
    dormStore.setManagers(managers.value)
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}
</script>

<style scoped>
.manager-list {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style>