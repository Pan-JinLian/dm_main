package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.Student;
import edu.fdzc.dorm_text.mapper.StudentMapper;
import edu.fdzc.dorm_text.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public int deleteStudentById(String id) {
        return studentMapper.deleteStudentById(id);
    }

    @Override
    public int updateStudent(Student student) {
        // 如果密码为空，则不更新密码字段
        if (student.getPassword() == null || student.getPassword().trim().isEmpty()) {
            // 获取原学生信息，保持密码不变
            Student originalStudent = studentMapper.getStudentById(student.getId());
            if (originalStudent != null) {
                student.setPassword(originalStudent.getPassword());
            }
        }
        return studentMapper.updateStudent(student);
    }

    @Override
    public Student getStudentById(String id) {
        return studentMapper.getStudentById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }
}