package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;
import edu.fdzc.dorm_text.service.DormitoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dorm/dormitory-manager")
public class DormitoryManagerController {

    @Autowired
    private DormitoryManagerService dormitoryManagerService;

    // 宿管登录 - 与管理员登录格式一致
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginData) {
        try {
            String name = loginData.get("name");  // 修改：使用name字段
            String password = loginData.get("password");
            
            System.out.println("宿管登录请求: name=" + name + ", password=" + password);
            
            DormitoryManagerEntity manager = dormitoryManagerService.login(name, password);
            
            if (manager != null) {
                System.out.println("宿管登录成功: " + name);
                return "登录成功";
            } else {
                System.out.println("宿管登录失败: " + name);
                return "工号或密码错误";
            }
        } catch (Exception e) {
            System.out.println("宿管登录异常: " + e.getMessage());
            return "工号或密码错误";
        }
    }

    // 测试连接
    @GetMapping("/test")
    public String testConnection() {
        return "连接成功";
    }

    // 获取宿管管理的宿舍信息
    @GetMapping("/dormitory/{managerId}")
    public ResponseEntity<?> getManagedDormitory(@PathVariable String managerId) {
        try {
            Map<String, Object> dormitoryInfo = dormitoryManagerService.getManagedDormitory(managerId);
            Map<String, Object> response = new HashMap<>();
            if (dormitoryInfo != null) {
                response.put("success", true);
                response.put("data", dormitoryInfo);
            } else {
                response.put("success", false);
                response.put("message", "未找到管理的宿舍");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 获取宿舍的学生列表
    @GetMapping("/dormitory/{dormitoryId}/students")
    public ResponseEntity<?> getDormitoryStudents(@PathVariable String dormitoryId) {
        try {
            List<Map<String, Object>> students = dormitoryManagerService.getDormitoryStudents(dormitoryId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", students);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 学生入住登记
    @PostMapping("/check-in")
    public ResponseEntity<?> checkInStudent(@RequestBody Map<String, String> checkInData) {
        try {
            boolean result = dormitoryManagerService.checkInStudent(checkInData);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("success", true);
                response.put("message", "入住登记成功");
            } else {
                response.put("success", false);
                response.put("message", "入住登记失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 学生退宿登记
    @PostMapping("/check-out")
    public ResponseEntity<?> checkOutStudent(@RequestBody Map<String, String> checkOutData) {
        try {
            boolean result = dormitoryManagerService.checkOutStudent(checkOutData);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("success", true);
                response.put("message", "退宿登记成功");
            } else {
                response.put("success", false);
                response.put("message", "退宿登记失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 获取宿舍统计信息
    @GetMapping("/stats/{dormitoryId}")
    public ResponseEntity<?> getDormitoryStats(@PathVariable String dormitoryId) {
        try {
            Map<String, Object> stats = dormitoryManagerService.getDormitoryStats(dormitoryId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", stats);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 提交维修申请
    @PostMapping("/maintenance")
    public ResponseEntity<?> submitMaintenanceRequest(@RequestBody Map<String, String> maintenanceData) {
        try {
            boolean result = dormitoryManagerService.submitMaintenanceRequest(maintenanceData);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("success", true);
                response.put("message", "维修申请提交成功");
            } else {
                response.put("success", false);
                response.put("message", "维修申请提交失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 获取宿舍维修记录
    @GetMapping("/maintenance/{dormitoryId}")
    public ResponseEntity<?> getMaintenanceRecords(@PathVariable String dormitoryId) {
        try {
            List<Map<String, Object>> records = dormitoryManagerService.getMaintenanceRecords(dormitoryId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 添加宿管
    @PostMapping
    public ResponseEntity<?> addManager(@RequestBody DormitoryManagerEntity manager) {
        try {
            boolean result = dormitoryManagerService.addManager(manager);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("success", true);
                response.put("message", "添加成功");
            } else {
                response.put("success", false);
                response.put("message", "添加失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 删除宿管
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable String id) {
        try {
            boolean result = dormitoryManagerService.deleteManager(id);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("success", true);
                response.put("message", "删除成功");
            } else {
                response.put("success", false);
                response.put("message", "删除失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 更新宿管
    @PutMapping
    public ResponseEntity<?> updateManager(@RequestBody DormitoryManagerEntity manager) {
        try {
            boolean result = dormitoryManagerService.updateManager(manager);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("success", true);
                response.put("message", "更新成功");
            } else {
                response.put("success", false);
                response.put("message", "更新失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // 根据ID获取宿管
    @GetMapping("/{id}")
    public DormitoryManagerEntity getManagerById(@PathVariable String id) {
        return dormitoryManagerService.getManagerById(id);
    }

    // 获取所有宿管
    @GetMapping
    public List<DormitoryManagerEntity> getAllManagers() {
        return dormitoryManagerService.getAllManagers();
    }
}
