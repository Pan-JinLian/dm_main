package edu.fdzc.dorm_text.mapper;

import edu.fdzc.dorm_text.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    // 添加学生
    int addStudent(Student student);

    // 根据学号删除学生
    int deleteStudentById(String id);

    // 更新学生信息
    int updateStudent(Student student);

    // 根据学号查询学生
    Student getStudentById(String id);

    // 查询所有学生
    List<Student> getAllStudents();
}