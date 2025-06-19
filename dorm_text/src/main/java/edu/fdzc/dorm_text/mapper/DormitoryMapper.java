package sut.edu.zyp.dormitory.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import sut.edu.zyp.dormitory.manage.entity.DormitoryEntity;
import java.util.List;

@Mapper
public interface DormitoryMapper {

    int insert(DormitoryEntity dormitory);

    int deleteById(String id);

    int update(DormitoryEntity dormitory);

    DormitoryEntity selectById(String id);

    List<DormitoryEntity> selectAll();

    List<DormitoryEntity> selectByBuildingId(String buildingId);
}
