package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    // 根据用户名查询管理员
    @Select("SELECT name, password FROM admin WHERE name = #{name}")
    Admin selectByName(@Param("name") String name);

    // 查询所有管理员
    @Select("SELECT name, password FROM admin")
    List<Admin> selectAll();

    // 插入管理员
    @Insert("INSERT INTO admin(name, password) VALUES(#{name}, #{password})")
    int insert(Admin admin);

    // 更新管理员信息
    @Update("UPDATE admin SET password = #{password} WHERE name = #{name}")
    int update(Admin admin);

    // 根据用户名删除管理员
    @Delete("DELETE FROM admin WHERE name = #{name}")
    int deleteByName(@Param("name") String name);

    // 检查用户名是否存在
    @Select("SELECT COUNT(*) FROM admin WHERE name = #{name}")
    boolean existsByName(@Param("name") String name);
}