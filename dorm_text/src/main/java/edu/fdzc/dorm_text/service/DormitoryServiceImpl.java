package sut.edu.zyp.dormitory.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sut.edu.zyp.dormitory.manage.entity.DormitoryEntity;
import sut.edu.zyp.dormitory.manage.mapper.DormitoryMapper;
import sut.edu.zyp.dormitory.manage.service.DormitoryService;
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