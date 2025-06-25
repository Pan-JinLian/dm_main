# 后端MyBatis映射文件修复指南

## 问题分析

根据数据库结构，发现以下问题：

1. **表名大小写不匹配**：数据库表名是大写（如 `Dormitory`），但MyBatis映射可能使用小写
2. **字段名不匹配**：数据库字段名与实体类字段名不一致
3. **关联查询缺失**：宿舍列表需要关联楼栋和宿管信息

## 需要修复的映射文件

### 1. DormitoryMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mapper.DormitoryMapper">

    <!-- 结果映射 -->
    <resultMap id="DormitoryVoMap" type="com.example.vo.DormitoryVo">
        <id column="id" property="id"/>
        <result column="dormitorymanagerId" property="dormitorymanagerId"/>
        <result column="buildingId" property="buildingId"/>
        <result column="floor" property="floor"/>
        <result column="maxNumber" property="maxNumber"/>
        <result column="livedNumber" property="livedNumber"/>
        <!-- 关联楼栋信息 -->
        <association property="building" javaType="com.example.entity.Building">
            <id column="building_id" property="id"/>
            <result column="building_name" property="name"/>
            <result column="building_type" property="type"/>
        </association>
        <!-- 关联宿管信息 -->
        <association property="dormitoryManager" javaType="com.example.entity.DormitoryManager">
            <id column="manager_id" property="id"/>
            <result column="manager_name" property="name"/>
            <result column="manager_gender" property="gender"/>
            <result column="manager_phone" property="phone"/>
        </association>
    </resultMap>

    <!-- 查询所有宿舍（包含楼栋和宿管信息） -->
    <select id="selectAllDormitories" resultMap="DormitoryVoMap">
        SELECT 
            d.id,
            d.dormitorymanagerId,
            d.buildingId,
            d.floor,
            d.maxNumber,
            d.livedNumber,
            b.id as building_id,
            b.name as building_name,
            b.type as building_type,
            dm.id as manager_id,
            dm.name as manager_name,
            dm.gender as manager_gender,
            dm.phone as manager_phone
        FROM Dormitory d
        LEFT JOIN Building b ON d.buildingId = b.id
        LEFT JOIN DormitoryManager dm ON d.dormitorymanagerId = dm.id
    </select>

    <!-- 根据ID查询宿舍 -->
    <select id="selectById" parameterType="string" resultMap="DormitoryVoMap">
        SELECT 
            d.id,
            d.dormitorymanagerId,
            d.buildingId,
            d.floor,
            d.maxNumber,
            d.livedNumber,
            b.id as building_id,
            b.name as building_name,
            b.type as building_type,
            dm.id as manager_id,
            dm.name as manager_name,
            dm.gender as manager_gender,
            dm.phone as manager_phone
        FROM Dormitory d
        LEFT JOIN Building b ON d.buildingId = b.id
        LEFT JOIN DormitoryManager dm ON d.dormitorymanagerId = dm.id
        WHERE d.id = #{id}
    </select>

    <!-- 插入宿舍 -->
    <insert id="insert" parameterType="com.example.entity.Dormitory">
        INSERT INTO Dormitory (id, dormitorymanagerId, buildingId, floor, maxNumber, livedNumber)
        VALUES (#{id}, #{dormitorymanagerId}, #{buildingId}, #{floor}, #{maxNumber}, #{livedNumber})
    </insert>

    <!-- 更新宿舍 -->
    <update id="update" parameterType="com.example.entity.Dormitory">
        UPDATE Dormitory 
        SET dormitorymanagerId = #{dormitorymanagerId},
            buildingId = #{buildingId},
            floor = #{floor},
            maxNumber = #{maxNumber},
            livedNumber = #{livedNumber}
        WHERE id = #{id}
    </update>

    <!-- 删除宿舍 -->
    <delete id="deleteById" parameterType="string">
        DELETE FROM Dormitory WHERE id = #{id}
    </delete>

</mapper>
```

### 2. LiveMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mapper.LiveMapper">

    <!-- 结果映射 -->
    <resultMap id="LiveVoMap" type="com.example.vo.LiveVo">
        <result column="studentId" property="studentId"/>
        <result column="dormitoryId" property="dormitoryId"/>
        <result column="bed_id" property="bed_id"/>
        <result column="liveInDate" property="liveInDate"/>
        <result column="liveOutDate" property="liveOutDate"/>
        <result column="status" property="status"/>
        <!-- 关联学生信息 -->
        <association property="student" javaType="com.example.entity.Student">
            <id column="student_id" property="id"/>
            <result column="student_name" property="name"/>
            <result column="student_gender" property="gender"/>
            <result column="student_department" property="department"/>
            <result column="student_major" property="major"/>
        </association>
        <!-- 关联宿舍信息 -->
        <association property="dormitory" javaType="com.example.entity.Dormitory">
            <id column="dormitory_id" property="id"/>
            <result column="dormitory_floor" property="floor"/>
            <result column="dormitory_maxNumber" property="maxNumber"/>
            <result column="dormitory_livedNumber" property="livedNumber"/>
            <!-- 嵌套关联楼栋信息 -->
            <association property="building" javaType="com.example.entity.Building">
                <id column="building_id" property="id"/>
                <result column="building_name" property="name"/>
            </association>
        </association>
    </resultMap>

    <!-- 查询所有住宿记录（包含学生和宿舍信息） -->
    <select id="selectAllLives" resultMap="LiveVoMap">
        SELECT 
            l.studentId,
            l.dormitoryId,
            l.bed_id,
            l.liveInDate,
            l.liveOutDate,
            l.status,
            s.id as student_id,
            s.name as student_name,
            s.gender as student_gender,
            s.department as student_department,
            s.major as student_major,
            d.id as dormitory_id,
            d.floor as dormitory_floor,
            d.maxNumber as dormitory_maxNumber,
            d.livedNumber as dormitory_livedNumber,
            b.id as building_id,
            b.name as building_name
        FROM Live l
        LEFT JOIN Student s ON l.studentId = s.id
        LEFT JOIN Dormitory d ON l.dormitoryId = d.id
        LEFT JOIN Building b ON d.buildingId = b.id
    </select>

    <!-- 根据学生ID查询住宿记录 -->
    <select id="selectByStudentId" parameterType="string" resultMap="LiveVoMap">
        SELECT 
            l.studentId,
            l.dormitoryId,
            l.bed_id,
            l.liveInDate,
            l.liveOutDate,
            l.status,
            s.id as student_id,
            s.name as student_name,
            s.gender as student_gender,
            s.department as student_department,
            s.major as student_major,
            d.id as dormitory_id,
            d.floor as dormitory_floor,
            d.maxNumber as dormitory_maxNumber,
            d.livedNumber as dormitory_livedNumber,
            b.id as building_id,
            b.name as building_name
        FROM Live l
        LEFT JOIN Student s ON l.studentId = s.id
        LEFT JOIN Dormitory d ON l.dormitoryId = d.id
        LEFT JOIN Building b ON d.buildingId = b.id
        WHERE l.studentId = #{studentId}
    </select>

    <!-- 插入住宿记录 -->
    <insert id="insert" parameterType="com.example.entity.Live">
        INSERT INTO Live (studentId, dormitoryId, bed_id, liveInDate, liveOutDate, status)
        VALUES (#{studentId}, #{dormitoryId}, #{bed_id}, #{liveInDate}, #{liveOutDate}, #{status})
    </insert>

    <!-- 更新住宿记录 -->
    <update id="update" parameterType="com.example.entity.Live">
        UPDATE Live 
        SET dormitoryId = #{dormitoryId},
            bed_id = #{bed_id},
            liveInDate = #{liveInDate},
            liveOutDate = #{liveOutDate},
            status = #{status}
        WHERE studentId = #{studentId}
    </update>

    <!-- 删除住宿记录 -->
    <delete id="deleteByStudentId" parameterType="string">
        DELETE FROM Live WHERE studentId = #{studentId}
    </delete>

</mapper>
```

### 3. StudentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mapper.StudentMapper">

    <!-- 结果映射 -->
    <resultMap id="StudentMap" type="com.example.entity.Student">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="id_card" property="idCard"/>
        <result column="password" property="password"/>
        <result column="gender" property="gender"/>
        <result column="department" property="department"/>
        <result column="major" property="major"/>
        <result column="grade" property="grade"/>
        <result column="class" property="clazz"/>
        <result column="phone" property="phone"/>
    </resultMap>

    <!-- 查询所有学生 -->
    <select id="selectAll" resultMap="StudentMap">
        SELECT * FROM Student
    </select>

    <!-- 根据ID查询学生 -->
    <select id="selectById" parameterType="string" resultMap="StudentMap">
        SELECT * FROM Student WHERE id = #{id}
    </select>

    <!-- 插入学生 -->
    <insert id="insert" parameterType="com.example.entity.Student">
        INSERT INTO Student (id, name, id_card, password, gender, department, major, grade, class, phone)
        VALUES (#{id}, #{name}, #{idCard}, #{password}, #{gender}, #{department}, #{major}, #{grade}, #{clazz}, #{phone})
    </insert>

    <!-- 更新学生 -->
    <update id="update" parameterType="com.example.entity.Student">
        UPDATE Student 
        SET name = #{name},
            id_card = #{idCard},
            password = #{password},
            gender = #{gender},
            department = #{department},
            major = #{major},
            grade = #{grade},
            class = #{clazz},
            phone = #{phone}
        WHERE id = #{id}
    </update>

    <!-- 删除学生 -->
    <delete id="deleteById" parameterType="string">
        DELETE FROM Student WHERE id = #{id}
    </delete>

</mapper>
```

### 4. BuildingMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mapper.BuildingMapper">

    <!-- 结果映射 -->
    <resultMap id="BuildingMap" type="com.example.entity.Building">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="type" property="type"/>
        <result column="floors" property="floors"/>
        <result column="rooms" property="rooms"/>
        <result column="dormitoryManagerId" property="dormitoryManagerId"/>
    </resultMap>

    <!-- 查询所有楼栋 -->
    <select id="selectAll" resultMap="BuildingMap">
        SELECT * FROM Building
    </select>

    <!-- 根据ID查询楼栋 -->
    <select id="selectById" parameterType="string" resultMap="BuildingMap">
        SELECT * FROM Building WHERE id = #{id}
    </select>

    <!-- 插入楼栋 -->
    <insert id="insert" parameterType="com.example.entity.Building">
        INSERT INTO Building (id, name, type, floors, rooms, dormitoryManagerId)
        VALUES (#{id}, #{name}, #{type}, #{floors}, #{rooms}, #{dormitoryManagerId})
    </insert>

    <!-- 更新楼栋 -->
    <update id="update" parameterType="com.example.entity.Building">
        UPDATE Building 
        SET name = #{name},
            type = #{type},
            floors = #{floors},
            rooms = #{rooms},
            dormitoryManagerId = #{dormitoryManagerId}
        WHERE id = #{id}
    </update>

    <!-- 删除楼栋 -->
    <delete id="deleteById" parameterType="string">
        DELETE FROM Building WHERE id = #{id}
    </delete>

</mapper>
```

### 5. DormitoryManagerMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mapper.DormitoryManagerMapper">

    <!-- 结果映射 -->
    <resultMap id="DormitoryManagerMap" type="com.example.entity.DormitoryManager">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="password" property="password"/>
        <result column="gender" property="gender"/>
        <result column="phone" property="phone"/>
    </resultMap>

    <!-- 查询所有宿管 -->
    <select id="selectAll" resultMap="DormitoryManagerMap">
        SELECT * FROM DormitoryManager
    </select>

    <!-- 根据ID查询宿管 -->
    <select id="selectById" parameterType="string" resultMap="DormitoryManagerMap">
        SELECT * FROM DormitoryManager WHERE id = #{id}
    </select>

    <!-- 插入宿管 -->
    <insert id="insert" parameterType="com.example.entity.DormitoryManager">
        INSERT INTO DormitoryManager (id, name, password, gender, phone)
        VALUES (#{id}, #{name}, #{password}, #{gender}, #{phone})
    </insert>

    <!-- 更新宿管 -->
    <update id="update" parameterType="com.example.entity.DormitoryManager">
        UPDATE DormitoryManager 
        SET name = #{name},
            password = #{password},
            gender = #{gender},
            phone = #{phone}
        WHERE id = #{id}
    </update>

    <!-- 删除宿管 -->
    <delete id="deleteById" parameterType="string">
        DELETE FROM DormitoryManager WHERE id = #{id}
    </delete>

</mapper>
```

## 需要创建的VO类

### 1. DormitoryVo.java

```java
package com.example.vo;

import com.example.entity.Building;
import com.example.entity.DormitoryManager;
import lombok.Data;

@Data
public class DormitoryVo {
    private String id;
    private String dormitorymanagerId;
    private String buildingId;
    private String floor;
    private Integer maxNumber;
    private Integer livedNumber;
    
    // 关联的楼栋信息
    private Building building;
    
    // 关联的宿管信息
    private DormitoryManager dormitoryManager;
    
    // 计算属性
    public String getBuildingName() {
        return building != null ? building.getName() : null;
    }
    
    public String getDormitoryManagerName() {
        return dormitoryManager != null ? dormitoryManager.getName() : null;
    }
}
```

### 2. LiveVo.java

```java
package com.example.vo;

import com.example.entity.Dormitory;
import com.example.entity.Student;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LiveVo {
    private String studentId;
    private String dormitoryId;
    private String bed_id;
    private LocalDate liveInDate;
    private LocalDate liveOutDate;
    private Integer status;
    
    // 关联的学生信息
    private Student student;
    
    // 关联的宿舍信息
    private Dormitory dormitory;
    
    // 计算属性
    public String getStudentName() {
        return student != null ? student.getName() : null;
    }
    
    public String getDormitoryInfo() {
        if (dormitory != null && dormitory.getBuilding() != null) {
            return dormitory.getBuilding().getName() + "-" + dormitory.getId();
        }
        return null;
    }
}
```

## 修复步骤

1. **更新MyBatis映射文件**：使用上述正确的映射文件替换现有的
2. **创建VO类**：添加DormitoryVo和LiveVo类
3. **更新Service层**：确保返回正确的VO对象
4. **重启后端服务**：让修改生效

## 关键修复点

1. **表名使用大写**：`Dormitory`, `Building`, `Student`, `Live`, `DormitoryManager`
2. **字段名匹配**：`dormitorymanagerId`（不是`dormitoryManagerId`），`class`（不是`clazz`）
3. **关联查询**：宿舍列表需要关联楼栋和宿管信息
4. **住宿记录**：需要关联学生和宿舍（包含楼栋）信息

修复这些映射文件后，前端应该能正确显示：
- 宿舍管理的所属楼栋、负责宿管、最大可住人数
- 住宿记录的学生学号、学生姓名和宿舍信息 