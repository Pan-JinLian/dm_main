package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.DormitoryEntity;
import org.apache.ibatis.annotations.Mapper;

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
