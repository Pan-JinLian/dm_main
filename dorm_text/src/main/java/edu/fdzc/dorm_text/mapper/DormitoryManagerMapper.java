package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DormitoryManagerMapper {

    int insert(DormitoryManagerEntity manager);

    int deleteById(String id);

    int update(DormitoryManagerEntity manager);

    DormitoryManagerEntity selectById(String id);

    List<DormitoryManagerEntity> selectAll();

    /**
     * 查询宿管管理的宿舍信息
     */
    Map<String, Object> selectManagedDormitory(@Param("managerId") String managerId);

    /**
     * 查询宿舍的学生列表
     */
    List<Map<String, Object>> selectDormitoryStudents(@Param("dormitoryId") String dormitoryId);

    /**
     * 检查学生是否已经入住其他宿舍
     */
    boolean checkStudentAlreadyInDormitory(@Param("studentId") String studentId);

    /**
     * 检查学生是否在指定宿舍
     */
    boolean checkStudentInDormitory(@Param("studentId") String studentId, @Param("dormitoryId") String dormitoryId);

    /**
     * 插入入住记录
     */
    int insertLiveRecord(@Param("studentId") String studentId, @Param("dormitoryId") String dormitoryId, @Param("checkInTime") String checkInTime);

    /**
     * 更新退宿记录
     */
    int updateLiveRecordCheckOut(@Param("studentId") String studentId, @Param("dormitoryId") String dormitoryId, @Param("checkOutTime") String checkOutTime, @Param("reason") String reason);

    /**
     * 更新宿舍入住人数
     */
    int updateDormitoryLivedNumber(@Param("dormitoryId") String dormitoryId, @Param("change") int change);
}
