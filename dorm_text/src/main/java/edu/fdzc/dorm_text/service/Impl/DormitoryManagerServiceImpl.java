package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;
import edu.fdzc.dorm_text.mapper.DormitoryManagerMapper;
import edu.fdzc.dorm_text.service.DormitoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DormitoryManagerServiceImpl implements DormitoryManagerService {

    @Autowired
    private DormitoryManagerMapper dormitoryManagerMapper;

    @Override
    public DormitoryManagerEntity login(String name, String password) {
        System.out.println("验证宿管登录: name=" + name + ", password=" + password);
        
        // 根据name查询宿管
        DormitoryManagerEntity manager = dormitoryManagerMapper.selectById(name);
        
        if (manager != null) {
            System.out.println("找到宿管: " + manager.getName() + ", 数据库密码: " + manager.getPassword());
            
            // 直接比较明文密码（与管理员登录一致）
            if (manager.getPassword().equals(password)) {
                System.out.println("密码匹配成功");
                return manager;
            } else {
                System.out.println("密码不匹配: 输入=" + password + ", 数据库=" + manager.getPassword());
            }
        } else {
            System.out.println("未找到宿管: " + name);
        }
        
        return null;
    }

    @Override
    public Map<String, Object> getManagedDormitory(String managerId) {
        try {
            // 查询宿管管理的宿舍信息
            Map<String, Object> dormitoryInfo = dormitoryManagerMapper.selectManagedDormitory(managerId);
            if (dormitoryInfo != null) {
                System.out.println("找到宿管管理的宿舍: " + dormitoryInfo);
            } else {
                System.out.println("未找到宿管管理的宿舍: " + managerId);
            }
            return dormitoryInfo;
        } catch (Exception e) {
            System.out.println("获取宿管宿舍信息失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getDormitoryStudents(String dormitoryId) {
        try {
            // 查询宿舍的学生列表
            List<Map<String, Object>> students = dormitoryManagerMapper.selectDormitoryStudents(dormitoryId);
            System.out.println("宿舍 " + dormitoryId + " 的学生数量: " + (students != null ? students.size() : 0));
            return students != null ? students : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("获取宿舍学生列表失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean checkInStudent(Map<String, String> checkInData) {
        try {
            String studentId = checkInData.get("studentId");
            String dormitoryId = checkInData.get("dormitoryId");
            String checkInTime = checkInData.get("checkInTime");
            
            System.out.println("学生入住登记: 学号=" + studentId + ", 宿舍=" + dormitoryId + ", 时间=" + checkInTime);
            
            // 检查学生是否已经入住
            boolean alreadyCheckedIn = dormitoryManagerMapper.checkStudentAlreadyInDormitory(studentId);
            if (alreadyCheckedIn) {
                System.out.println("学生 " + studentId + " 已经入住其他宿舍");
                return false;
            }
            
            // 检查宿舍是否有空床位
            Map<String, Object> dormitoryInfo = dormitoryManagerMapper.selectManagedDormitory(dormitoryId);
            if (dormitoryInfo != null) {
                int maxNumber = (Integer) dormitoryInfo.get("maxNumber");
                int livedNumber = (Integer) dormitoryInfo.get("livedNumber");
                
                if (livedNumber >= maxNumber) {
                    System.out.println("宿舍 " + dormitoryId + " 已满，无法入住");
                    return false;
                }
            }
            
            // 执行入住登记
            int result = dormitoryManagerMapper.insertLiveRecord(studentId, dormitoryId, checkInTime);
            if (result > 0) {
                // 更新宿舍入住人数
                dormitoryManagerMapper.updateDormitoryLivedNumber(dormitoryId, 1);
                System.out.println("学生入住登记成功");
                return true;
            } else {
                System.out.println("学生入住登记失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("学生入住登记异常: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean checkOutStudent(Map<String, String> checkOutData) {
        try {
            String studentId = checkOutData.get("studentId");
            String dormitoryId = checkOutData.get("dormitoryId");
            String checkOutTime = checkOutData.get("checkOutTime");
            String reason = checkOutData.get("reason");
            
            System.out.println("学生退宿登记: 学号=" + studentId + ", 宿舍=" + dormitoryId + ", 时间=" + checkOutTime);
            
            // 检查学生是否在该宿舍
            boolean isInDormitory = dormitoryManagerMapper.checkStudentInDormitory(studentId, dormitoryId);
            if (!isInDormitory) {
                System.out.println("学生 " + studentId + " 不在宿舍 " + dormitoryId + " 中");
                return false;
            }
            
            // 执行退宿登记
            int result = dormitoryManagerMapper.updateLiveRecordCheckOut(studentId, dormitoryId, checkOutTime, reason);
            if (result > 0) {
                // 更新宿舍入住人数
                dormitoryManagerMapper.updateDormitoryLivedNumber(dormitoryId, -1);
                System.out.println("学生退宿登记成功");
                return true;
            } else {
                System.out.println("学生退宿登记失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("学生退宿登记异常: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, Object> getDormitoryStats(String dormitoryId) {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // 获取宿舍基本信息
            Map<String, Object> dormitoryInfo = dormitoryManagerMapper.selectManagedDormitory(dormitoryId);
            if (dormitoryInfo != null) {
                stats.put("dormitoryName", dormitoryInfo.get("id"));
                stats.put("maxNumber", dormitoryInfo.get("maxNumber"));
                stats.put("livedNumber", dormitoryInfo.get("livedNumber"));
                
                int maxNumber = (Integer) dormitoryInfo.get("maxNumber");
                int livedNumber = (Integer) dormitoryInfo.get("livedNumber");
                stats.put("availableBeds", maxNumber - livedNumber);
                stats.put("totalStudents", livedNumber);
            } else {
                stats.put("dormitoryName", "未知");
                stats.put("maxNumber", 0);
                stats.put("livedNumber", 0);
                stats.put("availableBeds", 0);
                stats.put("totalStudents", 0);
            }
            
            // 获取维修申请数量（暂时设为0）
            stats.put("maintenanceCount", 0);
            
            System.out.println("宿舍统计信息: " + stats);
            return stats;
        } catch (Exception e) {
            System.out.println("获取宿舍统计信息失败: " + e.getMessage());
            Map<String, Object> stats = new HashMap<>();
            stats.put("dormitoryName", "未知");
            stats.put("maxNumber", 0);
            stats.put("livedNumber", 0);
            stats.put("availableBeds", 0);
            stats.put("totalStudents", 0);
            stats.put("maintenanceCount", 0);
            return stats;
        }
    }

    @Override
    public boolean submitMaintenanceRequest(Map<String, String> maintenanceData) {
        try {
            String dormitoryId = maintenanceData.get("dormitoryId");
            String type = maintenanceData.get("type");
            String description = maintenanceData.get("description");
            String priority = maintenanceData.get("priority");
            
            System.out.println("提交维修申请: 宿舍=" + dormitoryId + ", 类型=" + type + ", 描述=" + description);
            
            // 这里可以调用维修记录的Mapper来保存维修申请
            // 暂时返回成功
            System.out.println("维修申请提交成功");
            return true;
        } catch (Exception e) {
            System.out.println("维修申请提交异常: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getMaintenanceRecords(String dormitoryId) {
        try {
            // 这里可以调用维修记录的Mapper来获取维修记录
            // 暂时返回空列表
            System.out.println("获取维修记录: 宿舍=" + dormitoryId);
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("获取维修记录失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addManager(DormitoryManagerEntity manager) {
        // 直接保存明文密码（与管理员登录一致）
        return dormitoryManagerMapper.insert(manager) > 0;
    }

    @Override
    public boolean deleteManager(String id) {
        return dormitoryManagerMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateManager(DormitoryManagerEntity manager) {
        return dormitoryManagerMapper.update(manager) > 0;
    }

    @Override
    public DormitoryManagerEntity getManagerById(String id) {
        return dormitoryManagerMapper.selectById(id);
    }

    @Override
    public List<DormitoryManagerEntity> getAllManagers() {
        return dormitoryManagerMapper.selectAll();
    }
}
