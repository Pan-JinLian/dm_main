<template>
  <div class="dashboard">
    <h1>欢迎使用宿舍管理系统</h1>
    
    <div class="stats">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="stat-item">
              <div class="stat-value">{{ stats.buildings }}</div>
              <div class="stat-label">楼栋数量</div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="stat-item">
              <div class="stat-value">{{ stats.dormitories }}</div>
              <div class="stat-label">宿舍数量</div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="stat-item">
              <div class="stat-value">{{ stats.students }}</div>
              <div class="stat-label">学生数量</div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="stat-item">
              <div class="stat-value">{{ stats.managers }}</div>
              <div class="stat-label">管理员数量</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    
    <div class="recent-activity">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>最近活动</span>
          </div>
        </template>
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in activities"
            :key="index"
            :timestamp="activity.timestamp">
            {{ activity.content }}
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useDormStore } from '../stores/dorm'

const dormStore = useDormStore()

const stats = ref({
  buildings: 0,
  dormitories: 0,
  students: 0,
  managers: 0
})

const activities = ref([
  { timestamp: '2023-05-01 08:00', content: '系统管理员登录系统' },
  { timestamp: '2023-05-01 09:30', content: '添加了新的楼栋: 3号楼' },
  { timestamp: '2023-05-02 10:15', content: '添加了10个新的宿舍' },
  { timestamp: '2023-05-03 14:00', content: '20名学生信息被导入系统' }
])

onMounted(() => {
  // 从store获取统计数据
  stats.value = {
    buildings: dormStore.buildings.length,
    dormitories: dormStore.dormitories.length,
    students: dormStore.students.length,
    managers: dormStore.managers.length
  }
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stats {
  margin: 20px 0;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
}

.stat-label {
  color: #999;
}

.recent-activity {
  margin-top: 20px;
}

.card-header {
  font-weight: bold;
}
</style>