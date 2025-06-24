package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.Admin;
import edu.fdzc.dorm_text.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dorm/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 登录
    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        Admin result = adminService.login(admin.getName(), admin.getPassword());
        return result != null ? "登录成功" : "用户名或密码错误";
    }

    // 注册
    @PostMapping("/register")
    public String register(@RequestBody Admin admin) {
        return adminService.register(admin) ? "注册成功" : "用户名已存在";
    }

    // 更新
    @PutMapping
    public String update(@RequestBody Admin admin) {
        return adminService.updateAdmin(admin) ? "更新成功" : "管理员不存在";
    }

    // 删除
    @DeleteMapping("/{name}")
    public String delete(@PathVariable String name) {
        return adminService.deleteAdmin(name) ? "删除成功" : "管理员不存在";
    }

    // 获取所有
    @GetMapping
    public List<Admin> getAll() {
        return adminService.getAllAdmins();
    }

    // 根据name查询
    @GetMapping("/{name}")
    public Admin getByName(@PathVariable String name) {
        return adminService.getAdminByName(name);
    }
}