package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.DormitoryEntity;
import edu.fdzc.dorm_text.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public boolean deleteDormitory(@PathVariable String id) {
        return dormitoryService.deleteDormitory(id);
    }

    @PutMapping
    public boolean updateDormitory(@RequestBody DormitoryEntity dormitory) {
        return dormitoryService.updateDormitory(dormitory);
    }

    @GetMapping("/{id}")
    public DormitoryEntity getDormitoryById(@PathVariable String id) {
        return dormitoryService.getDormitoryById(id);
    }

    @GetMapping
    public List<DormitoryEntity> getAllDormitories() {
        return dormitoryService.getAllDormitories();
    }

    @GetMapping("/building/{buildingId}")
    public List<DormitoryEntity> getDormitoriesByBuildingId(@PathVariable String buildingId) {
        return dormitoryService.getDormitoriesByBuildingId(buildingId);
    }
}
