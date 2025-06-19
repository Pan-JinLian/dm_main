package sut.edu.zyp.dormitory.manage.service;

import sut.edu.zyp.dormitory.manage.entity.DormitoryEntity;
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
    List<DormitoryEntity> getAllDormitories();

    /**
     * 根据楼宇ID查询宿舍
     */
    List<DormitoryEntity> getDormitoriesByBuildingId(String buildingId);
}