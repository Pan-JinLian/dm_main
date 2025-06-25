package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.Building;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuildingMapper {

    // 查询所有楼宇信息
    @Select("SELECT * FROM Building")
    List<Building> selectAllBuildings();

    // 根据ID查询楼宇信息
    @Select("SELECT * FROM Building WHERE id = #{id}")
    Building selectBuildingById(String id);

    // 插入新楼宇信息
    @Insert("INSERT INTO Building(id, name, type, floors, rooms, dormitoryManagerId) " +
            "VALUES(#{id}, #{name}, #{type}, #{floors}, #{rooms}, #{dormitoryManagerId})")
    int insertBuilding(Building building);

    // 更新楼宇信息
    @Update("UPDATE Building SET name = #{name}, type = #{type}, floors = #{floors}, " +
            "rooms = #{rooms}, dormitoryManagerId = #{dormitoryManagerId} WHERE id = #{id}")
    int updateBuilding(Building building);

    // 根据ID删除楼宇信息
    @Delete("DELETE FROM Building WHERE id = #{id}")
    int deleteBuildingById(String id);

    // 根据宿管ID查询管理的楼宇
    @Select("SELECT * FROM Building WHERE dormitoryManagerId = #{managerId}")
    List<Building> selectBuildingsByManagerId(String managerId);

    @Select("SELECT * FROM Building WHERE id = #{id}")
    Building selectById(String id);
}