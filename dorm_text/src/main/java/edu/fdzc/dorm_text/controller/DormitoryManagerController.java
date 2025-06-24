package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.DormitoryManagerEntity;
import edu.fdzc.dorm_text.service.DormitoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/dorm/dormitory-manager")
public class DormitoryManagerController {

    @Autowired
    private DormitoryManagerService dormitoryManagerService;

    @PostMapping
    public boolean addManager(@RequestBody DormitoryManagerEntity manager) {
        return dormitoryManagerService.addManager(manager);
    }

    @DeleteMapping("/{id}")
    public boolean deleteManager(@PathVariable String id) {
        return dormitoryManagerService.deleteManager(id);
    }

    @PutMapping
    public boolean updateManager(@RequestBody DormitoryManagerEntity manager) {
        return dormitoryManagerService.updateManager(manager);
    }

    @GetMapping("/{id}")
    public DormitoryManagerEntity getManagerById(@PathVariable String id) {
        return dormitoryManagerService.getManagerById(id);
    }

    @GetMapping
    public List<DormitoryManagerEntity> getAllManagers() {
        return dormitoryManagerService.getAllManagers();
    }
}
