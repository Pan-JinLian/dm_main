package edu.fdzc.dorm_text.service;

import edu.fdzc.dorm_text.entity.DormitoryEntity;
import edu.fdzc.dorm_text.vo.DormitoryVo;

import java.util.List;

public interface DormitoryService {

    /**
     * 添加宿舍
     */
    boolean addDormitory(DormitoryEntity dormitory);

    /**
     * 删除宿舍
     */
    boolean deleteDormitory(String id);

    /**
     * 强制删除宿舍（即使有学生居住）
     */
    boolean forceDeleteDormitory(String id);

    /**
     * 更新宿舍信息
     */
    boolean updateDormitory(DormitoryEntity dormitory);

    /**
     * 根据ID查询宿舍
     */
    DormitoryEntity getDormitoryById(String id);

    /**
     * 查询所有宿舍
     */
    List<DormitoryVo> getAllDormitoriesWithDetails();

    /**
     * 根据楼宇ID查询宿舍
     */
    List<DormitoryEntity> getDormitoriesByBuildingId(String buildingId);
}