# 后端启动问题修复总结

## 问题描述

启动后端时出现错误：
```
Mapped Statements collection already contains value for edu.fdzc.dorm_text.mapper.BuildingMapper.selectById
```

## 问题原因

`BuildingMapper.java` 接口中已经使用注解方式定义了 `selectById` 方法：
```java
@Select("SELECT * FROM Building WHERE id = #{id}")
Building selectById(String id);
```

而我们在 `BuildingMapper.xml` 中又重复定义了一次：
```xml
<select id="selectById" parameterType="java.lang.String" resultMap="buildingResultMap">
    SELECT * FROM Building WHERE id = #{id}
</select>
```

这导致了方法重复定义的冲突。

## 修复方案

### 1. 清理BuildingMapper.xml
由于 `BuildingMapper.java` 已经使用注解方式定义了所有方法，我们不需要XML映射文件，所以清空了 `BuildingMapper.xml` 的内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="edu.fdzc.dorm_text.mapper.BuildingMapper">
    <!-- BuildingMapper接口已经使用注解方式定义了所有方法，这里不需要XML映射 -->
</mapper>
```

### 2. 修正BuildingMapper.java中的表名和字段名

**表名修正**：
- 原来：`building`（小写）
- 修正：`Building`（大写，与数据库表名一致）

**字段名修正**：
- 原来：`dormitory_manager_id`（下划线）
- 修正：`dormitoryManagerId`（驼峰，与数据库字段名一致）

### 3. 修正后的BuildingMapper.java

```java
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
```

## 其他Mapper的状态

- **AdminMapper**: 使用注解方式，无XML文件 ✅
- **BuildingMapper**: 使用注解方式，XML文件已清理 ✅
- **DormitoryMapper**: 使用XML方式 ✅
- **DormitoryManagerMapper**: 使用XML方式 ✅
- **StudentMapper**: 使用XML方式 ✅
- **LiveMapper**: 使用XML方式 ✅

## 修复后的效果

1. **解决了方法重复定义冲突**
2. **统一了表名大小写**（使用大写）
3. **统一了字段名格式**（使用驼峰命名）
4. **保持了所有功能的完整性**

## 下一步

现在可以重新启动后端服务，应该不会再出现启动错误了。 