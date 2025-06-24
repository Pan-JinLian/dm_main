package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.Student;
import edu.fdzc.dorm_text.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dorm/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // 添加学生
    @PostMapping("/add")
    public int addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // 根据学号删除学生
    @DeleteMapping("/delete/{id}")
    public int deleteStudentById(@PathVariable String id) {
        return studentService.deleteStudentById(id);
    }

    // 更新学生信息
    @PutMapping("/update")
    public int updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    // 根据学号查询学生
    @GetMapping("/get/{id}")
    public Student getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    // 查询所有学生
    @GetMapping("/getAll")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}