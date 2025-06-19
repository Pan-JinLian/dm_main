package com.example.susheguanli.mapper;

import com.example.susheguanli.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE name = #{name}")
    Admin selectByName(String name);

    @Insert("INSERT INTO admin(name, password) VALUES(#{name}, #{password})")
    @Options(useGeneratedKeys = false, keyProperty = "name")
    int insert(Admin admin);

    @Update("UPDATE admin SET password = #{password} WHERE name = #{name}")
    int update(Admin admin);

    @Delete("DELETE FROM admin WHERE name = #{name}")
    int delete(String name);
}