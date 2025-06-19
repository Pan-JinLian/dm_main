package com.example.susheguanli.mapper;

import com.example.susheguanli.entity.Building;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuildingMapper {
    @Select("SELECT * FROM building WHERE id = #{id}")
    Building selectById(String id);

    @Select("SELECT * FROM building")
    List<Building> selectAll();

    @Insert("INSERT INTO building(id, name, type, floors, rooms, dormitory_manager_id) " +
            "VALUES(#{id}, #{name}, #{type}, #{floors}, #{rooms}, #{dormitoryManagerId})")
    int insert(Building building);

    @Update("UPDATE building SET name=#{name}, type=#{type}, floors=#{floors}, " +
            "rooms=#{rooms}, dormitory_manager_id=#{dormitoryManagerId} WHERE id=#{id}")
    int update(Building building);

    @Delete("DELETE FROM building WHERE id = #{id}")
    int delete(String id);

    @Select("SELECT * FROM building WHERE type = #{type}")
    List<Building> selectByType(String type);
}