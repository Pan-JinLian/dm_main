package sut.edu.zyp.dormitory.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import sut.edu.zyp.dormitory.manage.entity.DormitoryManagerEntity;
import java.util.List;

@Mapper
public interface DormitoryManagerMapper {

    int insert(DormitoryManagerEntity manager);

    int deleteById(String id);

    int update(DormitoryManagerEntity manager);

    DormitoryManagerEntity selectById(String id);

    List<DormitoryManagerEntity> selectAll();
}
