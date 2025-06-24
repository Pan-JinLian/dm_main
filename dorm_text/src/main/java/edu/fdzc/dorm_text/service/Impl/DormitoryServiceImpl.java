package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.DormitoryEntity;
import edu.fdzc.dorm_text.mapper.DormitoryMapper;
import edu.fdzc.dorm_text.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DormitoryServiceImpl implements DormitoryService {

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Override
    public boolean addDormitory(DormitoryEntity dormitory) {
        return dormitoryMapper.insert(dormitory) > 0;
    }

    @Override
    public boolean deleteDormitory(String id) {
        return dormitoryMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateDormitory(DormitoryEntity dormitory) {
        return dormitoryMapper.update(dormitory) > 0;
    }

    @Override
    public DormitoryEntity getDormitoryById(String id) {
        return dormitoryMapper.selectById(id);
    }

    @Override
    public List<DormitoryEntity> getAllDormitories() {
        return dormitoryMapper.selectAll();
    }

    @Override
    public List<DormitoryEntity> getDormitoriesByBuildingId(String buildingId) {
        return dormitoryMapper.selectByBuildingId(buildingId);
    }
}