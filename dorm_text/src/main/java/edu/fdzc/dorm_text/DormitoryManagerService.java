package sut.edu.zyp.dormitory.manage.service;

import sut.edu.zyp.dormitory.manage.entity.DormitoryManagerEntity;
import java.util.List;

public interface DormitoryManagerService {

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
