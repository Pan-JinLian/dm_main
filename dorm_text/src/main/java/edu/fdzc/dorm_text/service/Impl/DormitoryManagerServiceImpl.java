package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;
import edu.fdzc.dorm_text.mapper.DormitoryManagerMapper;
import edu.fdzc.dorm_text.service.DormitoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DormitoryManagerServiceImpl implements DormitoryManagerService {

    @Autowired
    private DormitoryManagerMapper dormitoryManagerMapper;

    @Override
    public boolean addManager(DormitoryManagerEntity manager) {
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
