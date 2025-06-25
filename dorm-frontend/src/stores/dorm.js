import { defineStore } from 'pinia'
import { ref } from 'vue'

//宿舍管理
export const useDormStore = defineStore('dorm', () => {
  const buildings = ref([])
  const dormitories = ref([])
  const managers = ref([])
  const students = ref([])
  const lives = ref([])
  
  function setBuildings(data) {
    buildings.value = data
  }
  
  function setDormitories(data) {
    dormitories.value = data
  }
  
  function setManagers(data) {
    managers.value = data
  }
  
  function setStudents(data) {
    students.value = data
  }
  
  function setLives(data) {
    lives.value = data
  }
  
  return {
    buildings,
    dormitories,
    managers,
    students,
    lives,
    setBuildings,
    setDormitories,
    setManagers,
    setStudents,
    setLives
  }
})