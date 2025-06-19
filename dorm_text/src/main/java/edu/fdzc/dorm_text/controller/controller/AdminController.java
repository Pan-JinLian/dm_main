package com.example.susheguanli.controller;

import com.example.susheguanli.entity.Admin;
import com.example.susheguanli.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Admin admin) {
        logger.info("登录请求接收 - 用户名: {}", admin.getName());

        try {
            // 参数校验
            if (admin.getName() == null || admin.getName().trim().isEmpty()) {
                return buildResponse(400, "用户名不能为空");
            }
            if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
                return buildResponse(400, "密码不能为空");
            }

            // 业务处理（直接使用明文密码比对）
            Admin loggedInAdmin = adminService.login(admin.getName(), admin.getPassword());

            if (loggedInAdmin != null) {
                logger.info("用户 {} 登录成功", admin.getName());
                return buildResponse(200, "登录成功", loggedInAdmin);
            } else {
                logger.warn("用户 {} 登录失败，用户名或密码错误", admin.getName());
                return buildResponse(401, "用户名或密码错误");
            }
        } catch (Exception e) {
            logger.error("登录处理异常", e);
            return buildResponse(500, "系统异常，请稍后重试");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Admin admin) {
        logger.info("注册请求接收 - 用户名: {}", admin.getName());

        try {
            if (admin.getName() == null || admin.getName().trim().isEmpty()) {
                return buildResponse(400, "用户名不能为空");
            }
            if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
                return buildResponse(400, "密码不能为空");
            }

            // 直接存储明文密码
            boolean result = adminService.register(admin);
            if (result) {
                logger.info("用户 {} 注册成功", admin.getName());
                return buildResponse(200, "注册成功");
            } else {
                logger.warn("用户 {} 注册失败，用户名已存在", admin.getName());
                return buildResponse(409, "用户名已存在");
            }
        } catch (Exception e) {
            logger.error("注册处理异常", e);
            return buildResponse(500, "系统异常，请稍后重试");
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> update(@RequestBody Admin admin) {
        try {
            // 直接更新明文密码
            boolean result = adminService.updateAdmin(admin);
            return buildResponse(result ? 200 : 400,
                    result ? "更新成功" : "更新失败");
        } catch (Exception e) {
            logger.error("更新管理员信息异常", e);
            return buildResponse(500, "系统异常，请稍后重试");
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String name) {
        try {
            boolean result = adminService.deleteAdmin(name);
            return buildResponse(result ? 200 : 400,
                    result ? "删除成功" : "删除失败");
        } catch (Exception e) {
            logger.error("删除管理员异常", e);
            return buildResponse(500, "系统异常，请稍后重试");
        }
    }

    // 构建统一响应格式
    private ResponseEntity<Map<String, Object>> buildResponse(int status, String message) {
        return buildResponse(status, message, null);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(int status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }

        // 调试信息（仅开发环境）
        if (status >= 400) {
            response.put("debug_time", System.currentTimeMillis());
        }

        return ResponseEntity.status(status).body(response);
    }
}