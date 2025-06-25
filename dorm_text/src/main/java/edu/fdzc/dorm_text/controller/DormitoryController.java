package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.DormitoryEntity;
import edu.fdzc.dorm_text.service.DormitoryService;
import edu.fdzc.dorm_text.vo.DormitoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dorm/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryService dormitoryService;

    @PostMapping
    public boolean addDormitory(@RequestBody DormitoryEntity dormitory) {
        return dormitoryService.addDormitory(dormitory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDormitory(@PathVariable String id) {
        try {
            boolean result = dormitoryService.deleteDormitory(id);
            if (result) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "删除成功");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "删除失败");
                return ResponseEntity.ok(response);
            }
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{id}/force")
    public ResponseEntity<?> forceDeleteDormitory(@PathVariable String id) {
        try {
            boolean result = dormitoryService.forceDeleteDormitory(id);
            if (result) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "强制删除成功");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "强制删除失败");
                return ResponseEntity.ok(response);
            }
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping
    public boolean updateDormitory(@RequestBody DormitoryEntity dormitory) {
        return dormitoryService.updateDormitory(dormitory);
    }

    @GetMapping("/{id}")
    public DormitoryEntity getDormitoryById(@PathVariable String id) {
        return dormitoryService.getDormitoryById(id);
    }

    @GetMapping("/building/{buildingId}")
    public List<DormitoryEntity> getDormitoriesByBuildingId(@PathVariable String buildingId) {
        return dormitoryService.getDormitoriesByBuildingId(buildingId);
    }

    @GetMapping
    public List<DormitoryVo> getAllDormitories() {
        return dormitoryService.getAllDormitoriesWithDetails();
    }
}
