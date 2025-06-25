package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.DormitoryEntity;
import edu.fdzc.dorm_text.vo.DormitoryVo;
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

    // 查询所有宿舍（包含楼栋和宿管信息）
    List<DormitoryVo> selectAllWithDetails();
}
