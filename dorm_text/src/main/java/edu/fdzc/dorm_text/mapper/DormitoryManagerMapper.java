package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DormitoryManagerMapper {

    int insert(DormitoryManagerEntity manager);

    int deleteById(String id);

    int update(DormitoryManagerEntity manager);

    DormitoryManagerEntity selectById(String id);

    List<DormitoryManagerEntity> selectAll();
}
