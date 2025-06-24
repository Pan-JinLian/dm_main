package edu.fdzc.dorm_text.service;


import edu.fdzc.dorm_text.entity.Student;

import java.util.List;

public interface StudentService {
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