package sut.edu.zyp.dormitory.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sut.edu.zyp.dormitory.manage.entity.DormitoryManagerEntity;
import sut.edu.zyp.dormitory.manage.mapper.DormitoryManagerMapper;
import sut.edu.zyp.dormitory.manage.service.DormitoryManagerService;
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
