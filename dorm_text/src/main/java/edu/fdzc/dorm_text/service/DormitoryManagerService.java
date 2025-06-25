package edu.fdzc.dorm_text.service;

import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;

import java.util.List;
import java.util.Map;

public interface DormitoryManagerService {

    /**
     * 宿管登录
     */
    DormitoryManagerEntity login(String name, String password);

    /**
     * 获取宿管管理的宿舍信息
     */
    Map<String, Object> getManagedDormitory(String managerId);

    /**
     * 获取宿舍的学生列表
     */
    List<Map<String, Object>> getDormitoryStudents(String dormitoryId);

    /**
     * 学生入住登记
     */
    boolean checkInStudent(Map<String, String> checkInData);

    /**
     * 学生退宿登记
     */
    boolean checkOutStudent(Map<String, String> checkOutData);

    /**
     * 获取宿舍统计信息
     */
    Map<String, Object> getDormitoryStats(String dormitoryId);

    /**
     * 提交维修申请
     */
    boolean submitMaintenanceRequest(Map<String, String> maintenanceData);

    /**
     * 获取宿舍维修记录
     */
    List<Map<String, Object>> getMaintenanceRecords(String dormitoryId);

    /**
     * 添加宿管
     */
    boolean addManager(DormitoryManagerEntity manager);

    /**
     * 删除宿管
     */
    boolean deleteManager(String id);

    /**
     * 更新宿管信息
     */
    boolean updateManager(DormitoryManagerEntity manager);

    /**
     * 根据ID查询宿管
     */
    DormitoryManagerEntity getManagerById(String id);

    /**
     * 查询所有宿管
     */
    List<DormitoryManagerEntity> getAllManagers();
}
