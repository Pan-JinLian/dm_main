package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.Building;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuildingMapper {

    // 查询所有楼宇信息
    @Select("SELECT * FROM building")
    List<Building> selectAllBuildings();

    // 根据ID查询楼宇信息
    @Select("SELECT * FROM building WHERE id = #{id}")
    Building selectBuildingById(String id);

    // 插入新楼宇信息
    @Insert("INSERT INTO building(id, name, type, floors, rooms, dormitory_manager_id) " +
            "VALUES(#{id}, #{name}, #{type}, #{floors}, #{rooms}, #{dormitoryManagerId})")
    int insertBuilding(Building building);

    // 更新楼宇信息
    @Update("UPDATE building SET name = #{name}, type = #{type}, floors = #{floors}, " +
            "rooms = #{rooms}, dormitory_manager_id = #{dormitoryManagerId} WHERE id = #{id}")
    int updateBuilding(Building building);

    // 根据ID删除楼宇信息
    @Delete("DELETE FROM building WHERE id = #{id}")
    int deleteBuildingById(String id);

    // 根据宿管ID查询管理的楼宇
    @Select("SELECT * FROM building WHERE dormitory_manager_id = #{managerId}")
    List<Building> selectBuildingsByManagerId(String managerId);
}