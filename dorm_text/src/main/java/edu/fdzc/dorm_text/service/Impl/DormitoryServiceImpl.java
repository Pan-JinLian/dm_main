package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.Building;
import edu.fdzc.dorm_text.entity.DormitoryEntity;
import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;
import edu.fdzc.dorm_text.mapper.BuildingMapper;
import edu.fdzc.dorm_text.mapper.DormitoryManagerMapper;
import edu.fdzc.dorm_text.mapper.DormitoryMapper;
import edu.fdzc.dorm_text.service.DormitoryService;
import edu.fdzc.dorm_text.vo.DormitoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DormitoryServiceImpl implements DormitoryService {

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private DormitoryManagerMapper dormitoryManagerMapper;

    @Override
    public boolean addDormitory(DormitoryEntity dormitory) {
        // 检查宿舍ID是否已存在
        if (dormitoryMapper.selectById(dormitory.getId()) != null) {
            throw new RuntimeException("宿舍ID已存在");
        }

        // 检查关联的楼栋是否存在
        Building building = buildingMapper.selectById(dormitory.getBuildingId());
        if (building == null) {
            throw new RuntimeException("关联的楼栋不存在");
        }

        // 检查关联的宿管是否存在
        if (dormitory.getDormitorymanagerId() != null &&
                !dormitory.getDormitorymanagerId().isEmpty()) {
            DormitoryManagerEntity manager = dormitoryManagerMapper.selectById(dormitory.getDormitorymanagerId());
            if (manager == null) {
                throw new RuntimeException("关联的宿管不存在");
            }
        }

        return dormitoryMapper.insert(dormitory) > 0;
    }

    @Override
    public boolean deleteDormitory(String id) {
        // 检查宿舍是否存在
        DormitoryEntity dormitory = dormitoryMapper.selectById(id);
        if (dormitory == null) {
            throw new RuntimeException("宿舍不存在");
        }

        // 检查宿舍是否还有学生居住
        if (dormitory.getLivedNumber() > 0) {
            throw new RuntimeException("宿舍仍有学生居住，不能删除。请先让学生退宿，或使用强制删除功能。");
        }

        return dormitoryMapper.deleteById(id) > 0;
    }

    @Override
    public boolean forceDeleteDormitory(String id) {
        // 检查宿舍是否存在
        DormitoryEntity dormitory = dormitoryMapper.selectById(id);
        if (dormitory == null) {
            throw new RuntimeException("宿舍不存在");
        }

        // 强制删除，不检查是否有学生居住
        return dormitoryMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateDormitory(DormitoryEntity dormitory) {
        // 检查宿舍是否存在
        if (dormitoryMapper.selectById(dormitory.getId()) == null) {
            throw new RuntimeException("宿舍不存在");
        }

        // 检查关联的楼栋是否存在
        Building building = buildingMapper.selectById(dormitory.getBuildingId());
        if (building == null) {
            throw new RuntimeException("关联的楼栋不存在");
        }

        // 检查关联的宿管是否存在
        if (dormitory.getDormitorymanagerId() != null &&
                !dormitory.getDormitorymanagerId().isEmpty()) {
            DormitoryManagerEntity manager = dormitoryManagerMapper.selectById(dormitory.getDormitorymanagerId());
            if (manager == null) {
                throw new RuntimeException("关联的宿管不存在");
            }
        }

        return dormitoryMapper.update(dormitory) > 0;
    }

    @Override
    public DormitoryEntity getDormitoryById(String id) {
        return dormitoryMapper.selectById(id);
    }

    @Override
    public List<DormitoryVo> getAllDormitoriesWithDetails() {
        // 使用新的关联查询方法，一次性获取所有信息
        return dormitoryMapper.selectAllWithDetails();
    }

    @Override
    public List<DormitoryEntity> getDormitoriesByBuildingId(String buildingId) {
        return dormitoryMapper.selectByBuildingId(buildingId);
    }
}