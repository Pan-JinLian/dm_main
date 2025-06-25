package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.Live;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LiveMapper {
    // 添加入住信息
    int addLive(Live live);

    // 根据学生学号删除入住信息
    int deleteLiveByStudentId(String studentId);

    // 更新入住信息
    int updateLive(Live live);

    // 根据学生学号查询入住信息
    Live getLiveByStudentId(String studentId);

    // 查询所有入住信息
    List<Live> getAllLives();

    // 查询所有入住信息（包含学生和宿舍信息）
    List<Map<String, Object>> getAllLivesWithDetails();
}