<template>
  <div class="student-list">
    <div class="header">
      <h2>学生列表</h2>
      <el-button type="primary" @click="goToAddStudent">添加学生</el-button>
    </div>
    
    <el-table :data="students" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="studentId" label="学号"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="college" label="学院"></el-table-column>
      <el-table-column prop="major" label="专业"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="editStudent(scope.row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteStudent(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import studentApi from '../../api/student'
import { useDormStore } from '../../stores/dorm'

const router = useRouter()
const dormStore = useDormStore()
const students = ref([])

onMounted(async () => {
  await fetchStudents()
})

const fetchStudents = async () => {
  try {
    // 实际项目中调用API
    // const response = await studentApi.getAllStudents()
    // students.value = response.data
    // dormStore.setStudents(response.data)
    
    // 模拟数据
    students.value = [
      { id: 1, studentId: '2023001', name: '张三', gender: '男', college: '计算机学院', major: '计算机科学与技术', phone: '13800138001' },
      { id: 2, studentId: '2023002', name: '李四', gender: '女', college: '文学院', major: '汉语言文学', phone: '13800138002' }
    ]
    dormStore.setStudents(students.value)
  } catch (error) {
    ElMessage.error('获取学生列表失败')
  }
}

const goToAddStudent = () => {
  router.push('/students/add')
}

const editStudent = (id) => {
  router.push(`/students/edit/${id}`)
}

const deleteStudent = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该学生吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    // 实际项目中调用API
    // await studentApi.deleteStudent(id)
    // await fetchStudents()
    
    // 模拟删除
    students.value = students.value.filter(s => s.id !== id)
    dormStore.setStudents(students.value)
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}
</script>

<style scoped>
.student-list {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style>